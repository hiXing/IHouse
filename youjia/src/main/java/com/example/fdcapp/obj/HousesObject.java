package com.example.fdcapp.obj;

import org.json.JSONException;
import org.json.JSONObject;

public class HousesObject {
	String eCode;

	public String geteCode() {
		return eCode;
	}

	public void seteCode(String eCode) {
		this.eCode = eCode;
	}

	public String geteName() {
		return eName;
	}

	public void seteName(String eName) {
		this.eName = eName;
	}

	public String getImgUrl() {
		return ImgUrl;
	}

	public void setImgUrl(String imgUrl) {
		ImgUrl = imgUrl;
	}

	public String geteAddress() {
		return eAddress;
	}

	public void seteAddress(String eAddress) {
		this.eAddress = eAddress;
	}

	public String getTel() {
		return Tel;
	}

	public void setTel(String tel) {
		Tel = tel;
	}

	public String geteType() {
		return eType;
	}

	public void seteType(String eType) {
		this.eType = eType;
	}

	public String geteMomey() {
		return eMomey;
	}

	public void seteMomey(String eMomey) {
		this.eMomey = eMomey;
	}

	public String geteTime() {
		return eTime;
	}

	public void seteTime(String eTime) {
		this.eTime = eTime;
	}

	String eName;
	String ImgUrl;
	String eAddress;
	String Tel;
	String eType;
	String eMomey;
	String eTime;

	public void parseFromJson(JSONObject object) throws JSONException {
		if (object.has("eName"))
			this.eName = object.getString("eName");
		if (object.has("Tel"))
			this.Tel = object.getString("Tel");
		if (object.has("eCode"))
			this.eCode = object.getString("eCode");
		if (object.has("eMomey"))
			this.eMomey = object.getString("eMomey");
		if (object.has("eCode"))
			this.eCode = object.getString("eCode");
		if (object.has("eTime"))
			this.eTime = object.getString("eTime");
		if (object.has("eType"))
			this.eType = object.getString("eType");
		if (object.has("eAddress"))
			this.eAddress = object.getString("eAddress");

	}
}
