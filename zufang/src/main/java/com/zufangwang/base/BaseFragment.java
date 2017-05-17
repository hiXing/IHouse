package com.zufangwang.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zufangwang.francis.zufangwang.R;

/**
 * Created by nan on 2016/3/14.
 */
public abstract class BaseFragment extends Fragment {

    protected Context mContext;
    protected View view;
    AlertDialog alert;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(getLayoutId(), container, false);
        initView();
        initData();
        initListener();
        loadData();
        return view;
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initListener();

    protected abstract void loadData();

    public void changeTabAdaterByPosition(int position){

    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void showLoadingDialog(Context context) {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        alert=builder.create();
        View view=LayoutInflater.from(context).inflate(R.layout.dialog_loading,null);
        alert.setView(view);
        alert.show();
    }

    public void closeLoadingDialog(){
        alert.dismiss();
    }


}
