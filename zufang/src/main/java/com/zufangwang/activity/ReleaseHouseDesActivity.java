package com.zufangwang.activity;

import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;

import com.zufangwang.base.DrawerBaseActivity;
import com.zufangwang.entity.HouseInfo;
import com.zufangwang.fragment.ReleaseHouseDesFragment;
import com.zufangwang.francis.zufangwang.R;

/**
 * Created by Francis on 2016/4/28.
 */
public class ReleaseHouseDesActivity extends DrawerBaseActivity{
    ReleaseHouseDesFragment releaseHouseDesFragment=new ReleaseHouseDesFragment();
    HouseInfo houseInfo;
    @Override
    protected Fragment getLayoutFragment() {
        return releaseHouseDesFragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_base_nevigation;
    }

    @Override
    protected void initData() {
        houseInfo=(HouseInfo) getIntent().getSerializableExtra("house_info");
        super.initData();
        releaseHouseDesFragment.setHouseInfo(houseInfo);
    }

    @Override
    protected void loadData() {

    }
    @Override
    protected void initToolBar() {
        super.initToolBar();
        getSupportActionBar().setTitle("填写具体房源信息");
    }
    //发布成功后关掉当前Activity
    public void succeedRelease(){
        finish();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
            builder.setTitle("提示");
            builder.setMessage("信息尚未发布,确认离开吗?");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setNegativeButton("取消",null);
            builder.create().show();
        }
        return super.onKeyDown(keyCode, event);
    }
}
