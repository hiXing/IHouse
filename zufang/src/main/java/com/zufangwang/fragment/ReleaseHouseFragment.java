package com.zufangwang.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.zufangwang.activity.ReleaseHouseActivity;
import com.zufangwang.activity.ReleaseHouseDesActivity;
import com.zufangwang.base.Configs;
import com.zufangwang.entity.HouseInfo;
import com.zufangwang.francis.zufangwang.R;
import com.zufangwang.utils.FileTools;
import com.zufangwang.utils.OkHttpClientManager;
import com.zufangwang.utils.SelectHeadTools;
import com.zufangwang.view.GridViewForScrollView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Francis on 2016/4/25.
 */
public class ReleaseHouseFragment extends Fragment implements View.OnClickListener,AdapterView.OnItemSelectedListener{
    @InjectView(R.id.house_address)
    Spinner houseAddress;
    @InjectView(R.id.house_address_detail)
    Spinner houseAddressDetail;
    @InjectView(R.id.et_house_address_detail)
    EditText etHouseAddressDetail;
    @InjectView(R.id.et_house_apartment_room)
    EditText etHouseApartmentRoom;
    @InjectView(R.id.et_house_apartment_office)
    EditText etHouseApartmentOffice;
    @InjectView(R.id.et_house_apartment_wei)
    EditText etHouseApartmentWei;
    @InjectView(R.id.et_house_floor)
    EditText etHouseFloor;
    @InjectView(R.id.et_house_all_floor)
    EditText etHouseAllFloor;
    @InjectView(R.id.et_house_are)
    EditText etHouseAre;
    @InjectView(R.id.et_house_price)
    EditText etHousePrice;
    @InjectView(R.id.et_house_title)
    EditText etHouseTitle;
    @InjectView(R.id.et_house_des)
    EditText etHouseDes;
    @InjectView(R.id.et_house_contacts)
    EditText etHouseContacts;
    @InjectView(R.id.et_house_contacts_tel)
    EditText etHouseContactsTel;
    @InjectView(R.id.tv_add_image)
    TextView tvAddImage;
    @InjectView(R.id.gv_img)
    GridViewForScrollView gvImg;
    @InjectView(R.id.btn_release)
    Button btnRelease;

    private String[] location_first,location_second;
    private Map<String,String[]> locationMap;
    private String address1,address2;
    private Uri photoUri = null;
    private BaseAdapter imgAdapter;
    private ArrayList<Bitmap> bitmapArrayList;
    private ArrayList<String> bitmapToStringList;
    private Context mContext;
    private String house_type;

