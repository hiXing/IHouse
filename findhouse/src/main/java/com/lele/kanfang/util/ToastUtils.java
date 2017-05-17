package com.lele.kanfang.util;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;

import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperToast;
import com.lele.kanfang.R;

/**
 * Created by wuping on 2017/1/9.
 */

public class ToastUtils {

    public static void showDefaultSuperToast(Context context, int resId) {
        SuperToast.cancelAllSuperToasts();
        SuperToast s = new SuperToast(context);
        s.setDuration(Style.DURATION_SHORT);
        s.setAnimations(Style.ANIMATIONS_POP);
        s.setColor(ContextCompat.getColor(context,R.color.yellow));
        s.setGravity(Gravity.TOP);
        s.setText(context.getString(resId));
        s.show();
        s = null;
    }

    public static void showDefaultSuperToast(Context context, String str) {
        SuperToast.cancelAllSuperToasts();
        SuperToast s = new SuperToast(context);
        s.setDuration(Style.DURATION_SHORT);
        s.setAnimations(Style.ANIMATIONS_POP);
        s.setColor(ContextCompat.getColor(context,R.color.yellow));
        s.setGravity(Gravity.TOP);
        s.setText(str);
        s.show();
        s = null;

    }

    public static void showDefaultSuperToastNoCancel(Context context, int resId) {
        SuperToast.cancelAllSuperToasts();
        SuperToast s = new SuperToast(context);
        s.setDuration(Style.DURATION_SHORT);
        s.setAnimations(Style.ANIMATIONS_POP);
        s.setColor(ContextCompat.getColor(context,R.color.yellow));
        s.setGravity(Gravity.TOP);
        s.setText(context.getString(resId));
        s.show();
        s = null;
    }

    public static void showDefaultSuperToastNoCancel(Context context, String str) {
        SuperToast.cancelAllSuperToasts();
        SuperToast s = new SuperToast(context);
        s.setDuration(Style.DURATION_SHORT);
        s.setAnimations(Style.ANIMATIONS_POP);
        s.setColor(ContextCompat.getColor(context,R.color.yellow));
        s.setGravity(Gravity.TOP);
        s.setText(str);
        s.show();
        s = null;
    }

}
