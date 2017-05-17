
package com.nd.jisou.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

/**
 * 当应用程序由于异常出现Force Close时，把这些异常记录到sdcard下，路径为yourPackageName/log/AppErrorLog.txt。
 * <p>基本原理：设置UI主线程的UncaughtExceptionHandler，从而捕获到在该线程(以及该线程的子线程)出现的异常。
 * <p>调用方式：在Application::onCreate里调用如下代码即可：
 * <pre>
 * AppErrorLogHandler.getInstance(this);
 * </pre>
 * @author geolo
 *
 */
public class AppErrorLogHandler implements UncaughtExceptionHandler {

	private static final String LOG_FILE_NAME = "AppErrorLog.log";

	private static final int LOG_MAX_SIZE = 1024 * 1024;//1M
	private static final String TAG = "AppErrorLogHandler";

	private Context mContext;
	private String test="false";

	private static AppErrorLogHandler instance;
	private AppErrorLogHandler(Context mContext) {
		Thread.setDefaultUncaughtExceptionHandler(this);
		this.mContext = mContext;
	}

	private AppErrorLogHandler(Context mContext,String test) {
		this(mContext);
		this.test=test;
	}

	public static AppErrorLogHandler getInstance(Context mContext) {
		if (instance == null) {
			instance = new AppErrorLogHandler(mContext);
		}
		Thread.setDefaultUncaughtExceptionHandler(instance);
		return instance;
	}

	public static AppErrorLogHandler getInstance(Context mContext,String test) {
		if (instance == null) {
			instance = new AppErrorLogHandler(mContext,test);
		}
		Thread.setDefaultUncaughtExceptionHandler(instance);
		return instance;
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
//		MLog.e("AppErrorLogHandler.java", "uncaughtException", ex);
		if("true".equalsIgnoreCase(test)){
			playAlertRing();
		}
		if (!handleException(ex) && instance != null) {
			//如果用户没有处理则让系统默认的异常处理器来处理
			instance.uncaughtException(thread, ex);
		} else {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				Log.e(TAG, "error : ", e);
			}
			//退出程序
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(1);
		}
	}

	/**
	 * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
	 *
	 * @param ex
	 * @return true:如果处理了该异常信息;否则返回false.
	 */
	private boolean handleException(Throwable ex) {
		if (ex == null) {
			return false;
		}
		//使用Toast来显示异常信息
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				Toast.makeText(mContext, "很抱歉,程序出现异常,即将退出.", Toast.LENGTH_SHORT).show();
				Looper.loop();
			}
		}.start();
		//收集设备参数信息
//        collectDeviceInfo(mContext);
		//保存日志文件
		saveCrashInfoToFile(ex);
		return true;
	}

	/**
	 * 保存异常信息到文件
	 * @param ex
	 * @return
	 */
	private void saveCrashInfoToFile(final Throwable ex) {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				if(!StorageUtil.isSDcardExist(mContext)){//如果没有sdcard，则不存储
					return;
				}
				Writer writer = null;
				PrintWriter printWriter = null;
				String stackTrace = "";
				try {
					writer = new StringWriter();
					printWriter = new PrintWriter(writer);
					ex.printStackTrace(printWriter);
					Throwable cause = ex.getCause();
					while (cause != null) {
						cause.printStackTrace(printWriter);
						cause = cause.getCause();
					}
					stackTrace = writer.toString();
				} catch (Exception e) {
				}finally{
					if(writer!=null){
						try {
							writer.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if(printWriter!=null){
						printWriter.close();
					}
				}

				StringBuilder sb = new StringBuilder();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = new Date();
				String timestamp = sdf.format(date);
				sb.append(">>>>>>>>>>>>>>>>>>>>>>");
				sb.append(timestamp);//记录每个error的发生时间
				sb.append(System.getProperty("line.separator"));
				sb.append(stackTrace);
				sb.append(System.getProperty("line.separator"));//每个error间隔2行
				sb.append(System.getProperty("line.separator"));
				LogUtil.writeFileLog(mContext,sb.toString());

			}
		};
		new Thread(runnable).start();
	}

	private void playAlertRing(){
		Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		Ringtone r = RingtoneManager.getRingtone(mContext, notification);
		r.play();
	}

}