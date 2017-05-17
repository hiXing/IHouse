package silverlion.com.house.houselist.housedetails;

import com.google.gson.annotations.SerializedName;

/**
 * Created by k8190 on 2016/8/3.
 */
public class HouseDaployResult {

    @SerializedName("fac_name")
    private String facName;

    @SerializedName("amount")
    private String amount;

    public String getFacName() {
        return facName;
    }

    public String getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "HouseDaployResult{" +
                "facName='" + facName + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }
}
