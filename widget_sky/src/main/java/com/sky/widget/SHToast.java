package com.sky.widget;

import android.content.Context;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 自定义提示Toast
 * 
 * @author skypan
 */
public class SHToast extends Toast {

	public static Toast mToast = null;
	/** toast位置，0表示默认底部 */
	public static int POSITION_DEFAULT = 0;
	/** toast位置，1表示居中 */
	public static int POSITION_CENTER = 1;
	private static Context c;

	public SHToast(Context context) {
		super(context);
	}

	public static void showToast(Context context, CharSequence text) {
		c = context;
		if (mToast != null) {
			mToast.cancel();
		}
		mToast = makeText(context, text, 1000,POSITION_DEFAULT);
		mToast.show();
	}
	
	/**
	 * 显示toast,位置默认底部
	 * 
	 * @param context
	 * @param text
	 * @param duration
	 *          SHToast.LENGTH_LONG SHToast.LENGTH_SHORT
	 */
	public static void showToast(Context context, CharSequence text, int duration) {
		c = context;
		if (mToast != null) {
			mToast.cancel();
		}
		mToast = makeText(context, text, duration,POSITION_DEFAULT);
		mToast.show();
	}

	/**
	 * 
	 * @param context
	 * @param text
	 * @param duration
	 * 			默认底部SHToast.DEFAULT 居中SHToast。CENTER
	 * @param position
	 * 			SHToast.LENGTH_LONG SHToast.LENGTH_SHORT
	 */
	public static void showToast(Context context, CharSequence text, int duration, int position) {
		c = context;
		if (mToast != null) {
			mToast.cancel();
		}
		mToast = makeText(context, text, duration, position);
		mToast.show();
	}

	/**
	 * 带图片的toast
	 * @param context
	 * @param iconResId 图片资源ID
	 * @param text
	 * @param duration
	 * 			SHToast.LENGTH_LONG SHToast.LENGTH_SHORT
	 */
	public static void showToast(Context context, int iconResId, CharSequence text, int duration) {
		c = context;
		if (mToast != null) {
			mToast.cancel();
		}
		mToast = makeText(context, iconResId, text, duration);
		mToast.show();
	}

	/**
	 * 取消toast
	 */
	public static void cancelToast() {
		if (mToast != null) {
			mToast.cancel();
			mToast = null;
		}
	}

	public static SHToast makeText(Context context, CharSequence text, int duration, int position) {
		SHToast result = new SHToast(context);

		LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflate.inflate(R.layout.toast_bg, null);
		LinearLayout layout_main = (LinearLayout) v.findViewById(R.id.ll_tip_main);
		layout_main.setBackgroundColor(c.getResources().getColor(R.color.full_transparent));
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.ll_tip);
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.CENTER;
		layout.setLayoutParams(params);
		TextView tv = (TextView) v.findViewById(R.id.tips_msg);
		tv.setText(text);
		ImageView iv = (ImageView) v.findViewById(R.id.tips_icon);
		iv.setVisibility(View.GONE);
		result.setView(v);
		// setGravity方法用于设置位置，此处为垂直居中
		// result.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		switch (position) {
		case 1:
			result.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
			break;

		default:
			break;
		}
		result.setDuration(duration);

		return result;
	}

	public static SHToast makeText(Context context, int iconResId, CharSequence text, int duration) {
		SHToast result = new SHToast(context);

		LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflate.inflate(R.layout.toast_bg, null);
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.ll_tip);

		LayoutParams params = new LayoutParams(dp2px(260), dp2px(200));
		layout.setLayoutParams(params);
		TextView tv = (TextView) v.findViewById(R.id.tips_msg);
		tv.setText(text);
		ImageView iv = (ImageView) v.findViewById(R.id.tips_icon);
		iv.setImageResource(iconResId);
		result.setView(v);
		// setGravity方法用于设置位置，此处为垂直居中
		result.setGravity(Gravity.FILL, 0, 0);
		result.setDuration(duration);

		return result;
	}

	public static SHToast makeText(Context context, int iconResId, int resId, int duration) throws Resources.NotFoundException {
		return makeText(context, iconResId, context.getResources().getText(resId), duration);
	}

	private static int dp2px(int dp) {
		final float scale = c.getResources().getDisplayMetrics().density;
		int px = (int) (dp * scale + 0.5f);
		return px;
	}
}
