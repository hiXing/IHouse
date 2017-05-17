package com.zufangwang.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.zufangwang.activity.ReleaseHouseActivity;
import com.zufangwang.francis.zufangwang.R;

import butterknife.ButterKnife;

/**
 * Created by Francis on 2016/4/27.
 */
public class HouseTypeFragment extends Fragment {
    private Context context;
    private ListView lv_house_type;
    private String[] housetype;
    private BaseAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_house_type_list, null);
        lv_house_type=(ListView)view.findViewById(R.id.lv_house_type);
        initData();
        initAdapter();
        initHouseTypeList();
        initListener();
        return view;
    }

    private void initAdapter() {
        adapter=new BaseAdapter() {
            @Override
            public int getCount() {
                return housetype.length;
            }

            @Override
            public Object getItem(int position) {
                return housetype[position];
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView==null){
                    convertView=LayoutInflater.from(context).inflate(R.layout.item_house_type,null);
                }
                TextView tv_house_type=(TextView)convertView.findViewById(R.id.tv_house_type);
                tv_house_type.setText(housetype[position]);
                return convertView;
            }
        };
    }

    private void initListener() {
        lv_house_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(context, ReleaseHouseActivity.class);
                intent.putExtra("house_type",housetype[position]);
                startActivity(intent);
            }
        });
    }

    private void initData() {
        context=getActivity();
        housetype=context.getResources().getStringArray(R.array.house_release_type);
    }

    private void initHouseTypeList() {
        lv_house_type.setAdapter(adapter);
    }
}
