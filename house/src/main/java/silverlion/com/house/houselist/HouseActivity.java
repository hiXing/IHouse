package silverlion.com.house.houselist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.hannesdorfmann.mosby.mvp.MvpActivity;

import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import silverlion.com.house.R;
import silverlion.com.house.commous.ActivityUtils;
import silverlion.com.house.houselist.housedetails.HouseDetailsFragment;
import silverlion.com.house.houselist.housemark.HouseMarkFragment;
import silverlion.com.house.register.User;

public class HouseActivity extends MvpActivity<HouseView,HousePersenter> implements RadioGroup.OnCheckedChangeListener,HouseView{
    private ActivityUtils activityUtils;
    private List<Fragment> list_fm = new ArrayList<Fragment>();
    private FragmentTransaction transaction;
    private FragmentManager manager;
    private HouseDetailsFragment detailsFragment ;
    private HouseMarkFragment markFragment ;
    public static String accout_id,list_id;
    @Bind(R.id.group)RadioGroup group;
    @Bind(R.id.details)RadioButton details;
    @Bind(R.id.collect)CheckBox collect_iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
        setContentView(R.layout.activity_house);
        Intent intent = getIntent();//
        list_id = intent.getStringExtra("id");
        SharedPreferences spf = getSharedPreferences("first",0);
        accout_id = spf.getString("user_id", null);
        initFragment();
        details.setChecked(true);
    }

    @OnCheckedChanged(R.id.collect)
    public void onChangeClick(boolean ischange){
        if (ischange){
            presenter.Collect(new User(list_id,accout_id));//收藏
        }else if (!ischange){
            presenter.Cancel(new User(list_id,accout_id));//取消
        }
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        group.setOnCheckedChangeListener(this);
    }

    @Override
    public void shouMessage(String msg) {
        activityUtils.showToast(msg);
    }

    @NonNull
    @Override
    public HousePersenter createPresenter() {
        return new HousePersenter();
    }

    @OnClick({R.id.cancel})
    public void onCilck(View view){
        switch (view.getId()){
            case R.id.cancel:
                finish();
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.details:
                    showFragment(0);
                break;
            case R.id.mark:
                    showFragment(1);
                break;
        }
    }
    private void initFragment() {
        detailsFragment = new HouseDetailsFragment();
        markFragment = new HouseMarkFragment();
        list_fm.add(detailsFragment);
        list_fm.add(markFragment);
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.add(R.id.frament, detailsFragment);
        transaction.add(R.id.frament, markFragment);
        transaction.commit();
    }

    private void showFragment(int i) {
        FragmentTransaction transaction2 = manager.beginTransaction();
        for (int j = 0; j <list_fm.size(); j++) {
            if (i==j) {
                transaction2.show(list_fm.get(j));
            }else {
                transaction2.hide(list_fm.get(j));
            }
        }
        transaction2.commit();
    }
}
