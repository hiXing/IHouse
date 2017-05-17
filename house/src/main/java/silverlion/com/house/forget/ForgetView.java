package silverlion.com.house.forget;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by k8190 on 2016/7/20.
 */
public interface ForgetView extends MvpView{

    void showProgress();

    void hideProgress();

    void showMessage(String msg);

    void showPhoneText();

    void showVerText();

    void GotoNewPassword(ForgetVerResult result);

    void getACcountID(String id);
}
