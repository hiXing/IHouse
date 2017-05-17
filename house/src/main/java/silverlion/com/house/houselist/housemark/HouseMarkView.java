package silverlion.com.house.houselist.housemark;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by k8190 on 2016/7/29.
 */
public interface HouseMarkView extends MvpView {

    void showProgress();

    void hideProgress();

    void showMessage(String msg);

    void updateUI();

}
