package silverlion.com.house;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import silverlion.com.house.commous.ActivityUtils;
import silverlion.com.house.components.MianRadioButton;
import silverlion.com.house.houselist.HouseListFragment;
import silverlion.com.house.message.MessageFragment;
import silverlion.com.house.myself.MySelfFragment;

public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener{
    private ActivityUtils activityUtils;
    private MySelfFragment mySelfFragment = new MySelfFragment();
    private HouseListFragment houseListFragment = new HouseListFragment();
    private MessageFragment messageFragment = new MessageFragment();
    private List<Fragment> list_fm = new ArrayList<Fragment>();
    private FragmentTransaction transaction;
    private  String accout_id;
    private FragmentManager manager;
    private long time;
    @Bind(R.id.house)RadioButton house;
    @Bind(R.id.message)RadioButton message;
    @Bind(R.id.myself)RadioButton myself;
    @Bind(R.id.group)RadioGroup group;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
        setContentView(R.layout.activity_main);
        initview();
        initFragment();
        showFragment(0);
        group.setOnCheckedChangeListener(this);
        house.setChecked(true);

    }

    private void initview(){
        Resources res = this.getResources();
        Drawable drawable = res.getDrawable(R.drawable.selector_house);
        drawable.setBounds(1, 1, 100, 100);
        house.setCompoundDrawables(null, drawable, null, null);

        Drawable drawable1 = res.getDrawable(R.drawable.selector_message);
        drawable1.setBounds(1, 1, 100, 100);
        message.setCompoundDrawables(null, drawable1, null, null);

        Drawable drawable2 = res.getDrawable(R.drawable.selector_myself);
        drawable2.setBounds(1, 1, 75, 100);
        myself.setCompoundDrawables(null, drawable2, null, null);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.house:
                showFragment(0);
                break;
            case R.id.message:
                showFragment(1);
                break;
            case R.id.myself:
                showFragment(2);
                break;
        }
    }

    private void initFragment() {
        houseListFragment = new HouseListFragment();
        messageFragment = new MessageFragment();
        mySelfFragment = new MySelfFragment();
        list_fm.add(houseListFragment);
        list_fm.add(messageFragment);
        list_fm.add(mySelfFragment);
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.add(R.id.frament, houseListFragment);
        transaction.add(R.id.frament, messageFragment);
        transaction.add(R.id.frament, mySelfFragment);
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
    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        long finaltime;
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finaltime = System.currentTimeMillis() - time;
            if (finaltime > 2000) {
                activityUtils.showToast("再按一次退出程序");
                time = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
