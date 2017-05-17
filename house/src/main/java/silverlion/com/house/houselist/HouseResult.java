package silverlion.com.house.houselist;

import com.google.gson.annotations.SerializedName;

/**
 * Created by k8190 on 2016/8/3.
 */
public class HouseResult {
    @SerializedName("collect")
    private String collect;

    @SerializedName("error")
    private String error;

    public String getCollect() {
        return collect;
    }

    public String getError() {
        return error;
    }
}
