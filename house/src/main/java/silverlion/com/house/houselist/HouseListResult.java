package silverlion.com.house.houselist;

import com.google.gson.annotations.SerializedName;

/**
 * Created by k8190 on 2016/7/27.
 */
@SuppressWarnings("user")
public class HouseListResult {
    @SerializedName("id")
    private String id;
    @SerializedName("0")
    private String picture;
    @SerializedName("place")
    private String place;
    @SerializedName("address")
    private String address;
    @SerializedName("reg_time")
    private String time;

    public String getId() {
        return id;
    }

    public String getPicture() {
        return picture;
    }

    public String getAddress() {
        return address;
    }

    public String getPlace() {
        return place;
    }

    public String getTime() {
        return time;
    }

    public HouseListResult(String id, String picture, String place, String address, String time) {
        this.id = id;
        this.picture = picture;
        this.place = place;
        this.address = address;
        this.time = time;
    }

    @Override
    public String toString() {
        return "HouseListResult{" +
                "id='" + id + '\'' +
                ", picture='" + picture + '\'' +
                ", place='" + place + '\'' +
                ", address='" + address + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
