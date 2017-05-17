package silverlion.com.house.newpassphone;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by k8190 on 2016/7/28.
 */
public interface NewPassPhoneView extends MvpView{

    void showProgress();

    void hideProgress();

    void showMessage(String msg);

    void GotoLogin();
}
