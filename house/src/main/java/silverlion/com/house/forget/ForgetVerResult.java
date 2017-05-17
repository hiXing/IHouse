package silverlion.com.house.forget;

import com.google.gson.annotations.SerializedName;

/**
 * Created by k8190 on 2016/7/26.
 */
@SuppressWarnings("user")
public class ForgetVerResult {
    @SerializedName("output")
    private String output;

    @SerializedName("cellphone")
    private String phone;

    public String getOutput() {
        return output;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return "ForgetVerResult{" +
                "output='" + output + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
