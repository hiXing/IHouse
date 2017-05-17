package silverlion.com.house.myself;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by k8190 on 2016/8/4.
 */
public class MySelfResult {

    @SerializedName("id")//app用户id	id
    private String id;

    @SerializedName("real_name")//姓名	real_name
    private String name;

    @SerializedName("0")//           头像数据	键值为0的一维数组  head_image
    private List<HeadImage> list;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<HeadImage> getList() {
        return list;
    }

    public class HeadImage{
        @SerializedName("head_image")
        private String image;

        public String getImage() {
            return image;
        }
    }

}
