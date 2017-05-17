package com.zufangwang.base;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.zufangwang.francis.zufangwang.R;
import com.zufangwang.utils.ActivityCollector;


public abstract class BaseActivity extends AppCompatActivity {


    protected Context mContext;
    AlertDialog alert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getContentViewId());
        initVariables();
        initView();
        initData();
        initListener();
        loadData();

    }

    protected abstract int getContentViewId();


    protected void initVariables() {
        mContext = this;
        ActivityCollector.addActivity(this);
    }

    ;


    protected abstract void initView();

    ;


    protected abstract void initData()


            ;


    protected abstract void initListener()


            ;

    protected abstract void loadData();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    protected void showToast(String text, int duration) {
        Toast.makeText(mContext, text, duration).show();
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

//    public void showLoadingDialog(Context context) {
//        AlertDialog.Builder builder=new AlertDialog.Builder(context);
//        alert=builder.create();
//        View view= LayoutInflater.from(context).inflate(R.layout.dialog_loading,null);
//        alert.setView(view);
//        alert.show();
//    }
//
//    public void closeLoadingDialog(){
//        alert.dismiss();
//    }

}
