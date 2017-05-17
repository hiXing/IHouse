package silverlion.com.house.houselist.housedetails;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by k8190 on 2016/8/1.
 */
@SuppressWarnings("user")
public class HouseDetailsResult {

    @SerializedName("id")//    房源id 	house_id
    private String house_id;

    @SerializedName("user_id")
    private String user;

    @SerializedName("house_nature")//    性质	house_nature
    private String house_nature;

    @SerializedName("house_num")//    房产编号	house_num
    private String house_num;

    @SerializedName("place")//    地区	place
    private String place;

    @SerializedName("address")//    地址	place + address
    private String address;

    @SerializedName("house_type")//    房产类型	house_type
    private String house_type;

    @SerializedName("house_status")//    房产状态	house_status
    private String house_status;

    @SerializedName("payment")//    支付方式	payment
    private String payment;

    @SerializedName("coin")// 钱币符号
    private String coin;

    @SerializedName("price")//    价格	coin+price
    private String price;

    @SerializedName("immigrant")//    是否移民	immigrant
    private String immigrant;

    @SerializedName("lease_time")//    出租时间	lease_time + lease_unit
    private String lease;

    @SerializedName("lease_unit")//    出租时间	lease_time + lease_unit
    private String lease_unit;

    @SerializedName("practical_area")//    实用面积	practical_area + area_unit
    private String practical;

    @SerializedName("cover_area")//    占地面积	cover_area + area_unit
    private String cover;

    @SerializedName("construction_area")//    建筑面积	construction_area + area_unit
    private String construaction;

    @SerializedName("area_unit")//    建筑面积	construction_area + area_unit
    private String area_unit;

    @SerializedName("lat")//    地理位置：纬度（谷歌地图）	lat
    private String lat;

    @SerializedName("lng")//    地理位置：经度（谷歌地图）	lng
    private String lng;

    @SerializedName("0")//    房源图片	键值为0的二维数组
    private List<HouseImageResult> houseImageResults;

    @SerializedName("1")//    房屋配置	键值为1的二维数组
    private List<HouseDaployResult> houseDaployResults;

    @SerializedName("2")//   便利性	键值为2的一维数组
    private List<String> convenienceResults;

    @SerializedName("3")//	键值为3的一维数组
    private HouseInformationResult houseInformationResults;

    @SerializedName("4")//    用户资料图片（2张）	键值为4的二维数组
    private List<HouseUserImageResult> userImageResults;

    @SerializedName("5")//    app用户是否收藏了该房源	键值为5的数据
    private int ropo;

    public String getHouse_id() {
        return house_id;
    }

    public String getUser() {
        return user;
    }

    public String getHouse_nature() {
        return house_nature;
    }

    public String getHouse_num() {
        return house_num;
    }

    public String getPlace() {
        return place;
    }

    public String getAddress() {
        return address;
    }

    public String getHouse_type() {
        return house_type;
    }

    public String getHouse_status() {
        return house_status;
    }

    public String getPayment() {
        return payment;
    }

    public String getCoin() {
        return coin;
    }

    public String getPrice() {
        return price;
    }

    public String getImmigrant() {
        return immigrant;
    }

    public String getLease() {
        return lease;
    }

    public String getLease_unit() {
        return lease_unit;
    }

    public String getPractical() {
        return practical;
    }

    public String getCover() {
        return cover;
    }

    public String getConstruaction() {
        return construaction;
    }

    public String getArea_unit() {
        return area_unit;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public List<HouseImageResult> getHouseImageResults() {
        return houseImageResults;
    }

    public List<HouseDaployResult> getHouseDaployResults() {
        return houseDaployResults;
    }

    public List<String> getConvenienceResults() {
        return convenienceResults;
    }

    public HouseInformationResult getHouseInformationResults() {
        return houseInformationResults;
    }

    public List<HouseUserImageResult> getUserImageResults() {
        return userImageResults;
    }

    public int getRopo() {
        return ropo;
    }

    @Override
    public String toString() {
        return "HouseDetailsResult{" +
                "house_id='" + house_id + '\'' +
                ", user='" + user + '\'' +
                ", house_nature='" + house_nature + '\'' +
                ", house_num='" + house_num + '\'' +
                ", place='" + place + '\'' +
                ", address='" + address + '\'' +
                ", house_type='" + house_type + '\'' +
                ", house_status='" + house_status + '\'' +
                ", payment='" + payment + '\'' +
                ", coin='" + coin + '\'' +
                ", price='" + price + '\'' +
                ", immigrant='" + immigrant + '\'' +
                ", lease='" + lease + '\'' +
                ", lease_unit='" + lease_unit + '\'' +
                ", practical='" + practical + '\'' +
                ", cover='" + cover + '\'' +
                ", construaction='" + construaction + '\'' +
                ", area_unit='" + area_unit + '\'' +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                ", houseImageResults=" + houseImageResults +
                ", houseDaployResults=" + houseDaployResults +
                ", convenienceResults=" + convenienceResults +
                ", houseInformationResults=" + houseInformationResults +
                ", userImageResults=" + userImageResults +
                ", ropo=" + ropo +
                '}';
    }
}
