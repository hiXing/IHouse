package silverlion.com.house.message;

import com.google.gson.annotations.SerializedName;

/**
 * Created by k8190 on 2016/8/5.
 */
public class MessageResult {
    @SerializedName("id")
    private String id;

    @SerializedName("place")
    private String place;

    @SerializedName("address")
    private String address;

    @SerializedName("is_read")
    private String read;

    @SerializedName("ago_time")
    private String time;

    public String getId() {
        return id;
    }

    public String getPlace() {
        return place;
    }

    public String getAddress() {
        return address;
    }

    public String getRead() {
        return read;
    }

    public String getTime() {
        return time;
    }
}
