package com.example.fdcapp;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout.LayoutParams;

import com.ab.global.AbConstant;
import com.ab.util.AbStrUtil;
import com.example.fdcapp.view.MyProgressDialog;
import com.example.fdcapp.view.ToastView;
import com.jucai.wuliu.net.MainServerListener;
import com.jucai.wuliu.net.MainServerRequest;

public abstract class AbsSubActivity extends Activity {

	private static final int HashMap = 0;

	private static final int String = 0;

	/** 定义调试的标志,正式输出时，请设置为false */
	public static boolean IF_DEBUG = true;
	public static String serverUrl = "";
	// Secure.get// getContext().getContentResolver(), Secure.ANDROID_ID);

	/** 网络情况 */
	public static boolean hasNet = true;
	/** 记录日志的标  */
	private String TAG = AbsSubActivity.class.getSimpleName();
	private AbsSubActivity requestSubActivity;

	/** 加载框的文字说明. */
	private String mProgressMessage = "请稍 ,努力加载 ...";

	/** 全局的LayoutInflater对象，已经完成初始化. */
	public LayoutInflater mInflater;

	/** 全局的加载框对象，已经完成初始化. */
	public ProgressDialog mProgressDialog;

	/** 底部弹出的Dialog. */
	private Dialog mBottomDialog;

	/** 居中弹出的Dialog. */
	private Dialog mCenterDialog;

	/** 顶部弹出的Dialog. */
	private Dialog mTopDialog;

	/** 居中弹出的Dialog的View. */
	private View mCenterDialogView = null;

	/** 顶部弹出的Dialog的View. */
	private View mTopDialogView = null;

	/** 底部弹出的Dialog的View. */
	private View mBottomDialogView = null;

	/** 弹出的Dialog的左右边 */
	int dialogPadding = 40;

	/** 屏幕宽度. */
	public int diaplayWidth = 320;

	/** 屏幕高度. */
	public int diaplayHeight = 480;

	/** Window 管理 */
	private WindowManager mWindowManager = null;

	// 本地设置保存
	public Settings settings;

	private ToastView toastView;
	private Date mDate;
	private long ttime = 0;
	private long mtime = 0;
	private long timeGap = -28800;

	// 登录前的请求页面.class
	protected Class<?> activityClass = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// osmosis();
		mInflater = LayoutInflater.from(this);

		mWindowManager = getWindowManager();
		Display display = mWindowManager.getDefaultDisplay();
		diaplayWidth = display.getWidth();
		diaplayHeight = display.getHeight();

