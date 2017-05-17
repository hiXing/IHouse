package com.zufangwang.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.zufangwang.base.BaseActivity;
import com.zufangwang.base.Configs;
import com.zufangwang.base.DrawerBaseActivity;
import com.zufangwang.entity.CollectInfo;
import com.zufangwang.entity.HouseInfo;
import com.zufangwang.fragment.HouseDesFragment;
import com.zufangwang.francis.zufangwang.R;
import com.zufangwang.utils.OkHttpClientManager;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Francis on 2016/4/23.
 */
public class HouseDesActivity extends DrawerBaseActivity{
    HouseDesFragment houseDesFragment=new HouseDesFragment();
    HouseInfo houseInfo;
    boolean isCollect=false;
    MenuItem itemCollect;
    @Override
    protected Fragment getLayoutFragment() {
        return houseDesFragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_base_nevigation;
    }

    @Override
    protected void initData() {
        super.initData();
        houseInfo=(HouseInfo) getIntent().getSerializableExtra("house_info");
//        getCollectInfo();
        houseDesFragment.setHouseInfo(houseInfo);
    }

    private void getCollectInfo() {
        OkHttpClientManager.postAsyn(Configs.GETCOLLECT_A_HOUSE, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(getApplication(),Configs.URLERROR,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                Log.i("ming","url response:  "+response);
//                collectInfo=new Gson().fromJson(response,CollectInfo.class);
                if (!response.equals("0")){
                    isCollect=true;
                    itemCollect.setIcon(getResources().getDrawable(R.drawable.wb_collected_normal));
                }
                else{
                    isCollect=false;
                    itemCollect.setIcon(getResources().getDrawable(R.drawable.ic_grade));
                }
            }
        },new OkHttpClientManager.Param[]{
                new OkHttpClientManager.Param("house_no",String.valueOf(houseInfo.getHouse_no())),
                new OkHttpClientManager.Param("user_name",getSharedPreferences("user",0).getString("user_name","")),
                new OkHttpClientManager.Param("user_id",getSharedPreferences("user",0).getString("user_id",""))
        });
    }

    @Override
    protected void loadData() {

    }
    @Override
    protected void initToolBar() {
        super.initToolBar();
        getSupportActionBar().setTitle("详情");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.house_des_menu, menu);
        itemCollect=menu.findItem(R.id.collect);
        getCollectInfo();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.collect:
                if (isCollect){
                    item.setIcon(getResources().getDrawable(R.drawable.ic_grade));
                    isCollect=!isCollect;
                    collectHandle();
                }
                else{
                    item.setIcon(getResources().getDrawable(R.drawable.wb_collected_normal));
                    isCollect=!isCollect;
                    collectHandle();
                }

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void collectHandle() {
        String url="";
        if (!isCollect)
            url= Configs.DELETECOLLECT_HOUSE;
        else
            url=Configs.ADDCOLLECT_HOUSE;
        Log.i("ming","url:  "+url);
        OkHttpClientManager.postAsyn(url, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(getApplication(),Configs.URLERROR,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                Log.i("ming","url response:  "+response);
                if (response.equals("1")){
                    String message="";
                    if (isCollect)
                        message="添加收藏成功";
                    else
                        message="移除收藏成功";
                    Toast.makeText(getApplication(),message,Toast.LENGTH_SHORT).show();
                }
            }
        },new OkHttpClientManager.Param[]{
                new OkHttpClientManager.Param("house_no",String.valueOf(houseInfo.getHouse_no())),
                new OkHttpClientManager.Param("user_name",getSharedPreferences("user",0).getString("user_name","")),
                new OkHttpClientManager.Param("user_id",getSharedPreferences("user",0).getString("user_id",""))
        });
    }
}
