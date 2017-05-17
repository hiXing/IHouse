package com.zufangwang.activity;

import android.support.v4.app.Fragment;

import com.zufangwang.base.DrawerBaseActivity;
import com.zufangwang.entity.HouseInfo;
import com.zufangwang.fragment.RentalFragment;
import com.zufangwang.francis.zufangwang.R;

/**
 * Created by Francis on 2016/4/28.
 */
public class RentalActivity extends DrawerBaseActivity {
    RentalFragment rentalFragment=new RentalFragment();
    @Override
    protected Fragment getLayoutFragment() {
        return rentalFragment;
    }

    @Override
    protected int getContentViewId()  {
        return R.layout.activity_base_nevigation;
    }

    @Override
    protected void initData() {
        rentalFragment.setPrice(getIntent().getStringExtra("house_price"));
        rentalFragment.setHouseInfo((HouseInfo) getIntent().getSerializableExtra("house_info"));
        super.initData();
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initToolBar() {
        super.initToolBar();
        getSupportActionBar().setTitle("选择日期");
    }

    public void getSucceed(){
        finish();
    }
}
