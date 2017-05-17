package com.zufangwang.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.zufangwang.activity.ReleaseHouseDesActivity;
import com.zufangwang.base.Configs;
import com.zufangwang.entity.HouseDesInfo;
import com.zufangwang.entity.HouseInfo;
import com.zufangwang.francis.zufangwang.R;
import com.zufangwang.utils.OkHttpClientManager;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Francis on 2016/4/28.
 */
public class ReleaseHouseDesFragment extends Fragment implements View.OnClickListener,AdapterView.OnItemSelectedListener,CompoundButton.OnCheckedChangeListener{
    @InjectView(R.id.house_renovation)
    Spinner houseRenovation;
    @InjectView(R.id.house_orientation)
    Spinner houseOrientation;
    @InjectView(R.id.et_house_limit)
    EditText etHouseLimit;
    @InjectView(R.id.check_broadband)
    CheckBox checkBroadband;
    @InjectView(R.id.check_video)
    CheckBox checkVideo;
    @InjectView(R.id.check_sofa)
    CheckBox checkSofa;
    @InjectView(R.id.check_washing)
    CheckBox checkWashing;
    @InjectView(R.id.check_bed)
    CheckBox checkBed;
    @InjectView(R.id.check_refrigerator)
    CheckBox checkRefrigerator;
    @InjectView(R.id.check_air)
    CheckBox checkAir;
    @InjectView(R.id.check_heating)
    CheckBox checkHeating;
    @InjectView(R.id.check_wardrobe)
    CheckBox checkWardrobe;
    @InjectView(R.id.check_heater)
    CheckBox checkHeater;
    @InjectView(R.id.btn_release)
    Button btnRelease;

    private HouseInfo houseInfo;
    private HouseDesInfo houseDesInfo;
    private String[] renovation,orientation;
    private Context context;

    public void setHouseInfo(HouseInfo houseInfo) {
        this.houseInfo = houseInfo;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_release_des_house, null);
        ButterKnife.inject(this, view);

        initData();
        initListener();
        initSpin();
        return view;
    }

    private void initListener() {
        houseRenovation.setOnItemSelectedListener(this);
        houseOrientation.setOnItemSelectedListener(this);
        checkBroadband.setOnCheckedChangeListener(this);
        checkVideo.setOnCheckedChangeListener(this);
        checkSofa.setOnCheckedChangeListener(this);
        checkWashing.setOnCheckedChangeListener(this);
        checkBed.setOnCheckedChangeListener(this);
        checkRefrigerator.setOnCheckedChangeListener(this);
        checkAir.setOnCheckedChangeListener(this);
        checkHeating.setOnCheckedChangeListener(this);
        checkWardrobe.setOnCheckedChangeListener(this);
        checkHeater.setOnCheckedChangeListener(this);

    }

    private void initSpin() {

    }

    private void initData() {
        context=getActivity();
        houseDesInfo=new HouseDesInfo();
        renovation=context.getResources().getStringArray(R.array.house_renovation_type);
        orientation=context.getResources().getStringArray(R.array.house_orientation);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick(R.id.btn_release)
    public void onClick() {
        releaseHouse();
    }

    private void releaseHouse() {
        if (etHouseLimit.getText().toString().equals("")){
            Toast.makeText(context,"限制不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        houseDesInfo.setHouse_limit(etHouseLimit.getText().toString());
        AlertDialog.Builder build=new AlertDialog.Builder(getActivity());
        build.setTitle("发布房源");
        build.setMessage("是否发布该房源");
        build.setNegativeButton("取消",null);
        build.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("ming","housedesinfof  :"+houseDesInfo.toString());
                houseInfo.setHouse_des_info(new Gson().toJson(houseDesInfo));
                Log.i("ming","houseinfo  :"+houseInfo.toString());

                OkHttpClientManager.postAsyn(Configs.ADD_HOUSE, new OkHttpClientManager.ResultCallback<String>() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Toast.makeText(context, Configs.URLERROR, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response) {
                        Log.i("ming","ADD_HOUSE response:   "+response);
                        if (response.equals("1")) {
                            Toast.makeText(context, "房源发布成功", Toast.LENGTH_SHORT).show();
                            ((ReleaseHouseDesActivity)context).succeedRelease();
                        }
                    }
                },new OkHttpClientManager.Param("house_info",new Gson().toJson(houseInfo)));

            }
        });
        build.create().show();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.house_renovation:
                houseDesInfo.setHouse_renovation(renovation[position]);
                break;
            case R.id.house_orientation:
                houseDesInfo.setHouse_orientation(orientation[position]);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.check_broadband:
                houseDesInfo.setBroadband(isChecked);
                break;
            case R.id.check_video:
                houseDesInfo.setVideo(isChecked);
                break;
            case R.id.check_sofa:
                houseDesInfo.setSofa(isChecked);
                break;
            case R.id.check_washing:
                houseDesInfo.setWashing(isChecked);
                break;
            case R.id.check_bed:
                houseDesInfo.setBed(isChecked);
                break;
            case R.id.check_refrigerator:
                houseDesInfo.setRefrigerator(isChecked);
                break;
            case R.id.check_air:
                houseDesInfo.setAir(isChecked);
                break;
            case R.id.check_heating:
                houseDesInfo.setHeating(isChecked);
                break;
            case R.id.check_wardrobe:
                houseDesInfo.setWardrobe(isChecked);
                break;
            case R.id.check_heater:
                houseDesInfo.setHeater(isChecked);
                break;

        }
    }
}
