package silverlion.com.house.verify;

import android.util.Log;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import silverlion.com.house.net.NetClient;
import silverlion.com.house.register.RegisterResult;
import silverlion.com.house.register.User;

/**
 * Created by k8190 on 2016/7/22.
 */
public class VerfiyPresenter extends MvpBasePresenter<VerfiyView>{
    private Call<VerfiyResult> verfiyCall;
    private Call<VerfiyResponse> Response;
    private String area;
    private String  phone;
    private String password;
    private String email;
    private int proofread;
    private final int EMAIL_ID = 0;
    private final int PHONE_ID = 1;
    private Call<RegisterResult> registerCall;

    public void Verfiy(User user,String area,String phone,String password,String email,int proofread){
        getView().showProgress();
        this.area = area;
        this.phone = phone;
        this.password = password;
        this.email = email;
        this.proofread = proofread;
        if (verfiyCall != null)verfiyCall.cancel();
        Log.i("result",user.getArea_code()+"----"+user.getCellphone());
        verfiyCall = NetClient.getInstance().getUserApi().Verfiy(user.getArea_code(),user.getCellphone());
        verfiyCall.enqueue(verfiyCallback);
    }

    public void register(User user,int id ){
        getView().showProgress();
        if (id == PHONE_ID){
            if (registerCall != null) registerCall.cancel();
            registerCall = NetClient.getInstance().getUserApi().Register(user.getArea_code(), user.getCellphone());
            registerCall.enqueue(registerCallback);
        }else if (id == EMAIL_ID){
            if (registerCall != null) registerCall.cancel();
            registerCall = NetClient.getInstance().getUserApi().EmailRegister(user.getArea_code());
            registerCall.enqueue(registerCallback);
        }
    }

    private Callback<VerfiyResult> verfiyCallback = new Callback<VerfiyResult>() {
        @Override
        public void onResponse(Call<VerfiyResult> call, Response<VerfiyResult> response) {

            getView().hideProgress();
            VerfiyResult result= response.body();
            Log.i("result",result.toString());
            if (result.getOutput().equals("0")){
                getView().showMessage("验证码错误");
                return;
            }
            if (proofread == PHONE_ID){
                Response = NetClient.getInstance().getUserApi().LastPhoneRegister(area,phone,password);
                Response.enqueue(ResponseCallback);
            }else if (proofread == EMAIL_ID){
                Response = NetClient.getInstance().getUserApi().LastEmailRegister(email,password);
                Response.enqueue(ResponseCallback);
            }
        }

        @Override
        public void onFailure(Call<VerfiyResult> call, Throwable t) {
            getView().hideProgress();
            getView().sendMessage();
            getView().showMessage(t.toString());
        }
    };

    private Callback<VerfiyResponse> ResponseCallback = new Callback<VerfiyResponse>() {
        @Override
        public void onResponse(Call<VerfiyResponse> call, Response<VerfiyResponse> response) {
            getView().showMessage("注册成功");
            getView().GotoHome(response.body());
        }

        @Override
        public void onFailure(Call<VerfiyResponse> call, Throwable t) {
            getView().showMessage(t.toString());
        }
    };
    //手机和邮箱注册公用
    private Callback<RegisterResult> registerCallback = new Callback<RegisterResult>() {
        @Override
        public void onResponse(Call<RegisterResult> call, Response<RegisterResult> response) {
            getView().hideProgress();
            RegisterResult result = response.body();
            Log.i("result",result.toString());
            if (result == null) {
                getView().showMessage("Unknown Error");
                return;
            }
            //发信息
            getView().GetAccouID(result.getCode_id());
        }
        @Override
        public void onFailure(Call<RegisterResult> call, Throwable t) {
            getView().hideProgress();
        }
    };

    @Override public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (!retainInstance){
            if (registerCall != null) registerCall.cancel();
        }
        if (!retainInstance){
            if (verfiyCall != null) verfiyCall.cancel();
        }
        if (!retainInstance){
            if (Response != null) Response.cancel();
        }

    }
}
