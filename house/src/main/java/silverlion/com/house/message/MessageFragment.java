package silverlion.com.house.message;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hannesdorfmann.mosby.mvp.MvpFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import silverlion.com.house.R;
import silverlion.com.house.commous.ActivityUtils;
import silverlion.com.house.components.ProgressDialogFragment;
import silverlion.com.house.message.xlistview.XListView;
import silverlion.com.house.qrcode.MipcaActivityCapture;


public class MessageFragment extends MvpFragment<MessageView,MessagePersenter> implements MessageView,XListView.IXListViewListener {
    private ActivityUtils activityUtils;
    private ProgressDialogFragment progressDialogFragment;
    @Bind(R.id.list)XListView listView;
    private String id;
    @Override
    public MessagePersenter createPresenter() {
        return new MessagePersenter();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
    }

    @OnClick(R.id.scan)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.scan:
                Intent intent = new Intent(getActivity(), MipcaActivityCapture.class);
                startActivity(intent);
                break;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_message, null);
        ButterKnife.bind(this,view);
        SharedPreferences spf = getActivity().getSharedPreferences("first",0);
        id = spf.getString("user_id",null);
        listView.setPullRefreshEnable(true);
        listView.setXListViewListener(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.GetMessage(id);
    }

    @Override
    public void updaUI(MessageResult result) {

    }

    @Override
    public void showMessage(String msg) {
        activityUtils.showToast(msg);
    }

    @Override
    public void showProgress() {
        activityUtils.hideSoftKeyboard();
        if (progressDialogFragment == null) {
            progressDialogFragment = new ProgressDialogFragment();
        }
        if (progressDialogFragment.isVisible()) return;
        progressDialogFragment.show(getFragmentManager(), "fragment_progress_dialog");
    }

    @Override
    public void hideProgress() {
        if (progressDialogFragment.isVisible() && progressDialogFragment != null)progressDialogFragment.dismiss();
    }
    //下啦刷新
    @Override
    public void onRefresh() {

    }
    //上啦加载
    @Override
    public void onLoadMore() {

    }
}
