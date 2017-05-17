package silverlion.com.house.verify;

import com.google.gson.annotations.SerializedName;

/**
 * Created by k8190 on 2016/7/25.
 */
@SuppressWarnings("user")
public class VerfiyResponse {
    public String getAccout_id() {
        return accout_id;
    }
    public String getError() {
        return error;
    }
    @SerializedName("accout_id")
    private String accout_id;

    @SerializedName("error")
    private String error;

    @Override
    public String toString() {
        return "VerfiyResponse{" +
                "accout_id='" + accout_id + '\'' +
                ", error='" + error + '\'' +
                '}';
    }
}