    public void setHouse_type(String house_type) {
        this.house_type = house_type;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_house_release, null);
        ButterKnife.inject(this, view);
        initData();
        initListener();
        return view;
    }

    private void initData() {
        mContext=getActivity();
        locationMap=new HashMap<>();
        location_first=getActivity().getResources().getStringArray(R.array.address_first);
        location_second=getActivity().getResources().getStringArray(R.array.location_siming);
        locationMap.put(location_first[0],getActivity().getResources().getStringArray(R.array.location_siming));
        locationMap.put(location_first[1],getActivity().getResources().getStringArray(R.array.location_huli));
        locationMap.put(location_first[2],getActivity().getResources().getStringArray(R.array.location_jimei));
        locationMap.put(location_first[3],getActivity().getResources().getStringArray(R.array.location_xinglin));
        locationMap.put(location_first[4],getActivity().getResources().getStringArray(R.array.location_haicang));


        bitmapArrayList=new ArrayList<>();
        bitmapToStringList=new ArrayList<>();
        initImgAdapter();
        gvImg.setAdapter(imgAdapter);

        etHouseContacts.setText(mContext.getSharedPreferences("user",0).getString("user_name",""));
        etHouseContactsTel.setText(mContext.getSharedPreferences("user",0).getString("user_tel",""));
    }
    //初始化照片adapter
    private void initImgAdapter() {
        imgAdapter=new BaseAdapter() {
            @Override
            public int getCount() {
                return bitmapArrayList.size();
            }

            @Override
            public Object getItem(int position) {
                return bitmapArrayList.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView==null){
                    convertView=new ImageView(getActivity());
                }
                ((ImageView)convertView).setImageBitmap(bitmapArrayList.get(position));
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(new ViewGroup.LayoutParams(300,300));
                convertView.setLayoutParams(params);
                return convertView;
            }
        };
    }
    //在Activity中返回照片时调用
    public void setImageView(Bitmap bitmap){
        bitmapArrayList.add(bitmap);
        imgAdapter.notifyDataSetChanged();
    }
    private void initListener() {
        houseAddress.setOnItemSelectedListener(this);
        houseAddressDetail.setOnItemSelectedListener(this);

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.tv_add_image, R.id.btn_release})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_add_image:
                showCamear();
                break;
            case R.id.btn_release:
                releaseHouse();
                break;
        }
    }
    //发布房源
    private void releaseHouse() {
        if (etHouseAddressDetail.getText().toString().equals(""))
        {
            showToast("详细地址不能为空",Toast.LENGTH_SHORT);
            return;
        }
        if (etHouseApartmentRoom.getText().toString().equals(""))
        {
            showToast("具体几室不能为空",Toast.LENGTH_SHORT);
            return;
        }
        if (etHouseApartmentOffice.getText().toString().equals(""))
        {
            showToast("具体几厅不能为空",Toast.LENGTH_SHORT);
            return;
        }
        if (etHouseApartmentWei.getText().toString().equals(""))
        {
            showToast("具体几卫不能为空",Toast.LENGTH_SHORT);
            return;
        }
        if (etHouseFloor.getText().toString().equals(""))
        {
            showToast("具体楼层为空",Toast.LENGTH_SHORT);
            return;
        }
        if (etHouseAllFloor.getText().toString().equals(""))
        {
            showToast("总楼层不能为空",Toast.LENGTH_SHORT);
            return;
        }
        if (etHouseAre.getText().toString().equals(""))
        {
            showToast("面积不能为空",Toast.LENGTH_SHORT);
            return;
        }
        if (etHousePrice.getText().toString().equals(""))
        {
            showToast("租金不能为空",Toast.LENGTH_SHORT);
            return;
        }
        if (etHouseTitle.getText().toString().equals(""))
        {
            showToast("标题不能为空",Toast.LENGTH_SHORT);
            return;
        }
        if (etHouseDes.getText().toString().length()<10)
        {
            showToast("描述至少10字",Toast.LENGTH_SHORT);
            return;
        }
        if (etHouseContacts.getText().toString().equals(""))
        {
            showToast("联系人不能为空",Toast.LENGTH_SHORT);
            return;
        }
        if (etHouseContactsTel.getText().toString().equals(""))
        {
            showToast("联系人手机号不能为空",Toast.LENGTH_SHORT);
            return;
        }
        for (Bitmap bitmap:bitmapArrayList)
            bitmapToStringList.add(Configs.bitmapToBase64(bitmap));
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        HouseInfo house=new HouseInfo(0,house_type,address1+address2,etHouseAddressDetail.getText().toString(),
                etHouseApartmentRoom.getText().toString()+"室"+etHouseApartmentOffice.getText().toString()+"厅"+etHouseApartmentWei.getText().toString()+"卫",
                etHouseFloor.getText().toString(),etHouseAllFloor.getText().toString(),etHouseAre.getText().toString(),etHousePrice.getText().toString(),
                etHouseTitle.getText().toString(),etHouseDes.getText().toString(),etHouseContacts.getText().toString(),etHouseContactsTel.getText().toString(),
                new Gson().toJson(bitmapToStringList),df.format(new Date()),"",mContext.getSharedPreferences("user",0).getString("user_id",""));
        Log.i("ming","house:  "+new Gson().toJson(house));

        judgeHouseExist(house);
//        Intent intent=new Intent(getActivity(), ReleaseHouseDesActivity.class);
//        intent.putExtra("house_info",house);
//        startActivity(intent);
//        ((ReleaseHouseActivity)mContext).succeedRelease();

    }
    //判断当前发布的房源是否已经存在
    private void judgeHouseExist(final HouseInfo house) {
        OkHttpClientManager.postAsyn(Configs.JUDGE_HOUSE_EXIST, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(getContext(), Configs.URLERROR, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                Log.i("ming","ADD_HOUSE response:   "+response);
                if (!response.equals("1")) {
                    Intent intent=new Intent(getActivity(), ReleaseHouseDesActivity.class);
                    intent.putExtra("house_info",house);
                    startActivity(intent);
                    ((ReleaseHouseActivity)mContext).succeedRelease();
                }
                else{
                    Toast.makeText(getContext(), "您已经发布过相同的房源，请重新填写地址或价格。", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        },new OkHttpClientManager.Param("house_info",new Gson().toJson(house)));
    }

    //获取图片
    private void showCamear() {
        if(!FileTools.hasSdcard()){
            Toast.makeText(getActivity(),"没有找到SD卡，请检查SD卡是否存在",Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            photoUri = FileTools.getUriByFileDirAndFileName(Configs.SystemPicture.SAVE_DIRECTORY, Configs.SystemPicture.SAVE_PIC_NAME);
        } catch (IOException e) {
            Toast.makeText(getActivity(), "创建文件失败。", Toast.LENGTH_SHORT).show();
            return;
        }
        SelectHeadTools.openDialog(getActivity(),photoUri);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.house_address:
                address1=location_first[position];
                for (Map.Entry<String, String[]> entry : locationMap.entrySet()) {
                    if (entry.getKey().equals(location_first[position]))
                        location_second=entry.getValue();
                }
                setSecondSpin();
                break;
            case R.id.house_address_detail:
                address2=location_second[position];
                Log.i("ming","address:  "+address1+address2);
                break;
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    //设置第二个地址
    private void setSecondSpin() {
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, location_second);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        houseAddressDetail.setAdapter(adapter);
    }

    //显示Toast
    private void showToast(String message,int time){
        Toast.makeText(getActivity(),message,time).show();
    }

}
