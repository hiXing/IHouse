package silverlion.com.house.verify;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by k8190 on 2016/7/22.
 */
public interface VerfiyView extends MvpView{

    void showProgress();

    void hideProgress();

    void showMessage(String msg);

    void GotoHome(VerfiyResponse response);

    void sendMessage();

    void GetAccouID(String id);
}
