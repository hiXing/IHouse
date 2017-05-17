package silverlion.com.house.login;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import silverlion.com.house.net.NetClient;
import silverlion.com.house.register.User;

/**
 * Created by k8190 on 2016/7/19.
 */
public class LoginPresenter extends MvpBasePresenter<LoginView> {
    private Call<LoginResult> call;
    public void Login(User user){
        if (call != null)call.cancel();
        call = NetClient.getInstance().getUserApi().Login(user.getArea_code(),user.getCellphone());
        call.enqueue(logincallback);
    }

    private Callback<LoginResult> logincallback = new Callback<LoginResult>() {
        @Override
        public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
            LoginResult result = response.body();
            if (result.getAccout().equals("0")){
                getView().ShowMassage("账号不存在或者账号密码有误");
                return;
            }
            getView().GotoMain(result);
        }

        @Override
        public void onFailure(Call<LoginResult> call, Throwable t) {
            getView().ShowMassage("账号不存在或者账号密码有误");
        }
    };

}
