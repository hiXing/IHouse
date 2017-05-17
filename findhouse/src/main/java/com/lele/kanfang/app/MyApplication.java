package com.lele.kanfang.app;

import android.app.Application;

/**
 * Created by wuping on 2017/1/9.
 */

public class MyApplication extends Application {

    private static MyApplication app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public synchronized static MyApplication getAppContext() {
        return app;
    }
}
