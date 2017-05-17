package com.sky.widget;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.widget.TextView;

public class WHawkTimerBtn extends TextView {

    private TimeCount timer;
    private int time = 60;

    public WHawkTimerBtn(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void start(){
        this.setBackgroundResource(R.drawable.validate_clicked_border);
        this.setTextColor(Color.parseColor("#ffffff"));
        this.setClickable(false);
        timer = new TimeCount(time * 1000, 1000);
        timer.start();
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            WHawkTimerBtn.this.setBackgroundResource(R.drawable.send_order_info_tv);
//			WHawkTimerBtn.this.setTextColor(Color.parseColor("#fd7b24"));
            WHawkTimerBtn.this.setTextColor(getResources().getColor(R.color.colorMain));
            WHawkTimerBtn.this.setText("重新发送");
            WHawkTimerBtn.this.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            WHawkTimerBtn.this.setText(millisUntilFinished / 1000 + "秒后重发");
        }
    }
}
