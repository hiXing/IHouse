package silverlion.com.house.register;

import com.google.gson.annotations.SerializedName;

/**
 * Created by k8190 on 2016/7/22.
 */
@SuppressWarnings("user")
public final class RegisterResult {
    @SerializedName("output")
    private int output;
    @SerializedName("code_id")
    private String code_id;
    @SerializedName("verify_code")
    private String verify_code;
    @SerializedName("cellphone")
    private String cellphone;

    @SerializedName("email")
    private String  email;

    public int getOutput() {
        return output;
    }

    public String getCode_id() {
        return code_id;
    }

    public String getVerify_code() {
        return verify_code;
    }

    public String getCellphone() {
        return cellphone;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "RegisterResult{" +
                "output=" + output +
                ", code_id='" + code_id + '\'' +
                ", verify_code='" + verify_code + '\'' +
                ", cellphone='" + cellphone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
