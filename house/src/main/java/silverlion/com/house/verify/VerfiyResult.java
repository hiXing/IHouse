package silverlion.com.house.verify;

import com.google.gson.annotations.SerializedName;

/**
 * Created by k8190 on 2016/7/25.
 */
@SuppressWarnings("user")
public class VerfiyResult {
    @SerializedName("output")
    private String output;

    public String getOutput() {
        return output;
    }

    @Override
    public String toString() {
        return "VerfiyResult{" +
                "output='" + output + '\'' +
                '}';
    }
}
