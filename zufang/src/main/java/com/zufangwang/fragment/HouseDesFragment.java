package com.zufangwang.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.gson.Gson;
import com.zufangwang.activity.RentalActivity;
import com.zufangwang.base.Configs;
import com.zufangwang.entity.HouseDesInfo;
import com.zufangwang.entity.HouseInfo;
import com.zufangwang.francis.zufangwang.R;
import com.zufangwang.view.GridViewForScrollView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Francis on 2016/4/28.
 */
public class HouseDesFragment extends Fragment {
    @InjectView(R.id.tv_house_publisher)
    TextView tvHousePublisher;
    @InjectView(R.id.tv_house_publisher_tel)
    TextView tvHousePublisherTel;
    @InjectView(R.id.llyt_bottom)
    LinearLayout llytBottom;
    @InjectView(R.id.vf_login)
    ViewFlipper vfLogin;
    @InjectView(R.id.tv_house_title)
    TextView tvHouseTitle;
    @InjectView(R.id.tv_house_price)
    TextView tvHousePrice;
    @InjectView(R.id.tv_house_date)
    TextView tvHouseDate;
    @InjectView(R.id.tv_room)
    TextView tvRoom;
    @InjectView(R.id.tv_renovation)
    TextView tvRenovation;
    @InjectView(R.id.tv_floor)
    TextView tvFloor;
    @InjectView(R.id.tv_bedroom)
    TextView tvBedroom;
    @InjectView(R.id.tv_area)
    TextView tvArea;
    @InjectView(R.id.tv_survey)
    TextView tvSurvey;
    @InjectView(R.id.tv_orientation)
    TextView tvOrientation;
    @InjectView(R.id.tv_limit)
    TextView tvLimit;
    @InjectView(R.id.tv_house_location)
    TextView tvHouseLocation;
    @InjectView(R.id.tv_broadband)
    TextView tvBroadband;
    @InjectView(R.id.tv_television)
    TextView tvTelevision;
    @InjectView(R.id.tv_sofa)
    TextView tvSofa;
    @InjectView(R.id.tv_washing)
    TextView tvWashing;
    @InjectView(R.id.tv_bed)
    TextView tvBed;
    @InjectView(R.id.tv_refrigerator)
    TextView tvRefrigerator;
    @InjectView(R.id.tv_air)
    TextView tvAir;
    @InjectView(R.id.tv_heating)
    TextView tvHeating;
    @InjectView(R.id.tv_wardrobe)
    TextView tvWardrobe;
    @InjectView(R.id.tv_heater)
    TextView tvHeater;
    @InjectView(R.id.tv_house_des)
    TextView tvHouseDes;
    @InjectView(R.id.scroll_house_des)
    ScrollView scrollHouseDes;
    @InjectView(R.id.gv_img)
    GridViewForScrollView gvImg;
    @InjectView(R.id.tv_chat)
    TextView tvChat;
    @InjectView(R.id.tv_tel)
    TextView tvTel;
    @InjectView(R.id.iv_back)
    ImageView ivBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.llyt_title)
    LinearLayout llytTitle;

    private HouseInfo houseInfo;
    private HouseDesInfo houseDesInfo;
    private Context context;
    private ArrayList<Bitmap> bitmapList;
    private BaseAdapter imgAdapter;
    private GridView gridView;

    public void setHouseInfo(HouseInfo houseInfo) {
        this.houseInfo = houseInfo;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_house_des, null);
        ButterKnife.inject(this, view);
        initData();
        return view;
    }

    private void initData() {
        context = getActivity();
        houseDesInfo = new Gson().fromJson(houseInfo.getHouse_des_info(), HouseDesInfo.class);
        Log.i("ming", "houseDesInfon:　　" + houseDesInfo.toString());
        tvHouseTitle.setText(houseInfo.getHouse_title());
        tvHousePrice.setText(houseInfo.getHouse_price() + "元/月");
        tvHouseDate.setText(houseInfo.getHouse_date());
        tvHouseLocation.setText(houseInfo.getHouse_address() + houseInfo.getHouse_address_detail());
        tvRoom.setText(houseInfo.getHouse_apartment());
        tvArea.setText(houseInfo.getHouse_area());
        tvRenovation.setText(houseDesInfo.getHouse_renovation());
        tvOrientation.setText(houseDesInfo.getHouse_orientation());
        tvFloor.setText(houseInfo.getHouse_floor() + "/" + houseInfo.getHouse_all_floor() + "层");
        tvLimit.setText(houseDesInfo.getHouse_limit());

        isHave(tvBroadband, houseDesInfo.isBroadband());
        isHave(tvTelevision, houseDesInfo.isVideo());
        isHave(tvSofa, houseDesInfo.isSofa());
        isHave(tvWashing, houseDesInfo.isWashing());
        isHave(tvBed, houseDesInfo.isBed());
        isHave(tvRefrigerator, houseDesInfo.isRefrigerator());
        isHave(tvAir, houseDesInfo.isAir());
        isHave(tvHeating, houseDesInfo.isHeating());
        isHave(tvWardrobe, houseDesInfo.isWardrobe());
        isHave(tvHeater, houseDesInfo.isHeater());

        tvHouseDes.setText(houseInfo.getHouse_des());
        tvHousePublisher.setText(houseInfo.getHouse_contacts());
        tvHousePublisherTel.setText(houseInfo.getHouse_contacts_tel());

        bitmapList = new ArrayList<>();
        ArrayList<String> imgs = new ArrayList<>();
        if (!houseInfo.getHouse_imgs().equals("")){
            imgs = new Gson().fromJson(houseInfo.getHouse_imgs(), ArrayList.class);
            for (String img : imgs) {
                bitmapList.add(Configs.base64ToBitmap(img));
            }
            if (bitmapList.size() > 0) {
                for (Bitmap bitmap : bitmapList) {
                    vfLogin.addView(getImageView(bitmap));
                }
            }
            initImgAdapter();
            gvImg.setAdapter(imgAdapter);
        }

    }

    //初始化照片adapter
    private void initImgAdapter() {
        imgAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return bitmapList.size();
            }

            @Override
            public Object getItem(int position) {
                return bitmapList.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = new ImageView(getActivity());
                }
                ((ImageView) convertView).setImageBitmap(bitmapList.get(position));
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(new ViewGroup.LayoutParams(300, 300));
                convertView.setLayoutParams(params);
                return convertView;
            }
        };
    }

    private View getImageView(Bitmap bitmap) {
        ImageView imageView = new ImageView(context);
        imageView.setImageBitmap(bitmap);
        return imageView;
    }

    //判断宽带等是否有
    private void isHave(TextView textView, boolean is) {
        if (is) {
            textView.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        } else {
            String text = textView.getText().toString();
            SpannableString span = new SpannableString(text);
            span.setSpan(new StrikethroughSpan(), 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView.setText(span);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.tv_tel, R.id.tv_chat})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_tel:
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+tvHousePublisherTel.getText().toString()));
                startActivity(intent);
                break;
            case R.id.tv_chat:
                Intent intent1=new Intent(context, RentalActivity.class);
                intent1.putExtra("house_price",houseInfo.getHouse_price());
                intent1.putExtra("house_info",houseInfo);
                startActivity(intent1);
//                if (houseInfo.getHouse_publish_id().equals(context.getSharedPreferences("user",0).getString("user_id","")))
//                    Toast.makeText(context,"该房源是你发布的",Toast.LENGTH_SHORT).show();
//                else {
//
//                }

                break;
        }
    }
}
