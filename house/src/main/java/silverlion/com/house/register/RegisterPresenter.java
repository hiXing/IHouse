package silverlion.com.house.register;

import android.util.Log;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import silverlion.com.house.net.NetClient;

/**
 * Created by k8190 on 2016/7/20.
 */
public class RegisterPresenter extends MvpBasePresenter<RegisterView> {
    private Call<RegisterResult> registerCall;
    private User user;
    private final int SUCCESS = 1;

    public void register(User user){
        this.user = user;
        getView().ShowProgress();
        if (registerCall != null) registerCall.cancel();
        registerCall = NetClient.getInstance().getUserApi().Register(user.getArea_code(),user.getCellphone());
        registerCall.enqueue(registerCallback);
    }

    private Callback<RegisterResult> registerCallback = new Callback<RegisterResult>() {
        @Override
        public void onResponse(Call<RegisterResult> call, Response<RegisterResult> response) {
            getView().HideProgress();
            RegisterResult result = response.body();
            Log.i("result",result.toString());
            if (result == null) {
                getView().ShowMassage("Unknown Error");
                return;
            }
            if (result.getOutput() == SUCCESS) {
                getView().ShowMassage("成功");
                getView().GoToverify(result);
            } else {
                getView().ShowMassage("手机/邮箱已被注册，注册失败");
            }
        }

        @Override
        public void onFailure(Call<RegisterResult> call, Throwable t) {
            getView().HideProgress();
            getView().ShowMassage("注册失败");
        }
    };

    @Override public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (!retainInstance){
            if (registerCall != null) registerCall.cancel();
        }
    }
}
