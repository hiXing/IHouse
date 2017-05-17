package silverlion.com.house.message;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import silverlion.com.house.net.NetClient;

/**
 * Created by k8190 on 2016/7/27.
 */
public class MessagePersenter extends MvpBasePresenter<MessageView> {
    private Call<MessageResult> call;

    public void GetMessage(String id){
        getView().showProgress();
        if (call != null)call.cancel();
        call = NetClient.getInstance().getUserApi().Message(id);
        call.enqueue(callback);
    }

    private Callback<MessageResult> callback = new Callback<MessageResult>() {
        @Override
        public void onResponse(Call<MessageResult> call, Response<MessageResult> response) {
            getView().hideProgress();
            getView().updaUI(response.body());
        }

        @Override
        public void onFailure(Call<MessageResult> call, Throwable t) {
            getView().hideProgress();
            getView().showMessage("请检查网络连接");
        }
    };
}
