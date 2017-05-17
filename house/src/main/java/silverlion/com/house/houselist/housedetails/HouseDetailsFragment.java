package silverlion.com.house.houselist.housedetails;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import com.hannesdorfmann.mosby.mvp.MvpFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import silverlion.com.house.R;
import silverlion.com.house.commous.ActivityUtils;
import silverlion.com.house.components.ProgressDialogFragment;
import silverlion.com.house.houselist.HouseActivity;
import silverlion.com.house.register.User;

public class HouseDetailsFragment extends MvpFragment<HouseDetailsView, HouseDetailsPersenter> implements HouseDetailsView, OnMapReadyCallback {
    private ActivityUtils activityUtils;
    private ProgressDialogFragment progressDialogFragment;
    private String list_id = HouseActivity.list_id;//获取房源id
    private String account_id = HouseActivity.accout_id;//获取用户id
    public static String promulgator_id;//发布者ID
    private GridDepolyAdapter adapter;
    private GridConvenienceAdapter convenienceAdapter;
    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.left)
    Button left_btn;
    @Bind(R.id.right)
    Button right_btn;
    //    @Bind(R.id.maps)
//    SupportMapFragment mapFragment;//google 地图
    @Bind(R.id.address)
    TextView address_tv;//地址
    @Bind(R.id.nature)
    TextView nature_tv;//房屋性质
    @Bind(R.id.house_code)
    TextView house_code;//房屋编号
    @Bind(R.id.place)
    TextView place_tv;//地区
    @Bind(R.id.house_class)
    TextView house_class;//房屋类型
    @Bind(R.id.house_status)
    TextView house_status;//房屋状态
    @Bind(R.id.price)
    TextView price_tv;//房屋价格
    @Bind(R.id.immigrant)
    TextView immigrant;//移民
    @Bind(R.id.usable_area)
    TextView usable_tv;//实用面积
    @Bind(R.id.area)
    TextView area_tv;//房子面积
    @Bind(R.id.house_area)
    TextView house_tv;//建筑面积
    @Bind(R.id.house_deploy)
    GridView grid_depoly;//配套
    @Bind(R.id.house_convenience)
    GridView grid_convenience;//便利性
    @Bind(R.id.user_iamge1)
    ImageView user_iv1;//用户信息图片1
    @Bind(R.id.user_iamge2)
    ImageView user_iv2;//用户信息图片2
    @Bind(R.id.linkman)
    TextView linkman_tv;//联系人
    @Bind(R.id.linkphone)
    TextView linkphone_tv;//联系电话
    @Bind(R.id.linkemail)
    TextView email_tv;//Email

    @Override
    public HouseDetailsPersenter createPresenter() {
        return new HouseDetailsPersenter();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_house_details, null);
        ButterKnife.bind(this, view);
//        SupportMapFragment mapFragment = new SupportMapFragment();
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.maps);
        mapFragment.getMapAsync(this);

//        Log.i("result", list_id);
//        Log.i("result", account_id);
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        presenter.HouseDetails(new User(list_id, account_id));
    }
    /**
     * 在跟新以后再实用这方法
     * */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.i("result", "我到这了吗");
