package com.zufangwang.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.zufangwang.activity.MainActivity;
import com.zufangwang.base.BaseFragment;
import com.zufangwang.base.DrawerBaseActivity;
import com.zufangwang.francis.zufangwang.R;

/**
 * Created by nan on 2016/3/19.
 */
public class MainFragment extends BaseFragment implements View.OnClickListener {

    //    view
    private TextView mCourseText, mMessageText;


    //变量
    //fragment管理器
    private FragmentManager mFragmentManager;
    private MainCourseFragment mCouresFragment;
    private ReleaseFragment mMessageFragment;
    private MarketFragment mMarketFragment;
    private HomePageFragment mHomePageFragment;
    private HouseTypeFragment mHouseReleaseFragment;

    public Fragment getmCurrentFragment() {
        return mCurrentFragment;
    }

    //当前页面显示的fragment
    private Fragment mCurrentFragment;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initView() {
        mCourseText = (TextView) view.findViewById(R.id.tv_main_course);
        mMessageText = (TextView) view.findViewById(R.id.tv_main_message);
    }

    @Override
    protected void initData() {
        initFragment();
    }

    @Override
    protected void initListener() {
        mCourseText.setOnClickListener(this);
        mMessageText.setOnClickListener(this);
    }

    @Override
    protected void loadData() {

    }

    private void initFragment() {
        mCouresFragment = new MainCourseFragment();
        mMessageFragment = new ReleaseFragment();
        mHouseReleaseFragment=new HouseTypeFragment();
        mMarketFragment=new MarketFragment();
        mHomePageFragment=new HomePageFragment();
//        mCurrentFragment = mCouresFragment;
//        mCurrentFragment=mMarketFragment;
        mCurrentFragment=mHomePageFragment;
        mFragmentManager = ((AppCompatActivity) mContext).getSupportFragmentManager();
//        mFragmentManager.beginTransaction().add(R.id.fragment_main_mainContainer, mCouresFragment).commit();
//        mFragmentManager.beginTransaction().add(R.id.fragment_main_mainContainer, mMarketFragment).commit();
        mFragmentManager.beginTransaction().add(R.id.fragment_main_mainContainer, mHomePageFragment).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_main_course:
                changeText(DrawerBaseActivity.COURSE);
                break;
            case R.id.tv_main_message:
                changeText(DrawerBaseActivity.MESSAGE);
                break;


            default:
                break;
        }
    }

    public void changeText(int type) {
        View v;
        if (type == DrawerBaseActivity.COURSE) {
//            if (mCurrentFragment == mCouresFragment) {
//                return;
//            }
//            if (mCurrentFragment == mMarketFragment) {
//                return;
//            }
            if (mCurrentFragment == mHomePageFragment) {
                return;
            }
            v = mCourseText;
//            mCurrentFragment = mCouresFragment;
//            mCurrentFragment=mMarketFragment;
            mCurrentFragment=mHomePageFragment;
        } else {
//            if (mCurrentFragment == mMessageFragment) {
//                return;
//            }
            if (mCurrentFragment == mHouseReleaseFragment) {
                return;
            }
            v = mMessageText;
//            mCurrentFragment = mMessageFragment;
            mCurrentFragment = mHouseReleaseFragment;
        }
        if (v.getId() == R.id.tv_main_course) {
            ((AppCompatActivity) mContext).getSupportActionBar().setTitle("二手市场");
//            mFragmentManager.beginTransaction().replace(R.id.fragment_main_mainContainer, mCouresFragment).commit();
//            mFragmentManager.beginTransaction().replace(R.id.fragment_main_mainContainer, mMarketFragment).commit();
            mFragmentManager.beginTransaction().replace(R.id.fragment_main_mainContainer, mHomePageFragment).commit();
            ((TextView) v).setTextColor(getResources().getColor(R.color.colorBottomTextSelected));
            mMessageText.setTextColor(getResources().getColor(R.color.colorBottomTextNoSelected));
            ((MainActivity) mContext).selectNevigationText(DrawerBaseActivity.COURSE);
        } else {
            ((AppCompatActivity) mContext).getSupportActionBar().setTitle("选择发布类别");
//            mFragmentManager.beginTransaction().replace(R.id.fragment_main_mainContainer, mMessageFragment).commit();
            mFragmentManager.beginTransaction().replace(R.id.fragment_main_mainContainer, mHouseReleaseFragment).commit();
            ((TextView) v).setTextColor(getResources().getColor(R.color.colorBottomTextSelected));
            mCourseText.setTextColor(getResources().getColor(R.color.colorBottomTextNoSelected));
            ((MainActivity) mContext).selectNevigationText(DrawerBaseActivity.MESSAGE);

        }

//        ((MainActivity) mContext).setmCurrentFragment(mCouresFragment);
//        ((MainActivity) mContext).setmCurrentFragment(mMarketFragment);
        ((MainActivity) mContext).setmCurrentFragment(mHomePageFragment);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