		// 如果Dialog不是充满屏幕，要设置这个
		if (diaplayWidth < 400) {
			dialogPadding = 30;
		} else if (diaplayWidth > 700) {
			dialogPadding = 50;
		}
		settings = new Settings(getApplicationContext());
		iniTime();
	}

	public AbsSubActivity getRequestSubActivity() {
		return requestSubActivity;
	}

	public void setRequestSubActivity(AbsSubActivity requestSubActivity) {
		this.requestSubActivity = requestSubActivity;
	}

	private Class getTargetClass(Intent intent) {
		Class clazz = null;
		try {
			if (intent.getComponent() != null)
				clazz = Class.forName(intent.getComponent().getClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return clazz;
	}

	/**
	 * 描述：Toast提示文本.
	 * 
	 * @param text
	 *            文本
	 */
	public void showToast(String text) {
		if (toastView == null) {
			toastView = null;
		}
		toastView = new ToastView(this, text);
		toastView.setGravity(Gravity.CENTER, 0, 0);
		toastView.show();
	}

	/** 显示错误的信息时使用 */
	public void showDebugToast(String text) {
		if (IF_DEBUG) {
			if (toastView == null) {
				toastView = null;
			}
			toastView = new ToastView(this, text);
			toastView.setGravity(Gravity.CENTER, 0, 0);
			toastView.show();
		}
	}

	/**
	 * 描述：Toast提示文本.
	 * 
	 * @param text
	 *            文本
	 * @param time
	 *            Toast显示时间
	 */
	public void showToast(String text, int time) {
		// Toast.makeText(this,""+text, Toast.LENGTH_SHORT).show();
		if (toastView == null) {
			toastView = null;
		}
		toastView = new ToastView(this, text);
		toastView.setGravity(Gravity.BOTTOM, 0, 20);
		toastView.setLongTime(time);
		toastView.show();
	}

	/**
	 * 描述：在底部显示  Dialog,id ,在中间显示id .
	 * 
	 * @param id
	 *            Dialog的类 
	 * @param view
	 *            指定  新View
	 * @see AbConstant.DIALOGBOTTOM
	 */
	public void showDialog(int id, View view, boolean cancelable) {
		if (id == AbConstant.DIALOGBOTTOM) {
			mBottomDialogView = view;
			if (mBottomDialog == null) {
				mBottomDialog = new Dialog(this);
				setDialogLayoutParams(mBottomDialog, dialogPadding, Gravity.BOTTOM);
			}
			mBottomDialog.setContentView(mBottomDialogView, new LayoutParams(diaplayWidth - dialogPadding, LayoutParams.WRAP_CONTENT));
			// 点击外部  
			mBottomDialog.setCancelable(cancelable);
			mBottomDialog.setCanceledOnTouchOutside(cancelable);
			showDialog(id);
		} else if (id == AbConstant.DIALOGCENTER) {
			mCenterDialogView = view;
			if (mCenterDialog == null) {
				mCenterDialog = new Dialog(this, R.style.dialog);
				setDialogLayoutParams(mCenterDialog, dialogPadding, Gravity.CENTER);
			}
			mCenterDialog.setContentView(mCenterDialogView, new LayoutParams(diaplayWidth - dialogPadding, LayoutParams.WRAP_CONTENT));
			// 点击外部  
			mCenterDialog.setCancelable(cancelable);
			mCenterDialog.setCanceledOnTouchOutside(cancelable);
			showDialog(id);
		} else if (id == AbConstant.DIALOGTOP) {
			mTopDialogView = view;
			if (mTopDialog == null) {
				mTopDialog = new Dialog(this);
				setDialogLayoutParams(mTopDialog, dialogPadding, Gravity.TOP);
			}
			mTopDialog.setContentView(mTopDialogView, new LayoutParams(diaplayWidth - dialogPadding, LayoutParams.WRAP_CONTENT));
			// 点击外部  
			mCenterDialog.setCancelable(cancelable);
			mCenterDialog.setCanceledOnTouchOutside(cancelable);
			showDialog(id);
		} else {
			Log.i(TAG, "Dialog的ID传错了，请参考AbConstant类定 ");
		}
	}

	public void removeDialog() {
		if (mBottomDialog != null) {
			mBottomDialog.dismiss();
			return;
		}
		if (mCenterDialog != null) {
			mCenterDialog.dismiss();
			return;
		}
		if (mTopDialog != null) {
			mTopDialog.dismiss();
			return;
		}
	}

	/**
	 * 描述：对话框初始 
	 * 
	 * @param id
	 *            the id
	 * @return the dialog
	 * @see Activity#onCreateDialog(int)
	 */
	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		switch (id) {
		case AbConstant.DIALOGPROGRESS:
			if (mProgressDialog == null) {
				Log.i(TAG, "Dialog方法调用错误,请调用showProgressDialog()!");
			}
			return mProgressDialog;
		case AbConstant.DIALOGBOTTOM:
			if (mBottomDialog == null) {
				Log.i(TAG, "Dialog方法调用错误,请调用showDialog(int id,View view)!");
			}
			return mBottomDialog;
		case AbConstant.DIALOGCENTER:
			if (mCenterDialog == null) {
				Log.i(TAG, "Dialog方法调用错误,请调用showDialog(int id,View view)!");
			}
			return mCenterDialog;
		case AbConstant.DIALOGTOP:
			if (mTopDialog == null) {
				Log.i(TAG, "Dialog方法调用错误,请调用showDialog(int id,View view)!");
			}
			return mTopDialog;
		default:
			break;
		}
		return dialog;
	}

	/**
	 * 描述：设置弹出Dialog的属 
	 * 
	 * @param dialog
	 *            弹出Dialog
	 * @param dialogPadding
	 *            如果Dialog不是充满屏幕，要设置这个 
	 * @param gravity
	 *            the gravity
	 */
	private void setDialogLayoutParams(Dialog dialog, int dialogPadding, int gravity) {
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		Window window = dialog.getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		// 此处可以设置dialog显示的位 
		window.setGravity(gravity);
		// 设置宽度
		lp.width = diaplayWidth - dialogPadding;
		lp.type = WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG;
		// 背景透明
		// lp.screenBrightness = 0.2f;
		lp.alpha = 0.8f;
		lp.dimAmount = 0f;
		window.setAttributes(lp);
		// 添加动画
		window.setWindowAnimations(android.R.style.Animation_Dialog);
		// 设置点击屏幕Dialog不消 
		dialog.setCanceledOnTouchOutside(false);

	}

	/**
	 * 描述：显示进度框.
	 */
	public void showProgressDialog() {
		showProgressDialog(null);
	}

	private MyProgressDialog progressDialog;

	/**
	 * 描述：显示进度框.
	 * 
	 * @param message
	 *            the message
	 */
	public void showProgressDialog(String message) {

		if (!AbStrUtil.isEmpty(message)) {
			progressDialog = new MyProgressDialog(this, message);
		} else {
			progressDialog = new MyProgressDialog(this, "加载 ...");
		}

		progressDialog.show();
	}

	/**
	 * 描述：移除进度框.
	 */
	public void removeProgressDialog() {
		// removeDialog(AbConstant.DIALOGPROGRESS);
		if (progressDialog != null)
			progressDialog.dismiss();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	public void startAbsActivity(Context packageContext, Class<?> class1) {
		Intent intent = new Intent(packageContext, class1);
		packageContext.startActivity(intent);
	}

	public void startAbsActivity(Context packageContext, Class<?> class1, Bundle bundle) {
		Intent intent = new Intent(packageContext, class1);
		intent.putExtras(bundle);
		packageContext.startActivity(intent);
	}

	public void startAbsActivity(Context packageContext, Class<?> class1, Bundle bundle, String objectName, Object object) {
		Intent intent = new Intent(packageContext, class1);
		intent.putExtras(bundle);
		try {
			bundle.putSerializable(objectName, (Serializable) object);
		} catch (Exception e) {
			Log.v(TAG, "传对象未序列化！");
		}
		packageContext.startActivity(intent);
	}

	// _____________________________________________________________________________________
	public void back(View view) {
		finish();
	}

	public static String eKey = "wahaha";
	public String eCode;

	@SuppressWarnings("deprecation")
	private void iniTime() {
		eCode = Secure.getString(getContentResolver(), Secure.ANDROID_ID);
		mDate = new Date();
		ttime = getLongtime("2016 01 01 00ʱ00 00 ");
		mtime = mDate.getTime();
		mtime = (mtime - ttime) / 1000;
		MainServerRequest serverRequest = MainServerRequest.getInstance();
		serverRequest.setListener(new MainServerListener() {

			@Override
			public void requestSuccess(JSONObject s) {
				// TODO Auto-generated method stub
				try {
					int errorCode = Integer.parseInt(s.getString("reason"));
					if (errorCode == 100) {
						JSONObject object = s.getJSONArray("data").getJSONObject(0);
						String string = object.getString("interval");
						timeGap = Long.parseLong(string);
					} else
						valErrCode(errorCode);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					showToast(s.toString());
					e.printStackTrace();

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					showToast(e.getMessage());
					e.printStackTrace();
				}
			}

			@Override
			public void requestFailure(String s) {
				// TODO Auto-generated method stub
				showToast(s.toString());
			}
		});
		serverRequest.stime(mtime + "");
	}

	public String getTime() {
		mDate = new Date();
		long tetime = (mDate.getTime() - ttime) / 1000 + timeGap;
		return tetime + "";
	}

	public void getUserInfo() {
		MainServerRequest serverRequest = MainServerRequest.getInstance();
		serverRequest.setListener(new MainServerListener() {
			@Override
			public void requestSuccess(JSONObject s) {
				// TODO Auto-generated method stub
				try {
					int errorCode = Integer.parseInt(s.getString("reason"));
					switch (errorCode) {
					case 100:
						settings.saveUser(s);
						break;
					default:
						valErrCode(errorCode);
						break;
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					showToast(s.toString());
				}
			}

			@Override
			public void requestFailure(String s) {
				// TODO Auto-generated method stub
				showToast(s);
			}
		});
		HashMap<String, String> map = new HashMap<String, String>();
		String open_id = settings.getOpen_id();
		String token_id = settings.getToken_id();
		String eCode = null;
		map.put("open_id", open_id);
		map.put("token_id", token_id);
		map.put("eTime", getTime());
		map.put("eCode", eCode);
		serverRequest.userInfo(map);
	}

	public void valErrCode(int err) {
		switch (err) {
			case 103:
				showToast("密码长度错误");
				break;
			case 101:
				showToast("传输信息不完整");
				break;
			case 102:
				showToast("登陆账户长度错误（9/11位）");
				break;
			case 104:
				showToast("验证秘钥长度错误");
				break;
			case 105:
				showToast("时间戳差误过大（±30/10）");
				break;
			case 108:
				showToast("手机号码不正确");
				break;
			case 106:
				showToast("秘钥校验错误");
				break;
			case 107:
				showToast("账号或者密码不正确 / 验证用户权限失败");
				break;
			case 109:
				showToast("手机校验码验证失败	");
				break;
			case 110:
				showToast("昵称/手机号码已存在");
				break;
			case 111:
				showToast("公司编码不正确");
				break;
			case 201:
				showToast("token_id无效");
				break;
			case 301:
				showToast("客户已经存在");
				break;
			case 302:
				showToast("楼盘编号验证失败");
				break;
			case 303:
				showToast("没有该楼盘的报备权限");
				break;
			case 121:
				showToast("旧密码不正确");
				break;
			case 122:
				showToast("密码MD5位数校验失败（32位）");
				break;
			case 401:
				showToast("未实名认证");
				break;
			case 402:
				showToast("提现余额不足");
				break;
			case 403:
				showToast("已有提现尚未处理");
				break;
			case 501:
				showToast("图片保存失败");
				break;
			case 502:
				showToast("图片编码不正确");
				break;
			default:
				break;
		}
	}

	public static String md5(String string) {
		byte[] hash;
		try {
			hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Huh, MD5 should be supported?", e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Huh, UTF-8 should be supported?", e);
		}
		StringBuilder hex = new StringBuilder(hash.length * 2);
		for (byte b : hash) {
			if ((b & 0xFF) < 0x10)
				hex.append("0");
			hex.append(Integer.toHexString(b & 0xFF));
		}

		return hex.toString().toUpperCase();
	}

	public static long getLongtime(String time) {
		String re_timeString = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd HHʱmm ss ");
		Date date;
		long l = 0;
		try {
			date = sdf.parse(time);
			l = date.getTime();

		} catch (Exception e) {
			// TODO: handle exception
		}
		return l;

	}

}
