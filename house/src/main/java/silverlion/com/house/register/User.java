package silverlion.com.house.register;

import com.google.gson.annotations.SerializedName;

/**
 * Created by k8190 on 2016/7/22.
 */
@SuppressWarnings("user")
public class User {
    public User(String account, String cellphone) {
        this.area_code = account;
        this.cellphone = cellphone;
    }
    private final String area_code;

    private final String cellphone;

    public String getArea_code() {
        return area_code;
    }
    public String getCellphone() {
        return cellphone;
    }

}
