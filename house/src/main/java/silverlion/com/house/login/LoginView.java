package silverlion.com.house.login;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by k8190 on 2016/7/19.
 */
public interface LoginView extends MvpView{

    void ShowProgress();

    void HideProgress();

    void ShowMassage(String msg);

    void GotoMain(LoginResult result);
}
