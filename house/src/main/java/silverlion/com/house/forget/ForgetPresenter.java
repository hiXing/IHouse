package silverlion.com.house.forget;

import android.util.Log;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import silverlion.com.house.net.NetClient;
import silverlion.com.house.register.User;

/**
 * Created by k8190 on 2016/7/20.
 */
public class ForgetPresenter extends MvpBasePresenter<ForgetView> {
    private Call<ForgetResult> call;
    private Call<ForgetVerResult> callver;

    public void Forget(User user){
        getView().showProgress();
        if (call != null)call.cancel();
        call = NetClient.getInstance().getUserApi().Forget(user.getArea_code(),user.getCellphone(),"get_code");
        call.enqueue(forgetcallback);
    }

    private Callback<ForgetResult> forgetcallback = new Callback<ForgetResult>() {
        @Override
        public void onResponse(Call<ForgetResult> call, Response<ForgetResult> response) {
            getView().hideProgress();
            ForgetResult result = response.body();
            Log.i("result",result.toString());
            if (result.getOutput().equals("0")){
                getView().showMessage("用户不存在");
                getView().showPhoneText();
                return;
            }
            getView().getACcountID(result.getCode_id());
        }

        @Override
        public void onFailure(Call<ForgetResult> call, Throwable t) {
            getView().hideProgress();
            getView().showPhoneText();
            getView().showMessage(t.toString());
        }
    };

    public void Verify(User user,String account_id){
        getView().showProgress();
        if (callver != null)callver.cancel();
        Log.i("result",account_id);
        callver = NetClient.getInstance().getUserApi().ForgetVer(account_id,user.getArea_code(),user.getCellphone(),"code_check");
        callver.enqueue(vercallback);
    }

    private Callback<ForgetVerResult> vercallback = new Callback<ForgetVerResult>() {
        @Override
        public void onResponse(Call<ForgetVerResult> call, Response<ForgetVerResult> response) {
            getView().hideProgress();
            ForgetVerResult result = response.body();
            Log.i("result",result.toString());
            if (result.getOutput().equals("0")){
                getView().showMessage("验证码错误");
                getView().showVerText();
                return;
            }
            getView().showMessage("bingo");
            getView().GotoNewPassword(result);
        }

        @Override
        public void onFailure(Call<ForgetVerResult> call, Throwable t) {
            getView().hideProgress();
            getView().showVerText();
        }
    };

}
