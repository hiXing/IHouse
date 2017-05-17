package silverlion.com.house.personal;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by k8190 on 2016/7/25.
 */
public interface PersonalView extends MvpView {

    void showProgress();

    void hideProgress();

    void showMessage(String msg);

    void GotoMain();

    void updateAvatar(String url);

    void updatePersonal(PersonalResult result);
}
