package silverlion.com.house.houselist.housedetails;

import com.google.gson.annotations.SerializedName;

/**
 * Created by k8190 on 2016/8/3.
 */
public class HouseInformationResult {
    //    发布者id	键值为3的一维数组
    @SerializedName("id")
    private String userId;
//    用户类型	键值为3的一维数组
    @SerializedName("user_type")
    private String userType;
//    真实姓名	键值为3的一维数组real_name
    @SerializedName("real_name")
    private String realName;
//    公司名称	键值为3的一维数组
    @SerializedName("company_name")
    private String companyName;
//    手机号码	键值为3的一维数
    @SerializedName("cellphone")
    private String cellphone;
//    电话号码	键值为3的一维数组
    @SerializedName("telephone")
    private String telephone;
//    传真	键值为3的一维数组
    @SerializedName("fax")
    private String fax;
//    邮箱	键值为3的一维数组
    @SerializedName("email")
    private String email;
//    联系邮箱	键值为3的一维数组
    @SerializedName("contact_email")
    private String contactEmail;

    public String getUserId() {
        return userId;
    }

    public String getUserType() {
        return userType;
    }

    public String getRealName() {
        return realName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCellphone() {
        return cellphone;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getFax() {
        return fax;
    }

    public String getEmail() {
        return email;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    @Override
    public String toString() {
        return "HouseInformationResult{" +
                "userId='" + userId + '\'' +
                ", userType='" + userType + '\'' +
                ", realName='" + realName + '\'' +
                ", companyName='" + companyName + '\'' +
                ", cellphone='" + cellphone + '\'' +
                ", telephone='" + telephone + '\'' +
                ", fax='" + fax + '\'' +
                ", email='" + email + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                '}';
    }
}
