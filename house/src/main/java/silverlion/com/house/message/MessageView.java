package silverlion.com.house.message;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by k8190 on 2016/7/27.
 */
public interface MessageView extends MvpView{

    void showProgress();

    void hideProgress();

    void showMessage(String msg);

    void updaUI(MessageResult result);
}
