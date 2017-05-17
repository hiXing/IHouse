package silverlion.com.house.houselist.housedetails;

import android.util.Log;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import silverlion.com.house.net.NetClient;
import silverlion.com.house.register.User;

/**
 * Created by k8190 on 2016/7/29.
 */
public class HouseDetailsPersenter extends MvpBasePresenter<HouseDetailsView>{
    private Call<HouseDetailsResult> call;

    public void HouseDetails(User user){
        if (call != null)call.cancel();
        call = NetClient.getInstance().getUserApi().HouseList(user.getArea_code(),user.getCellphone());
        call.enqueue(callback);
    }

    private Callback<HouseDetailsResult> callback = new Callback<HouseDetailsResult>() {
        @Override
        public void onResponse(Call<HouseDetailsResult> call, Response<HouseDetailsResult> response) {
            Log.i("result","woddao ");
            HouseDetailsResult result = response.body();
            Log.i("result",result.toString());
            getView().upDateUI(result);
        }

        @Override
        public void onFailure(Call<HouseDetailsResult> call, Throwable t) {
            if (t.toString() != null)getView().showMessage(t.toString());
        }
    };
}
