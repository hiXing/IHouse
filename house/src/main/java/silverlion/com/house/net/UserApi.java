package silverlion.com.house.net;

import java.io.File;

import retrofit2.http.Query;
import silverlion.com.house.forget.ForgetResult;
import silverlion.com.house.forget.ForgetVerResult;
import silverlion.com.house.houselist.HouseResult;
import silverlion.com.house.houselist.housedetails.HouseDetailsResult;
import silverlion.com.house.houselist.housemark.HouseMarkResult;
import silverlion.com.house.login.LoginResult;
import silverlion.com.house.message.MessageResult;
import silverlion.com.house.myself.MySelfResult;
import silverlion.com.house.personal.PersonalResult;
import silverlion.com.house.register.RegisterResult;
import retrofit2.Call;
import retrofit2.http.POST;
import silverlion.com.house.verify.VerfiyResponse;
import silverlion.com.house.verify.VerfiyResult;

/**
 * 用户相关接口
 */
public interface UserApi {
    //手机注册
    @POST("/OpenDC/index.php/App/Account/reg_check")
    Call<RegisterResult> Register(@Query("area_code")String ara_code,@Query("cellphone")String cellphone);
    //邮箱注册
    @POST("/OpenDC/index.php/App/Account/reg_check")
    Call<RegisterResult> EmailRegister(@Query("email")String email);
    //验证码
    @POST("/OpenDC/index.php/App/Account/code_check")
    Call<VerfiyResult> Verfiy(@Query("code_id")String code_id,@Query("verify_code")String verfiy_code);
    //手机注册
    @POST("/OpenDC/index.php/App/Account/register")
    Call<VerfiyResponse> LastPhoneRegister(@Query("area_code")String area,
                                           @Query("cellphone")String phone,
                                           @Query("password")String password);
    //邮箱注册
    @POST("/OpenDC/index.php/App/Account/register")
    Call<VerfiyResponse> LastEmailRegister(@Query("email")String email, @Query("password")String password);
    //保存个人资料
    @POST("/OpenDC/index.php/App/Account/info_confirm")
    Call<VerfiyResponse> Keep(@Query("account_id")String account,@Query("real_name")String name,
                              @Query("sex")String sex,@Query("place")String address,
                              @Query("area_code")String area,@Query("contact_phone")String phone,
                              @Query("contact_email")String email,@Query("account_head")String url);
    //登陆
    @POST("/OpenDC/index.php/App/Account/login")
    Call<LoginResult> Login(@Query("account")String account,@Query("password")String password);
    //手机获取验证码
    @POST("/OpenDC/index.php/App/Account/psd_back")
    Call<ForgetResult> Forget(@Query("area_code")String area,@Query("cellphone")String phone,@Query("action1")String action);
    //手机检查验证码
    @POST("/OpenDC/index.php/App/Account/psd_back")
    Call<ForgetVerResult> ForgetVer(@Query("code_id")String code_id,@Query("verify_code")String verify,
                                    @Query("cellphone")String phone,@Query("action2")String action);
    //手机修改新密码
    @POST("/OpenDC/index.php/App/Account/psd_back")
    Call<ForgetVerResult> NewPass(@Query("cellphone")String phone,@Query("new_password")String password,@Query("action3")String action);
    //邮箱获取验证码
    @POST("/OpenDC/index.php/App/Account/psd_back")
    Call<ForgetResult> EmailForget(@Query("email")String email,@Query("action1")String action);
    //邮箱检查验证码
    @POST("/OpenDC/index.php/App/Account/psd_back")
    Call<ForgetVerResult> EmailForgetVer(@Query("code_id")String id,@Query("verify_code")String verify,
                                         @Query("email")String email,@Query("action2")String action);
    //邮箱修改新密码
    @POST("/OpenDC/index.php/App/Account/psd_back")
    Call<ForgetVerResult> EmailNewPass(@Query("email")String email,@Query("new_password")String password,@Query("action3")String action);
    //
    @POST("/OpenDC/index.php/App/House/house_detail")
    Call<HouseDetailsResult> HouseList(@Query("house_id")String houseID ,@Query("account_id")String accountID);
    //收藏与取消
    @POST("/OpenDC/index.php/App/Account/collect_action")
    Call<HouseResult> Collect(@Query("house_id")String house_id,@Query("account_id")String id,@Query("action")String action);
    //获取个人信息
    @POST("/OpenDC/index.php/App/Account/index")
    Call<MySelfResult> MySelf(@Query("account_id")String id);
    //获取信息列表
    @POST("/OpenDC/index.php/App/Message/message_list")
    Call<MessageResult> Message(@Query("account_id")String id);
    //获取当日吉时
    @POST("/OpenDC/index.php/App/House/user_busy")
    Call<HouseMarkResult> Busy(@Query("user_id")String id,@Query("busy_date")String date);
    //更新用户信息
    @POST("/OpenDC/index.php/App/Account/info_update")
    Call<VerfiyResponse> Update(@Query("account")String id,@Query("real_name")String name,@Query("sex")String sex,
                                @Query("place")String place,@Query("area_code")String area,@Query("contact_phone")String phone,
                                @Query("contact_email")String email,@Query("account_head")String file);
    //获取个人详细信息
    @POST("/OpenDC/index.php/App/Account/account_detail")
    Call<PersonalResult> GetPersonal(@Query("account_id")String id);
}
