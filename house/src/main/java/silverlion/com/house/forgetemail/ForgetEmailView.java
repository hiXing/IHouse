package silverlion.com.house.forgetemail;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by k8190 on 2016/7/21.
 */
public interface ForgetEmailView extends MvpView {

    void showProgress();

    void hideProgress();

    void showMessage(String msg);

    void showVerify(int i);

    void hideVerify(int i);

    void getVerifyID(String id);

    void GotoNewPass();
}
