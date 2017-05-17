package com.example.fdcapp.obj;

import org.json.JSONException;
import org.json.JSONObject;

public class UserObject {
	private String eCode;
	private String Nick;
	private String eName;
	private String Tel;
	private String UserImg;
	private int token_time;
	private int eNum;
	private int oNum;
	private int IsReal;

	public String geteCode() {
		return eCode;
	}

	public void seteCode(String eCode) {
		this.eCode = eCode;
	}

	public String getNick() {
		return Nick;
	}

	public void setNick(String nick) {
		Nick = nick;
	}

	public String geteName() {
		return eName;
	}

	public void seteName(String eName) {
		this.eName = eName;
	}

	public String getTel() {
		return Tel;
	}

	public void setTel(String tel) {
		Tel = tel;
	}

	public String getUserImg() {
		return UserImg;
	}

	public void setUserImg(String userImg) {
		UserImg = userImg;
	}

	public int getToken_time() {
		return token_time;
	}

	public void setToken_time(int token_time) {
		this.token_time = token_time;
	}

	public int geteNum() {
		return eNum;
	}

	public void seteNum(int eNum) {
		this.eNum = eNum;
	}

	public int getoNum() {
		return oNum;
	}

	public void setoNum(int oNum) {
		this.oNum = oNum;
	}

	public int getIsReal() {
		return IsReal;
	}

	public void setIsReal(int isReal) {
		IsReal = isReal;
	}

	String Company;

	public String getCompany() {
		return Company;
	}

	public void setCompany(String company) {
		Company = company;
	}

	public String getProperties() {
		return Properties;
	}

	public void setProperties(String properties) {
		Properties = properties;
	}

	String Properties;

	public void parseFromJson(JSONObject object) throws JSONException {
		if (object.has("eCode"))
			this.eCode = object.getString("eCode");
		if (object.has("Nick"))
			this.Nick = object.getString("Nick");
		if (object.has("Company"))
			this.Company = object.getString("Company");
		if (object.has("Properties"))
			this.Properties = object.getString("Properties");
		if (object.has("eName"))
			this.eName = object.getString("eName");
		if (object.has("Tel"))
			this.Tel = object.getString("Tel");
		if (object.has("UserImg"))
			this.UserImg = object.getString("UserImg");
		if (object.has("token_time"))
			this.token_time = Integer.parseInt(object.getString("token_time"));
		if (object.has("eNum"))
			this.eNum = Integer.parseInt(object.getString("eNum"));
		if (object.has("oNum"))
			this.oNum = Integer.parseInt(object.getString("oNum"));
		if (object.has("IsReal"))
			this.IsReal = Integer.parseInt(object.getString("IsReal"));

	}
}
