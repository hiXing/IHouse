package silverlion.com.house.forget;

import com.google.gson.annotations.SerializedName;

/**
 * Created by k8190 on 2016/7/26.
 */
@SuppressWarnings("user")
public class ForgetResult {

    @SerializedName("output")
    private String output;
    @SerializedName("code_id")
    private String code_id;
    @SerializedName("verify_code")
    private String verify_code;

    public String getOutput() {
        return output;
    }

    public String getCode_id() {
        return code_id;
    }

    public String getVerify_code() {
        return verify_code;
    }

    @Override
    public String toString() {
        return "ForgetResult{" +
                "output='" + output + '\'' +
                ", code_id='" + code_id + '\'' +
                ", verify_code='" + verify_code + '\'' +
                '}';
    }
}
