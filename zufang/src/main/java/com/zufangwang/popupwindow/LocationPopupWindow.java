package com.zufangwang.popupwindow;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zufangwang.base.BaseAdapter;
import com.zufangwang.francis.zufangwang.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by Francis on 2016/4/23.
 */
public class LocationPopupWindow extends PopupWindow {
//    @InjectView(R.id.recycle_location_first)
//    RecyclerView recycleLocationFirst;
//    @InjectView(R.id.recycle_location_second)
//    RecyclerView recycleLocationSecond;
//    private View view;
//    private Context context;
//    private LocationAdapter location_first,location_second;
//    private String[] location;
//    private ArrayList<String> location1;
//    public LocationPopupWindow(Context context,int x,int y,boolean a) {
//        super(context);
//        this.context = context;
//        view = LayoutInflater.from(context).inflate(R.layout.popup_select_location, null);
//        recycleLocationFirst=(RecyclerView) view.findViewById(R.id.recycle_location_first);
//        location=context.getResources().getStringArray(R.array.location_first);
//        location1=new ArrayList<>();
//        for (String s:location)
//        location1.add(s);
//        location_first=new LocationAdapter(context,location1);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        recycleLocationFirst.setLayoutManager(layoutManager);
//        recycleLocationFirst.setAdapter(location_first);
//
//    }
//    class LocationAdapter extends BaseAdapter<String>{
//
//        public LocationAdapter(Context mContext, List<String> mDataList) {
//            super(mContext, mDataList);
//        }
//
//
//
//
//        @Override
//        protected int getItemLayoutId(int viewType) {
//            return R.layout.item_location;
//        }
//
//        @Override
//        protected void bindData(ViewHolder holder, int position, String item) {
//            TextView tv_location;
//            tv_location=(TextView) holder.getViewById(R.id.tv_location);
//            tv_location.setText(item);
//        }
//
//    }
}
