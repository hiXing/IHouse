package com.zufangwang.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zufangwang.fragment.SearchTabFragment;
import com.zufangwang.francis.zufangwang.R;

/**
 * Created by nan on 2016/3/21.
 */
public class SearchTabPagerAdapter extends FragmentPagerAdapter {
    String[] mSearchTabs;

    public SearchTabPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mSearchTabs = context.getResources().getStringArray(R.array.search_tabs);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mSearchTabs[position];
    }

    @Override
    public Fragment getItem(int position) {
        return SearchTabFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return mSearchTabs.length;
    }
}
