package silverlion.com.house.newpassphone;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import silverlion.com.house.forget.ForgetVerResult;
import silverlion.com.house.net.NetClient;
import silverlion.com.house.register.User;

/**
 * Created by k8190 on 2016/7/28.
 */
public class NewPassPhonePresenter extends MvpBasePresenter<NewPassPhoneView> {
    private Call<ForgetVerResult> emailcall;
    private Call<ForgetVerResult> call;

    public void Action(User user){
        getView().showProgress();
        if (call != null)call.cancel();
        call = NetClient.getInstance().getUserApi().NewPass(user.getCellphone(),user.getArea_code(),"new_password");
        call.enqueue(callback);
    }

    private Callback<ForgetVerResult> callback = new Callback<ForgetVerResult>() {
        @Override
        public void onResponse(Call<ForgetVerResult> call, Response<ForgetVerResult> response) {
            getView().hideProgress();
            ForgetVerResult result = response.body();
            if (result.getOutput().equals("0")){
                getView().showMessage("修改失败");
                return;
            }
            getView().showMessage("修改成功");
            getView().GotoLogin();
        }

        @Override
        public void onFailure(Call<ForgetVerResult> call, Throwable t) {
            getView().hideProgress();
            getView().showMessage("修改失败");
        }
    };

    public void EmailACtion(User user){
        getView().showProgress();
        if (emailcall != null)emailcall.cancel();
        emailcall = NetClient.getInstance().getUserApi().EmailNewPass(user.getCellphone(),user.getArea_code(),"new_password");
        emailcall.enqueue(emailback);
    }

    private Callback<ForgetVerResult> emailback = new Callback<ForgetVerResult>() {
        @Override
        public void onResponse(Call<ForgetVerResult> call, Response<ForgetVerResult> response) {
            getView().hideProgress();
            ForgetVerResult result = response.body();
            if (result.getOutput().equals("0")){
                getView().showMessage("修改失败");
                return;
            }
            getView().showMessage("修改成功");
            getView().GotoLogin();
        }

        @Override
        public void onFailure(Call<ForgetVerResult> call, Throwable t) {
            getView().hideProgress();
            getView().showMessage("修改失败");
        }
    };

}
