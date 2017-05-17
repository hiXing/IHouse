package com.zufangwang.application;

import android.app.Application;

import com.zufangwang.utils.AppContextUtils;


/**
 * Created by nan on 2016/3/10.
 */
public class kechengpaiApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppContextUtils.initContext(this);

    }
}
