package com.example.fdcapp;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.example.fdcapp.obj.UserObject;

public class Settings {
	private Context context;
	public SharedPreferences sp;
	public Editor editor;

	public Settings(Context cx) {
		context = cx;
		sp = PreferenceManager.getDefaultSharedPreferences(context);
		editor = sp.edit();
	}

	/*** 是否登录 **/
	public void saveIsLogin(String isLogin) {
		editor.putString("isLogin", isLogin);
		editor.commit();
	}

	public String getIsLogin() {
		return sp.getString("isLogin", "0");
	}

	/*** 首次登录 **/
	public void saveFirst(boolean ifFirst) {
		editor.putBoolean("first", ifFirst);
		editor.commit();
	}

	public boolean getFirst() {
		return sp.getBoolean("first", true);
	}

	// 启动界面的图
	public void saveLancherImage(String imageUrl) {
		editor.putString("lancher", imageUrl);
		editor.commit();
	}

	public String getLancherImage() {
		return sp.getString("lancher", null);
	}

	// ////记住密码选框是否选中
	public void saveIfRemember(boolean ifRemember) {
		editor.putBoolean("ifRemember", ifRemember);
		editor.commit();
	}

	public boolean getIfRemember() {
		return sp.getBoolean("ifRemember", true);
	}

	/** 保存用户对象 */
	public void saveUser(JSONObject user) {
		editor.putString("user", user.toString());
		editor.commit();
	}

	public void saveUserName(String userName) {
		editor.putString("userName", userName);
		editor.commit();
	}

	public void saveUserID(String userID) {
		editor.putString("userID", userID);
		editor.commit();
	}

	public String getUserID() {
		return sp.getString("userID", "");
	}

	public void saveUserHead(String userHead) {
		editor.putString("userHead", userHead);
		editor.commit();
	}

	public String getUserHead() {
		return sp.getString("userHead", "");
	}

	public void saveUserRole(String userRole) {
		editor.putString("userRole", userRole);
		editor.commit();
	}

	public String getUserRole() {
		return sp.getString("userRole", "1");
	}

	/** 获取用户对象 */
	public JSONObject getUser() {
		JSONObject userObject = null;
		String userString = sp.getString("user", "");
		if (userString != null) {
			try {
				userObject = new JSONObject(userString);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return userObject;
	}

	/** 用户手机 */
	public void saveUserPhone(String userPhone) {
		editor.putString("userPhone", userPhone);
		editor.commit();
	}

	public String getUserPhone() {
		String tempStr = sp.getString("userPhone", null);
		return tempStr;
	}

	public String getUserName() {
		String tempStr = sp.getString("userName", null);
		return tempStr;
	}

	/** 保存用户密码 **/
	public void saveUserPass(String userPass) {
		editor.putString("password", userPass);
		editor.commit();
	}

	/** 获取用户密码 **/
	public String getUserPass() {
		return sp.getString("password", "");
	}

	public boolean saveValue(String key, String value) {
		Editor sharedata = context.getSharedPreferences("data", 0).edit();
		sharedata.putString(key, value);
		return sharedata.commit();
	}

	public String getValue(String key) {
		SharedPreferences sharedata = context.getSharedPreferences("data", 0);
		return sharedata.getString(key, "");
	}

	// id1
	public void saveSelectone_id(int id) {
		editor.putInt("saveSelectone_id", id);
		editor.commit();
	}

	public Integer getSelectone_id() {
		return sp.getInt("saveSelectone_id", -1);
	}

	// 保存地址二ID
	public void saveToken_id(String token_id) {
		editor.putString("token_id", token_id);
		editor.commit();
	}

	public String getToken_id() {
		return sp.getString("token_id", "");
	}

	// 保存地址id
	public void saveOpen_id(String open_id) {
		editor.putString("open_id", open_id);
		editor.commit();
	}

	public String getOpen_id() {
		return sp.getString("open_id", "");
	}

	public UserObject getUserObject() {
		JSONObject userObject = null;
		UserObject user = new UserObject();
		String userString = sp.getString("user", "");
		if (userString != null) {
			try {
				userObject = new JSONObject(userString);
				user.parseFromJson(userObject);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return user;
	}

}
