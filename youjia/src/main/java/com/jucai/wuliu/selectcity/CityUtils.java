package com.jucai.wuliu.selectcity;

import java.util.ArrayList;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class CityUtils {
	
	private Context context;
	private Handler handler;
	
	public CityUtils(final Context context,final Handler handler) {
		this.context = context ;
		this.handler = handler;
	}

	/***
	 * 初始化省份
	 */
	public  void initProvince() {
		new Thread() {
			@Override
			public void run() {
				CityDBManager dbm = new CityDBManager(
						context);
				dbm.openDatabase();
				SQLiteDatabase db = dbm.getDatabase();
				final ArrayList<MyRegion> list = new ArrayList<MyRegion>();
				try {
					String sql = "SELECT id,name FROM REGION WHERE parent_id='1'";
					Cursor cursor = db.rawQuery(sql, null);
					while (cursor.moveToNext()) {
						MyRegion myListItem = new MyRegion();
						myListItem.setId(cursor.getString(0));
						myListItem.setName(cursor.getString(1));
						myListItem.setParent_id("1");
						list.add(myListItem);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				dbm.closeDatabase();
				db.close();
				Message msg = new Message();
				msg.what = 1;
				msg.obj = list;
				handler.sendMessage(msg);
			}
		}.start();
	}
	
	/**
	 * 初始化城市
	 * @param pcode 省份码
	 */
	public void initCities(final String pcode) {
		new Thread() {
			@Override
			public void run() {
				CityDBManager dbm = new CityDBManager(
						context);
				dbm.openDatabase();
				SQLiteDatabase db = dbm.getDatabase();
				ArrayList<MyRegion> list = new ArrayList<MyRegion>();
				try {
					String sql = "SELECT id,name FROM REGION WHERE parent_id='"
							+ pcode + "'";
					Cursor cursor = db.rawQuery(sql, null);
					while (cursor.moveToNext()) {
						MyRegion myListItem = new MyRegion();
						myListItem.setId(cursor.getString(0));
						myListItem.setName(cursor.getString(1));
						myListItem.setParent_id(pcode);
						list.add(myListItem);
					}
					dbm.closeDatabase();
					db.close();
					Message msg = new Message();
					msg.what = 2;
					msg.obj = list;
					handler.sendMessage(msg);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();

	}
	
	/**
	 * 初始化区
	 * @param 城市码
	 */
	public  void initDistricts(final String pcode) {
		new Thread() {
			@Override
			public void run() {
				CityDBManager dbm = new CityDBManager(
						context);
				dbm.openDatabase();
				SQLiteDatabase db = dbm.getDatabase();
				Log.e("AIITEC", "code="+pcode);
				ArrayList<MyRegion> list = new ArrayList<MyRegion>();
				try {
					String sql = "SELECT id,name FROM REGION WHERE parent_id='"
							+ pcode + "'"+" UNION SELECT id,name FROM REGION WHERE ID='"+pcode+"'";
					Cursor cursor = db.rawQuery(sql, null);
					// cursor.moveToFirst();
					while (cursor.moveToNext()) {
						String id=cursor.getString(0);
						MyRegion myListItem = new MyRegion();
						myListItem.setId(id);
						if(id.equalsIgnoreCase(pcode)){
							myListItem.setName("全市");
						}else{
							myListItem.setName(cursor.getString(1));
						}
						myListItem.setParent_id(pcode);
						list.add(myListItem);
					}
					dbm.closeDatabase();
					db.close();
					Message msg = new Message();
					msg.what = 3;
					msg.obj = list;
					handler.sendMessage(msg);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();

	}
}
