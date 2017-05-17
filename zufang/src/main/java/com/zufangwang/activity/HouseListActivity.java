package com.zufangwang.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.zufangwang.base.BaseActivity;
import com.zufangwang.francis.zufangwang.R;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Francis on 2016/4/23.
 */
public class HouseListActivity extends BaseActivity{
    @InjectView(R.id.iv_back)
    ImageView ivBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.llyt_title)
    LinearLayout llytTitle;
    @InjectView(R.id.tv_house_location)
    TextView tvHouseLocation;
    @InjectView(R.id.tv_house_price)
    TextView tvHousePrice;
    @InjectView(R.id.tv_house_mode)
    TextView tvHouseMode;
    @InjectView(R.id.recycle_house)
    RecyclerView recycleHouse;

    private String[] location,location1,house_price,house_mode;
    private HouseAdapter adapter,adapter1,price_adapter,mode_adapter;
    private int location_position1 =0, location_position2 =0, location_top1, location_top2,price_position,mode_position,price_top,mode_top;
    private Map<String,String[]> locationMap;
    private PopupWindow popWindow;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_house_list;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        house_price=mContext.getResources().getStringArray(R.array.house_price);
        house_mode=mContext.getResources().getStringArray(R.array.house_mode);
        locationMap=new HashMap<>();
        location=mContext.getResources().getStringArray(R.array.location_first);
        locationMap.put(location[1],mContext.getResources().getStringArray(R.array.location_siming));
        locationMap.put(location[2],mContext.getResources().getStringArray(R.array.location_huli));
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);

    }

    @OnClick({R.id.iv_back, R.id.tv_house_location, R.id.tv_house_price, R.id.tv_house_mode})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_house_location:
                initPopWindow(view,R.id.tv_house_location);
                break;
            case R.id.tv_house_price:
                initPopWindow(view,R.id.tv_house_price);
                break;
            case R.id.tv_house_mode:
                initPopWindow(view,R.id.tv_house_mode);
                break;
        }
    }

    //选择房源地址
    private View selectLocation() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.popup_select_location, null, false);
        final ListView recycler_first= (ListView) view.findViewById(R.id.recycle_location_first);
        final ListView recycler_second= (ListView) view.findViewById(R.id.recycle_location_second);
        final ImageView imageView=(ImageView)view.findViewById(R.id.iv_listview);
        adapter=new HouseAdapter(location);
        recycler_first.setAdapter(adapter);
        if (location_position1 !=0){
            adapter.setSelected(location_position1,adapter);
            recycler_first.setSelectionFromTop(location_position1, location_top1);
            imageView.setVisibility(View.GONE);
            recycler_second.setVisibility(View.VISIBLE);

            for (Map.Entry<String, String[]> entry : locationMap.entrySet()) {
                if (entry.getKey().equals(location[location_position1])){
                    location1=entry.getValue();
                }
            }
//            location1=mContext.getResources().getStringArray(R.array.location_siming);
            adapter1=new HouseAdapter(location1);
            recycler_second.setAdapter(adapter1);
            adapter1.setSelected(location_position2,adapter1);
            recycler_second.setSelectionFromTop(location_position2, location_top2);
        }
        else if (location_position1 ==0){
            adapter.setSelected(location_position1,adapter);
            imageView.setVisibility(View.VISIBLE);
            recycler_second.setVisibility(View.GONE);
        }
        recycler_first.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                location_position1 =position;
                adapter.setSelected(position,adapter);
                if (position==0){
                    imageView.setVisibility(View.VISIBLE);
                    recycler_second.setVisibility(View.GONE);
                    tvHouseLocation.setText(location[position]);
                    popWindow.dismiss();
                    return;
                }
                imageView.setVisibility(View.GONE);
                recycler_second.setVisibility(View.VISIBLE);
                for (Map.Entry<String, String[]> entry : locationMap.entrySet()) {
                    if (entry.getKey().equals(location[position])){
                        location1=entry.getValue();
                    }
                }
                adapter1=new HouseAdapter(location1);
                recycler_second.setAdapter(adapter1);
                View v = recycler_first.getChildAt(0);
                location_top1 = (v == null) ? 0 : v.getTop();
            }
        });
        recycler_second.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                location_position2 =position;
                adapter1.setSelected(position,adapter1);
                tvHouseLocation.setText(location1[position]);
                View v = recycler_second.getChildAt(0);
                location_top2 = (v == null) ? 0 : v.getTop();
                popWindow.dismiss();
                return;
            }
        });
        return view;
    }

    //选择房源价格
    private View selectPrice() {
        View view=LayoutInflater.from(mContext).inflate(R.layout.popup_select_price,null);
        final ListView lv_house_price=(ListView)view.findViewById(R.id.lv_house_price);
        price_adapter=new HouseAdapter(house_price);
        lv_house_price.setAdapter(price_adapter);
        price_adapter.setSelected(price_position,price_adapter);
        lv_house_price.setSelectionFromTop(price_position, price_top);
        lv_house_price.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                price_position=position;
                price_adapter.setSelected(position,price_adapter);
                if (position==(house_price.length-1)){
                    selectPriceDialog();
                }
                tvHousePrice.setText(house_price[position]);
                View v = lv_house_price.getChildAt(0);
                price_top = (v == null) ? 0 : v.getTop();
                popWindow.dismiss();
            }
        });

        return view;
    }

    //选择房源方式
    private View selectMode() {
        View view=LayoutInflater.from(mContext).inflate(R.layout.popup_select_price,null);
        final ListView lv_house_mode=(ListView)view.findViewById(R.id.lv_house_price);
        mode_adapter=new HouseAdapter(house_mode);
        lv_house_mode.setAdapter(mode_adapter);
        mode_adapter.setSelected(mode_position,mode_adapter);
        lv_house_mode.setSelectionFromTop(mode_position, mode_top);

        lv_house_mode.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mode_position=position;
                mode_adapter.setSelected(position,mode_adapter);
                tvHouseMode.setText(house_mode[position]);
                View v = lv_house_mode.getChildAt(0);
                mode_top = (v == null) ? 0 : v.getTop();
                popWindow.dismiss();
            }
        });
        return view;

    }
    private void initPopWindow(View v,int i) {
        initData();
        View view = null;
        switch (i){
            case R.id.tv_house_location:
                view=selectLocation();
                break;
            case R.id.tv_house_price:
                view=selectPrice();
                break;
            case R.id.tv_house_mode:
                view=selectMode();
                break;
        }
        //1.构造一个PopupWindow，参数依次是加载的View，宽高
        popWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        //这些为了点击非PopupWindow区域，PopupWindow会消失的，如果没有下面的
        //代码的话，你会发现，当你把PopupWindow显示出来了，无论你按多少次后退键
        //PopupWindow并不会关闭，而且退不出程序，加上下述代码可以解决这个问题
        popWindow.setTouchable(true);
        popWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
        popWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));    //要为popWindow设置一个背景才有效
        //设置popupWindow显示的位置，参数依次是参照View，x轴的偏移量，y轴的偏移量
        popWindow.showAsDropDown(v, 0, 0);
    }

    class HouseAdapter extends android.widget.BaseAdapter{
        String[] data;
        int index=10000;
        public HouseAdapter(String[] data) {
            this.data=data;
        }

        @Override
        public int getCount() {
            return data.length;
        }

        @Override
        public Object getItem(int position) {
            return data[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void setSelected(int setpos,HouseAdapter adapter) {
            index = setpos;
            adapter.notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView==null)
                convertView=LayoutInflater.from(mContext).inflate(R.layout.item_location,null);
            TextView tv= (TextView) convertView.findViewById(R.id.tv_location);
            ImageView iv_select=(ImageView)convertView.findViewById(R.id.iv_select);
            tv.setText(data[position]);
            if (position==index){
                tv.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
                iv_select.setBackgroundResource(R.color.red);
            }
            else{
                tv.setTextColor(mContext.getResources().getColor(R.color.black));
            iv_select.setBackgroundResource(R.color.white);}
            return convertView;
        }
    }
    //自定义价格的dialog
    private void selectPriceDialog(){
        final AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
        final AlertDialog alertDialog=builder.create();
        View view=LayoutInflater.from(mContext).inflate(R.layout.dialog_custom_price,null);
        final EditText et_low_price=(EditText)view.findViewById(R.id.et_low_price);
        final EditText et_high_price=(EditText)view.findViewById(R.id.et_high_price);
        Button btn_back=(Button)view.findViewById(R.id.btn_back);
        Button btn_select=(Button)view.findViewById(R.id.btn_select);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_low_price.getText().toString().equals("")||et_high_price.getText().toString().equals("")){
                    showToast("您还没有输入任何条件",Toast.LENGTH_SHORT);
                    return;
                }
                tvHousePrice.setText(et_low_price.getText().toString()+"-"+et_high_price.getText().toString()+"元");
                alertDialog.dismiss();
                popWindow.dismiss();
            }
        });
        alertDialog.setView(view);
        alertDialog.show();
    }
}
