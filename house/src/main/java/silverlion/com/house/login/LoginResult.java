package silverlion.com.house.login;

import com.google.gson.annotations.SerializedName;

/**
 * Created by k8190 on 2016/7/21.
 */
@SuppressWarnings("Login")
public class LoginResult {
    @SerializedName("account_id")
    private String accout;

    @SerializedName("no_file")
    private String no_file;

    public String getAccout() {
        return accout;
    }

    public String getNo_file() {
        return no_file;
    }

    @Override
    public String toString() {
        return "LoginResult{" +
                "accout=" + accout +
                ", no_file=" + no_file +
                '}';
    }
}
