package silverlion.com.house.houselist.housedetails;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by k8190 on 2016/7/29.
 */
public interface HouseDetailsView extends MvpView{

    void showProgress();

    void hideProgress();

    void showMessage(String msg);

    void upDateUI(HouseDetailsResult result);
}
