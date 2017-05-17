package com.nd.jisou.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;

public final class LogUtil {
    private static final String LOG_FILE_NAME = "AppErrorLog.log";
    private static final int LOG_MAX_SIZE = 1024 * 1024;//1M
    
	public static void writeFileLog(Context context, String logString){
        BufferedWriter mBufferedWriter = null;
        try {
	        StringBuilder sb = new StringBuilder();
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        Date date = new Date();
	        String timestamp = sdf.format(date);
	        sb.append(">>>>>>>>>>>>>>>>>>>>>>");
            sb.append(timestamp);//记录每个error的发生时间
            sb.append("\n");
            sb.append(logString);
            sb.append("\r\n");
            sb.append(System.getProperty("line.separator"));//每个error间隔2行
            sb.append(System.getProperty("line.separator"));
            File mFile = new File(StorageUtil.getWritePathIgnoreError(context,LOG_FILE_NAME));
            File pFile = mFile.getParentFile();
            if(!pFile.exists()){//如果文件夹不存在，则先创建文件夹
                pFile.mkdirs();
            }
            if (mFile.length() > LOG_MAX_SIZE) {//如果超过最大文件大小，则重新创建一个文件
                mFile.delete();
                mFile.createNewFile();
            }
            mBufferedWriter = new BufferedWriter(new FileWriter(mFile,true));//追加模式写文件
            mBufferedWriter.append(sb.toString());
            mBufferedWriter.flush();
        } catch (IOException e) {
        } finally {
            if (mBufferedWriter != null) {
                try {
                    mBufferedWriter.close();
                } catch (IOException e) {
                }
            }
        }
	}
}
