package com.zufangwang.activity;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.zufangwang.base.BaseFragment;
import com.zufangwang.base.DrawerBaseActivity;
import com.zufangwang.entity.GoodsInfo;
import com.zufangwang.fragment.GoodsDesFragment;
import com.zufangwang.fragment.GoodsListFragment;
import com.zufangwang.fragment.MyCollectFragment;
import com.zufangwang.fragment.MyNotifyFragment;
import com.zufangwang.francis.zufangwang.R;

/**
 * Created by nan on 2016/3/18.
 */
public class ChatActivity extends DrawerBaseActivity{
    private GoodsInfo goodsInfo;

    public GoodsInfo getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(GoodsInfo goodsInfo) {
        this.goodsInfo = goodsInfo;
        Log.i("ming","setGoodsInfo:  "+goodsInfo.toString());
//        getSupportFragmentManager().beginTransaction().replace(R.id.fl_main_container,new GoodsDesFragment()).commit();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_base_nevigation;
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initListener() {
        super.initListener();
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    @Override
    public void onItemClick(View view, int position) {
        super.onItemClick(view, position);
    }

    @Override
    protected void initToolBar() {
        super.initToolBar();
        setTitle(getIntent().getStringExtra("type"));
    }

    @Override
    protected Fragment getLayoutFragment() {
        BaseFragment fragment=null;
        switch (getIntent().getIntExtra("fragment_type",0)){
            case 1:
                fragment=new GoodsListFragment();
                break;
            case 2:
                fragment=new GoodsDesFragment();
                break;
            case 3:
                fragment=new MyCollectFragment();
                break;
            case 4:
                fragment=new MyNotifyFragment();
                break;
        }
        return fragment;
    }


}
