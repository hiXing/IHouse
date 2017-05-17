package com.lele.kanfang.ui.home;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wuping on 2017/1/12.
 */

public class KanFangShi implements Parcelable {

    private String name;//姓名
    private String address;//来源
    private String area;//区域
    private String volume;//成交量
    private String reputation;//好评度

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getReputation() {
        return reputation;
    }

    public void setReputation(String reputation) {
        this.reputation = reputation;
    }

    public static final Creator<KanFangShi> CREATOR = new Creator<KanFangShi>() {
        @Override
        public KanFangShi createFromParcel(Parcel parcel) {
            return new KanFangShi(parcel);
        }

        @Override
        public KanFangShi[] newArray(int i) {
            return new KanFangShi[i];
        }
    };

    protected KanFangShi(Parcel in){
        name = in.readString();
        address = in.readString();
        area = in.readString();
        volume = in.readString();
        reputation = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(name);
        out.writeString(address);
        out.writeString(area);
        out.writeString(volume);
        out.writeString(reputation);
    }
}
