package com.jucai.wuliu.selectcity;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

//public class City implements Parcelable{
public class City implements Serializable{	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String regionId;
	private String provinceCode;
	private String cityCode;
	private String districtCode;
	private String province;
	private String city;
	private String district;
	
	public String getRegionId() {
		return regionId;
	}
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}

	public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getDistrictCode() {
		return districtCode;
	}
	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

//	
//	public City()
//	{
//		regionId = "";
//		provinceCode= "";
//		cityCode= "";
//		districtCode= "";
//		province= "";
//		city= "";
//		district= "";
//	}

}
