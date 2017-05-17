package com.zufangwang.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.zufangwang.activity.HouseDesActivity;
import com.zufangwang.activity.HouseList1Activity;
import com.zufangwang.adapter.HouseItemAdapter;
import com.zufangwang.base.BaseFragment;
import com.zufangwang.base.Configs;
import com.zufangwang.entity.HouseInfo;
import com.zufangwang.francis.zufangwang.R;
import com.zufangwang.listener.OnItemClickListener;
import com.zufangwang.utils.OkHttpClientManager;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 首页fragment
 * Created by Francis on 2016/4/22.
 */
public class HomePageFragment extends BaseFragment {
    @InjectView(R.id.recycle_house)
    RecyclerView recycleHouse;
    @InjectView(R.id.tv_more_house)
    TextView tvMoreHouse;
    @InjectView(R.id.tv_rental)
    TextView tvRental;
    @InjectView(R.id.tv_individual_housing)
    TextView tvIndividualHousing;
    @InjectView(R.id.tv_spacious_master_bedroom)
    TextView tvSpaciousMasterBedroom;
    @InjectView(R.id.tv_charge_one_to_pay)
    TextView tvChargeOneToPay;
    @InjectView(R.id.tv_easy_communication)
    TextView tvEasyCommunication;
    @InjectView(R.id.tv_quality_short_rent)
    TextView tvQualityShortRent;
    @InjectView(R.id.tv_single_apartment)
    TextView tvSingleApartment;
    @InjectView(R.id.tv_second_hand_housing)
    TextView tvSecondHandHousing;
    @InjectView(R.id.tv_price_listings)
    TextView tvPriceListings;
    @InjectView(R.id.tv_just_need_to_home)
    TextView tvJustNeedToHome;
    @InjectView(R.id.tv_school_district_housing)
    TextView tvSchoolDistrictHousing;
    @InjectView(R.id.tv_tax_exempt_listings)
    TextView tvTaxExemptListings;
    @InjectView(R.id.tv_improve_sanju)
    TextView tvImproveSanju;
    @InjectView(R.id.tv_improvement_room)
    TextView tvImprovementRoom;
    @InjectView(R.id.tv_new_house)
    TextView tvNewHouse;
    @InjectView(R.id.tv_quality_new_plate)
    TextView tvQualityNewPlate;
    @InjectView(R.id.tv_two_bedroom_houses)
    TextView tvTwoBedroomHouses;
    @InjectView(R.id.tv_three_bedroom_houses)
    TextView tvThreeBedroomHouses;
    @InjectView(R.id.tv_shops)
    TextView tvShops;
    @InjectView(R.id.tv_rental_shops)
    TextView tvRentalShops;
    @InjectView(R.id.tv_shop_for_sale)
    TextView tvShopForSale;
    @InjectView(R.id.tv_business_transfer)
    TextView tvBusinessTransfer;
    @InjectView(R.id.tv_rental_office)
    TextView tvRentalOffice;
    @InjectView(R.id.tv_rental_warehouse)
    TextView tvRentalWarehouse;
    @InjectView(R.id.tv_rental_plant)
    TextView tvRentalPlant;

    private static final int GET_HOUSE = 100;
    AlertDialog alert;

    private HouseItemAdapter houseItemAdapter;
    private Intent intent;
    private ArrayList<HouseInfo> houseInfos;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GET_HOUSE:
                    try {
                        JSONArray data = new JSONArray(msg.obj.toString());
                        for (int i = 0; i < data.length(); i++) {
                            HouseInfo houseInfo = new Gson().fromJson(data.getString(i), HouseInfo.class);
                            houseInfos.add(houseInfo);
                            Log.i("ming", "house:  " + houseInfo.toString());
                        }
                        showHouseList();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }

            super.handleMessage(msg);
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_page;
    }

    @Override
    protected void initView() {
        Log.i("ming", "initView");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void loadData() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.inject(this, rootView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleHouse.setLayoutManager(layoutManager);

//        getHouseInfo();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getHouseInfo();
    }

    //获得所有房源
    private void getHouseInfo() {
        showLoadingDialog(getContext());
        houseInfos = new ArrayList<>();
        OkHttpClientManager.getAsyn(Configs.QUERY_ALL_HOUSE, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(mContext, Configs.URLERROR, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                Log.i("ming", "house   response:　　" + response);
                Message message = new Message();
                message.what = GET_HOUSE;
                message.obj = response;
                mHandler.sendMessage(message);


            }
        });
    }

