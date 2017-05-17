package com.sky.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;



public class SHDialog {
	public final static int SELECT_DIALOG=1;
	public final static int RADIO_DIALOG=2;//onekey
	private static android.app.Dialog d;
	  
	public static android.app.Dialog ShowProgressDiaolg(Context context,String title){ 
		dismissProgressDiaolg();
		return ShowDiaolg(context, title);
	}
	
	public static void dismissProgressDiaolg(){ 
		if(d != null){
			d.dismiss();
			d = null;
		}
	}

	public static android.app.Dialog showActionSheet(Context context,String title,String[] items,final DialogItemClickListener dialogItemClickListener){
		return ShowDialog(context,title,items,dialogItemClickListener);
	}

	public static android.app.Dialog showOneKeyDialog(Context context,String toast,final DialogClickListener dialogClickListener){
		return ShowDialog(context,context.getResources().getString(R.string.pointMessage),toast,dialogClickListener,RADIO_DIALOG);
	}

	public static android.app.Dialog showOneKeyDialog(Context context,String title,String toast,final DialogClickListener dialogClickListener){
		return ShowDialog(context,title,toast,dialogClickListener,RADIO_DIALOG);
	}

	public static android.app.Dialog showDoubleKeyDialog(Context context,String toast,final DialogClickListener dialogClickListener){
		return ShowDialog(context,context.getResources().getString(R.string.pointMessage),toast,dialogClickListener,SELECT_DIALOG);
	}
	
	public static android.app.Dialog showDoubleKeyDialog(Context context,String title,String toast,final DialogClickListener dialogClickListener){
		return ShowDialog(context,title,toast,dialogClickListener,SELECT_DIALOG);
	}
	
	private static android.app.Dialog ShowDiaolg(Context context,String title){ 
		final android.app.Dialog dialog=new android.app.Dialog(context, R.style.dialog);
		d = dialog;
		final View view = LayoutInflater.from(context).inflate(R.layout.dialog_progress, null);
		dialog.setContentView(view);
		TextView tv = (TextView) view.findViewById(R.id.tv_msg);
		if(title != null && title.length() >0){
			tv.setText(title);
		}
		ImageView loadingImageView = (ImageView)view.findViewById(R.id.img_loading);
		AnimationDrawable Bt2_dialog = (AnimationDrawable) loadingImageView.getBackground();
		Bt2_dialog.start();
		dialog.setCancelable(false);
		dialog.show();
		return dialog;
	}
	
	private static android.app.Dialog ShowDialog(Context context,String title,String toast,final DialogClickListener dialogClickListener,int DialogType){ 
		final android.app.Dialog dialog=new android.app.Dialog(context, R.style.DialogStyle);
		d = dialog;
		dialog.setCancelable(false);
		View view=LayoutInflater.from(context).inflate(R.layout.dialog_doublekey, null);
		dialog.setContentView(view);
		((TextView)view.findViewById(R.id.point)).setText(title);
		((TextView)view.findViewById(R.id.toast)).setText(toast);
		if(DialogType==RADIO_DIALOG){
		}else{
			view.findViewById(R.id.ok).setVisibility(View.GONE);
			view.findViewById(R.id.divider).setVisibility(View.VISIBLE);
		}
		view.findViewById(R.id.cancel).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						dialogClickListener.cancel();
					}
				},200);
			}
		});
		view.findViewById(R.id.confirm).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						dialogClickListener.confirm();
					}
				},200);
			}
		});
		view.findViewById(R.id.ok).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
				if(dialogClickListener != null){
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							dialogClickListener.confirm();
						}
					},200);
				}
				
			}
		});
		Window mWindow=dialog.getWindow();
        WindowManager.LayoutParams lp = mWindow.getAttributes();
        if(context.getResources().getConfiguration().orientation==Configuration.ORIENTATION_LANDSCAPE){//妯�灞�
            lp.width= getScreenHeight(context)/10*8;
        }else{
            lp.width= getScreenWidth(context)/10*8;
        }
        mWindow.setAttributes(lp);
		dialog.show();
		
		return dialog;
	}
	private static android.app.Dialog ShowDialog(Context context,String title,String[] items,final DialogItemClickListener dialogClickListener){ 
		final android.app.Dialog dialog=new android.app.Dialog(context, R.style.DialogStyle);
		dialog.setCancelable(true);
		View view=LayoutInflater.from(context).inflate(R.layout.dialog_onekey, null);
		dialog.setContentView(view);
		((TextView)view.findViewById(R.id.title)).setText(title);
		LinearLayout parent=(LinearLayout) view.findViewById(R.id.dialogLayout);
		parent.removeAllViews();
		int length=items.length;
		for (int i = 0; i < items.length; i++) {
			LayoutParams params1=new LayoutParams(-1,-2);
			params1.rightMargin=1;
			final TextView tv=new TextView(context);
			tv.setLayoutParams(params1);
			tv.setTextSize(18);
			tv.setText(items[i]);
			tv.setTextColor(context.getResources().getColor(R.color.dialogTxtColor));
			int pad=context.getResources().getDimensionPixelSize(R.dimen.padding10);
			tv.setPadding(pad,pad,pad,pad);
			tv.setSingleLine(true);
			tv.setGravity(Gravity.CENTER);
			if(i!=length-1)
				tv.setBackgroundResource(R.drawable.actionsheet_center_selector);
			else
				tv.setBackgroundResource(R.drawable.actionsheet_bottom2_selector);
				
			tv.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					dialog.dismiss();
					dialogClickListener.onSelected(tv.getText().toString());
				}
			});
			parent.addView(tv);
			if(i!=length-1){
				TextView divider=new TextView(context);
				LayoutParams params=new LayoutParams(-1,(int)1);
				divider.setLayoutParams(params);
				divider.setBackgroundResource(android.R.color.darker_gray);
				parent.addView(divider);
			}
		}
		view.findViewById(R.id.ok).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});
		Window mWindow=dialog.getWindow();
		WindowManager.LayoutParams lp = mWindow.getAttributes();
	    lp.width= getScreenWidth(context);
		mWindow.setGravity(Gravity.BOTTOM);
		mWindow.setWindowAnimations(R.style.dialogAnim);
		mWindow.setAttributes(lp);
		dialog.show();
		return dialog;
	}
	public static int getScreenWidth(Context context){
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
	}
	public static int getScreenHeight(Context context){
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
	}
	public interface DialogClickListener{
		public abstract void confirm();
		public abstract void cancel();
	}
	public interface DialogItemClickListener{
		public abstract void onSelected(String result);
	}
}