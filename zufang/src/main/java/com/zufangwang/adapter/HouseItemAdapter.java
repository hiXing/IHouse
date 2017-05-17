package com.zufangwang.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zufangwang.base.BaseAdapter;
import com.zufangwang.base.Configs;
import com.zufangwang.entity.HouseInfo;
import com.zufangwang.francis.zufangwang.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Francis on 2016/4/22.
 */
public class HouseItemAdapter extends BaseAdapter<HouseInfo> {
    List<HouseInfo> mDataList;
    public HouseItemAdapter(Context mContext, List<HouseInfo> mDataList) {
        super(mContext, mDataList);
        this.mDataList=mDataList;
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_house;
    }

    @Override
    protected void bindData(ViewHolder holder, int position, HouseInfo item) {
        CircleImageView img_house;
        TextView tv_house_title,tv_house_address,tv_house_price,tv_house_type;
        img_house=(CircleImageView)holder.getViewById(R.id.img_house);
        tv_house_address=(TextView)holder.getViewById(R.id.tv_house_location);
        tv_house_price=(TextView)holder.getViewById(R.id.tv_house_price);
        tv_house_title=(TextView)holder.getViewById(R.id.tv_house_title);
        tv_house_type=(TextView)holder.getViewById(R.id.tv_house_type);

        item=mDataList.get(position);

        tv_house_address.setText(item.getHouse_address()+" "+item.getHouse_address_detail());
        tv_house_price.setText(item.getHouse_price()+"元/月");
        tv_house_title.setText(item.getHouse_title());
        tv_house_type.setText(item.getHouse_type());
        ArrayList<String> imgs=new ArrayList<>();
        Log.i("ming","house imgs:  "+item.getHouse_imgs());
        if (!item.getHouse_imgs().equals("")){
            imgs=new Gson().fromJson(item.getHouse_imgs(),ArrayList.class);
            if (imgs.size()>0){
                img_house.setImageBitmap(Configs.base64ToBitmap(imgs.get(0)));
            }
        }
    }
}
