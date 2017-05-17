package com.example.fdcapp.obj;

import org.json.JSONException;
import org.json.JSONObject;

public class CusRportObject {

	String eName;

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

	public String getSex() {
		return Sex;
	}

	public void setSex(String sex) {
		Sex = sex;
	}

	public String getProp() {
		return Prop;
	}

	public void setProp(String prop) {
		Prop = prop;
	}

	public String geteCode() {
		return eCode;
	}

	public void seteCode(String eCode) {
		this.eCode = eCode;
	}

	public String geteTime() {
		return eTime;
	}

	public void seteTime(String eTime) {
		this.eTime = eTime;
	}

	public String geteType() {
		return eType;
	}

	public void seteType(String eType) {
		this.eType = eType;
	}

	String Tel;
	String Sex;
	String Prop;
	String eCode;
	String eTime;
	String eType;

	public void parseFromJson(JSONObject object) throws JSONException {
		if (object.has("eName"))
			this.eName = object.getString("eName");
		if (object.has("Tel"))
			this.Tel = object.getString("Tel");
		if (object.has("Sex"))
			this.Sex = object.getString("Sex");
		if (object.has("Prop"))
			this.Prop = object.getString("Prop");
		if (object.has("eCode"))
			this.eCode = object.getString("eCode");
		if (object.has("eTime"))
			this.eTime = object.getString("eTime");
		if (object.has("eType")) {
			this.eType = object.getString("eType");
			int type = Integer.parseInt(eType);
			switch (type) {
				case 0:
					eType = "未到场";
					break;
				case 1:
					eType = "到场";
					break;
				case 2:
					eType = "定金审核";
					break;
				case 3:
					eType = "已定金";
					break;
				case 4:
					eType = "首付审核";
					break;
				case 5:
					eType = "已首付";
					break;
				case 6:
					eType = "退款审核";
					break;
				case 7:
					eType = "已退款";
					break;
				case 8:
					eType = "定金驳回";
					break;
				case 9:
					eType = "首付驳回";
					break;

				default:
					break;
			}
		}
	}
}