//    private void showLoadingDialog() {
//        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
//        alert=builder.create();
//        View view=LayoutInflater.from(getContext()).inflate(R.layout.dialog_loading,null);
//        alert.setView(view);
//        alert.show();
//    }

    //显示最近发布的两条房源信息
    private void showHouseList() {
//        alert.dismiss();
        closeLoadingDialog();
        if (houseInfos.size() <= 0) {
            recycleHouse.setVisibility(View.GONE);
            return;
        }
        recycleHouse.setVisibility(View.VISIBLE);
        ArrayList<HouseInfo> data = new ArrayList<>();
        if (houseInfos.size() > 2) {
            data.add(houseInfos.get(0));
            data.add(houseInfos.get(1));
        } else
            data = houseInfos;
        houseItemAdapter = new HouseItemAdapter(mContext, data);
        recycleHouse.setAdapter(houseItemAdapter);
        clickItem();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.tv_more_house, R.id.tv_individual_housing, R.id.tv_spacious_master_bedroom, R.id.tv_charge_one_to_pay, R.id.tv_easy_communication, R.id.tv_quality_short_rent, R.id.tv_single_apartment, R.id.tv_price_listings, R.id.tv_just_need_to_home, R.id.tv_school_district_housing, R.id.tv_tax_exempt_listings, R.id.tv_improve_sanju, R.id.tv_improvement_room, R.id.tv_quality_new_plate, R.id.tv_two_bedroom_houses,
            R.id.tv_three_bedroom_houses, R.id.tv_rental_shops, R.id.tv_shop_for_sale, R.id.tv_business_transfer,R.id.tv_rental_office,R.id.tv_rental_warehouse,R.id.tv_rental_plant})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_more_house:
                skipActivity("", tvMoreHouse.getText().toString());
                break;
            case R.id.tv_individual_housing:
                skipActivity("租房", tvIndividualHousing.getText().toString());
                break;
            case R.id.tv_spacious_master_bedroom:
                skipActivity("租房", tvSpaciousMasterBedroom.getText().toString());
                break;
            case R.id.tv_charge_one_to_pay:
                skipActivity("租房", tvChargeOneToPay.getText().toString());
                break;
            case R.id.tv_easy_communication:
                skipActivity("租房", tvEasyCommunication.getText().toString());
                break;
            case R.id.tv_quality_short_rent:
                skipActivity("租房", tvQualityShortRent.getText().toString());
                break;
            case R.id.tv_single_apartment:
                skipActivity("租房", tvSingleApartment.getText().toString());
                break;
            case R.id.tv_price_listings:
                skipActivity("二手房", tvPriceListings.getText().toString());
                break;
            case R.id.tv_just_need_to_home:
                skipActivity("二手房", tvJustNeedToHome.getText().toString());
                break;
            case R.id.tv_school_district_housing:
                skipActivity("二手房", tvSchoolDistrictHousing.getText().toString());
                break;
            case R.id.tv_tax_exempt_listings:
                skipActivity("二手房", tvTaxExemptListings.getText().toString());
                break;
            case R.id.tv_improve_sanju:
                skipActivity("二手房", tvImproveSanju.getText().toString());
                break;
            case R.id.tv_improvement_room:
                skipActivity("二手房", tvImprovementRoom.getText().toString());
                break;
            case R.id.tv_quality_new_plate:
                skipActivity("新房", tvQualityNewPlate.getText().toString());
                break;
            case R.id.tv_two_bedroom_houses:
                skipActivity("新房", tvTwoBedroomHouses.getText().toString());
                break;
            case R.id.tv_three_bedroom_houses:
                skipActivity("新房", tvThreeBedroomHouses.getText().toString());
                break;
            case R.id.tv_rental_shops:
                skipActivity("商铺", tvRentalShops.getText().toString());
                break;
            case R.id.tv_shop_for_sale:
                skipActivity("商铺", tvShopForSale.getText().toString());
                break;
            case R.id.tv_business_transfer:
                skipActivity("商铺", tvBusinessTransfer.getText().toString());
                break;
            case R.id.tv_rental_office:
                skipActivity("商业地产", tvRentalOffice.getText().toString());
                break;
            case R.id.tv_rental_warehouse:
                skipActivity("商业地产", tvRentalWarehouse.getText().toString());
                break;
            case R.id.tv_rental_plant:
                skipActivity("商业地产", tvRentalPlant.getText().toString());
                break;

        }
    }

    private void skipActivity(String s, String s1) {
        intent = new Intent(getActivity(), HouseList1Activity.class);
        intent.putExtra("house_condition", s1);
        intent.putExtra("house_type", s);
        Log.i("ming","house_condition:  "+s1+"  housetyep:  "+s);
        startActivity(intent);
    }

    //点击房源item
    private void clickItem() {
        houseItemAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), HouseDesActivity.class);
                intent.putExtra("house_info", houseInfos.get(position));
                startActivity(intent);
            }
        });
    }
}
