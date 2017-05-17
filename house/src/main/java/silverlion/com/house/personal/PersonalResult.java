package silverlion.com.house.personal;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by k8190 on 2016/8/11.
 */
@SuppressWarnings("user")
public class PersonalResult {
    @SerializedName("real_name")
    private String name;

    @SerializedName("sex")
    private String sex;

    @SerializedName("place")
    private String place;

    @SerializedName("area_code")
    private String area;

    @SerializedName("contact_phone")
    private String phone;

    @SerializedName("contact_email")
    private String email;

    @SerializedName("0")
    private List<Hand> list;

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public String getPlace() {
        return place;
    }

    public String getArea() {
        return area;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public List<Hand> getList() {
        return list;
    }

    @SuppressWarnings("user")
    public class Hand {
        public String getIamge() {
            return iamge;
        }

        @SerializedName("head_image")
        private String iamge;

    }

}
