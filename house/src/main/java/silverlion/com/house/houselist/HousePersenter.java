package silverlion.com.house.houselist;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import silverlion.com.house.net.NetClient;
import silverlion.com.house.register.User;

/**
 * Created by k8190 on 2016/8/3.
 */
public class HousePersenter extends MvpBasePresenter<HouseView> {
    private Call<HouseResult> call;

    public void Collect(User user){//收藏
        if (call != null)call.cancel();
        call = NetClient.getInstance().getUserApi().Collect(user.getArea_code(),user.getCellphone(),"collect");
        call.enqueue(collectCall);
    }
    public void Cancel(User user){//取消
        if (call != null)call.cancel();
        call = NetClient.getInstance().getUserApi().Collect(user.getArea_code(),user.getCellphone(),"cancel");
        call.enqueue(collectCall);
    }
    //共用一个方法
    private Callback<HouseResult> collectCall = new Callback<HouseResult>() {
        @Override
        public void onResponse(Call<HouseResult> call, Response<HouseResult> response) {
            HouseResult result = response.body();
            if (result.getCollect().equals("1")){
                getView().shouMessage("收藏成功");
            }else if (result.getCollect().equals("0")){
                getView().shouMessage("取消收藏");
            }
        }

        @Override
        public void onFailure(Call<HouseResult> call, Throwable t) {
            getView().shouMessage("收藏失败");
        }
    };

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (!retainInstance){
            if (call != null) call.cancel();
        }
    }
}