//        googleMap.addMarker(new MarkerOptions()
//                .title("Sydney")
//                .snippet("The most populous city in Australia.")
//                .position(sydney));
        googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);//地图类型
        googleMap.setIndoorEnabled(false);
        LatLng sydney = new LatLng(-34, 151);
        googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"//这是设置点击title
        ).snippet("the most populious city in Australia"));//这是设置点击message
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void showProgress() {
        activityUtils.hideSoftKeyboard();
        if (progressDialogFragment == null) {
            progressDialogFragment = new ProgressDialogFragment();
        }
        if (progressDialogFragment.isVisible()) return;
        progressDialogFragment.show(getFragmentManager(), "fragment_progress_dialog");
    }

    @Override
    public void hideProgress() {
        progressDialogFragment.dismiss();
    }

    @Override
    public void showMessage(String msg) {
        activityUtils.showToast(msg);
    }

    @Override
    public void upDateUI(HouseDetailsResult result) {
        if (result == null)return;
        promulgator_id = result.getHouseInformationResults().getUserId();
        address_tv.setText(result.getAddress());
        nature_tv.setText(result.getHouse_nature());
        house_code.setText(result.getHouse_num());
        place_tv.setText(result.getPlace());
        house_class.setText(result.getHouse_type());
        house_status.setText(result.getHouse_status());
        price_tv.setText(result.getPrice()+result.getCoin());
        immigrant.setText(result.getImmigrant());
        usable_tv.setText(result.getPractical()+result.getArea_unit());
        area_tv.setText(result.getCover()+result.getArea_unit());
        house_tv.setText(result.getConstruaction()+result.getArea_unit());
        if (result.getHouseInformationResults().getRealName() != null){
            linkman_tv.setText(result.getHouseInformationResults().getRealName());
        }else {
            linkman_tv.setText(result.getHouseInformationResults().getCompanyName());
        }
        if (result.getHouseInformationResults().getCellphone() != null){
            linkphone_tv.setText(result.getHouseInformationResults().getCellphone());
        }else {
            linkphone_tv.setText(result.getHouseInformationResults().getTelephone());
        }
        if (result.getHouseInformationResults().getEmail() != null){
            email_tv.setText(result.getHouseInformationResults().getEmail());
        }else {
            email_tv.setText(result.getHouseInformationResults().getContactEmail());
        }
        if (result.getHouseDaployResults().size() <=3){
            ViewGroup.LayoutParams params = grid_depoly.getLayoutParams();
            params.height = 80;
            grid_depoly.setLayoutParams(params);
            adapter = new GridDepolyAdapter(getContext(),result.getHouseDaployResults());
            grid_depoly.setAdapter(adapter);
        }else if (result.getHouseDaployResults().size() >3 &&result.getHouseDaployResults().size()<7){
            ViewGroup.LayoutParams params = grid_depoly.getLayoutParams();
            params.height = 180;
            grid_depoly.setLayoutParams(params);
            adapter = new GridDepolyAdapter(getContext(),result.getHouseDaployResults());
            grid_depoly.setAdapter(adapter);
        }else if (result.getHouseDaployResults().size() >7 &&result.getHouseDaployResults().size()<10){
            ViewGroup.LayoutParams params = grid_depoly.getLayoutParams();
            params.height = 260;
            grid_depoly.setLayoutParams(params);
            adapter = new GridDepolyAdapter(getContext(),result.getHouseDaployResults());
            grid_depoly.setAdapter(adapter);
        }
        if (result.getConvenienceResults().size() <3){
            ViewGroup.LayoutParams params = grid_convenience.getLayoutParams();
            params.height = 80;
            grid_convenience.setLayoutParams(params);
            convenienceAdapter = new GridConvenienceAdapter(getContext(),result.getConvenienceResults());
            grid_convenience.setAdapter(convenienceAdapter);
        }else if (result.getConvenienceResults().size() >3 &&result.getConvenienceResults().size()<7){
            ViewGroup.LayoutParams params = grid_convenience.getLayoutParams();
            params.height = 180;
            grid_convenience.setLayoutParams(params);
            convenienceAdapter = new GridConvenienceAdapter(getContext(),result.getConvenienceResults());
            grid_convenience.setAdapter(convenienceAdapter);
        }else if (result.getConvenienceResults().size() >7 &&result.getConvenienceResults().size()<10){
            ViewGroup.LayoutParams params = grid_convenience.getLayoutParams();
            params.height = 260;
            grid_convenience.setLayoutParams(params);
            convenienceAdapter = new GridConvenienceAdapter(getContext(),result.getConvenienceResults());
            grid_convenience.setAdapter(convenienceAdapter);
        }

    }
}
