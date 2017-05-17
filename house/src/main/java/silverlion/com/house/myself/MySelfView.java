package silverlion.com.house.myself;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by k8190 on 2016/8/4.
 */
public interface MySelfView extends MvpView{

    void shouProgress();

    void hideProgress();

    void showMessage(String msg);

    void updaUi(MySelfResult result);
}
