package silverlion.com.house.emailregister;

import android.util.Log;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import silverlion.com.house.net.NetClient;
import silverlion.com.house.register.RegisterResult;
import silverlion.com.house.register.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by k8190 on 2016/7/20.
 */
public class EmailPresenter extends MvpBasePresenter<EmailView> {
    private Call<RegisterResult> registerCall;
    private final int SUCCESS = 1;
    public void EmailRegister(User user){
        getView().showProgress();
        if (registerCall != null) registerCall.cancel();
        registerCall = NetClient.getInstance().getUserApi().EmailRegister(user.getArea_code());
        registerCall.enqueue(registerCallback);

    }

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
            if (result.getOutput() == SUCCESS) {
                getView().showMessage("成功");
                getView().GoToverify(result);
            } else {
                getView().showMessage("手机/邮箱已被注册，注册失败");
            }
        }

        @Override
        public void onFailure(Call<RegisterResult> call, Throwable t) {
            getView().hideProgress();
            getView().showMessage("注册失败");
        }
    };
}
