package silverlion.com.house.register;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by k8190 on 2016/7/20.
 */
public interface RegisterView extends MvpView{

    void ShowProgress();

    void HideProgress();

    void ShowMassage(String msg);

    void GoToverify(RegisterResult result);
}
