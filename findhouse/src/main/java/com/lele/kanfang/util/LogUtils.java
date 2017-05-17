package com.lele.kanfang.util;

import android.util.Log;

import com.lele.kanfang.app.Constants;

/**
 * Created by wuping on 2017/1/9.
 */

public class LogUtils {

    private static final String TAG = "FindHourse";

    // 下面四个是默认tag的函数
    public static void i(String msg)
    {
        if (Constants.isDebug)
            Log.i(TAG, msg);
    }

    public static void d(String msg)
    {
        if (Constants.isDebug)
            Log.d(TAG, msg);
    }

    public static void e(String msg)
    {
        if (Constants.isDebug)
            Log.e(TAG, msg);
    }

    public static void v(String msg)
    {
        if (Constants.isDebug)
            Log.v(TAG, msg);
    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg)
    {
        if (Constants.isDebug)
            Log.i(tag, msg);
    }

    public static void d(String tag, String msg)
    {
        if (Constants.isDebug)
            Log.i(tag, msg);
    }

    public static void e(String tag, String msg)
    {
        if (Constants.isDebug)
            Log.i(tag, msg);
    }

    public static void v(String tag, String msg)
    {
        if (Constants.isDebug)
            Log.i(tag, msg);
    }
}
