package silverlion.com.house.houselist.housemark;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import silverlion.com.house.net.NetClient;
import silverlion.com.house.register.User;

/**
 * Created by k8190 on 2016/7/29.
 */
public class HouseMarkPersenter extends MvpBasePresenter<HouseMarkView> {
    private Call<HouseMarkResult> busyCall;

    public void Busy(User user){
        if (busyCall != null)busyCall.cancel();
        busyCall = NetClient.getInstance().getUserApi().Busy(user.getArea_code(),user.getCellphone());
        busyCall.enqueue(busycallback);
    }

    private Callback<HouseMarkResult> busycallback = new Callback<HouseMarkResult>() {
        @Override
        public void onResponse(Call<HouseMarkResult> call, Response<HouseMarkResult> response) {

        }

        @Override
        public void onFailure(Call<HouseMarkResult> call, Throwable t) {

        }
    };
}
