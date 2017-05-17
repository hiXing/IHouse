package silverlion.com.house.myself;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import silverlion.com.house.net.NetClient;

/**
 * Created by k8190 on 2016/8/4.
 */
public class MySelfPersenter extends MvpBasePresenter<MySelfView>{
    Call<MySelfResult> call;

    public void GetMyMessage(String id){
        if (call != null)call.cancel();
        call = NetClient.getInstance().getUserApi().MySelf(id);
        call.enqueue(callback);
    }

    private Callback<MySelfResult> callback = new Callback<MySelfResult>() {
        @Override
        public void onResponse(Call<MySelfResult> call, Response<MySelfResult> response) {
            getView().updaUi(response.body());
        }

        @Override
        public void onFailure(Call<MySelfResult> call, Throwable t) {
            getView().showMessage("个人信息获取失败");
        }
    };
}
