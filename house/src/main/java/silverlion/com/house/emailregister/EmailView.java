package silverlion.com.house.emailregister;

import com.hannesdorfmann.mosby.mvp.MvpView;
import silverlion.com.house.register.RegisterResult;

/**
 * Created by k8190 on 2016/7/20.
 */
public interface EmailView extends MvpView {

    void showProgress();

    void hideProgress();

    void showMessage(String msg);

    void GoToverify(RegisterResult result);
}
