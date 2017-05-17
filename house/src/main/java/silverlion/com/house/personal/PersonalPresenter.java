package silverlion.com.house.personal;

import android.util.Log;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import silverlion.com.house.commous.UserPrefs;
import silverlion.com.house.net.NetClient;
import silverlion.com.house.verify.VerfiyResponse;
import silverlion.com.house.verify.VerfiyResult;

/**
 * Created by k8190 on 2016/7/25.
 */
public class PersonalPresenter extends MvpBasePresenter<PersonalView> {
    private Call<VerfiyResponse> call;
    private  Call<VerfiyResponse> mycall;
    private Call<PersonalResult> personCall;

    public void keep(String id,String name ,String sex,String address,String area,String phone,String email,String url){
        getView().showProgress();
        if (call != null)call.cancel();
        UserPrefs.getInstance().setAvatar(NetClient.BASE_URL + url);
        call = NetClient.getInstance().getUserApi().Keep(id, name, sex, address, area, phone, email, url);
        call.enqueue(callback);
    }

    private Callback<VerfiyResponse> callback = new Callback<VerfiyResponse>() {
        @Override
        public void onResponse(Call<VerfiyResponse> call, Response<VerfiyResponse> response) {
            Log.i("result", response.body().toString());
            getView().hideProgress();
            getView().GotoMain();
        }

        @Override
        public void onFailure(Call<VerfiyResponse> call, Throwable t) {
            getView().hideProgress();
            getView().showMessage(t.toString());
        }
    };

    public void UpDate(String id,String name ,String sex,String address,String area,String phone,String email,String url){
        getView().showProgress();
        if (mycall != null)mycall.cancel();
        UserPrefs.getInstance().setAvatar(NetClient.BASE_URL + url);
        mycall = NetClient.getInstance().getUserApi().Update(id, name, sex, address, area, phone, email, url);
        mycall.enqueue(mycallback);
    }

    private Callback<VerfiyResponse> mycallback = new Callback<VerfiyResponse>() {
        @Override
        public void onResponse(Call<VerfiyResponse> call, Response<VerfiyResponse> response) {
            getView().hideProgress();
        }

        @Override
        public void onFailure(Call<VerfiyResponse> call, Throwable t) {
            getView().hideProgress();
            getView().showMessage("保存失败");
        }
    };

    public void getPersonal(String id){
        getView().showProgress();
        if (personCall != null)personCall.cancel();
        personCall = NetClient.getInstance().getUserApi().GetPersonal(id);
        personCall.enqueue(Personback);
    }

    private Callback<PersonalResult> Personback = new Callback<PersonalResult>() {
        @Override
        public void onResponse(Call<PersonalResult> call, Response<PersonalResult> response) {
            getView().hideProgress();
            if (response.body() != null) getView().updatePersonal(response.body());
        }

        @Override
        public void onFailure(Call<PersonalResult> call, Throwable t) {
            getView().hideProgress();
            getView().showMessage("获取失败");
        }
    };

    @Override public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (!retainInstance){
            if (call != null) call.cancel();
            if (mycall != null) call.cancel();
            if (personCall != null)personCall.cancel();
        }
    }
}
