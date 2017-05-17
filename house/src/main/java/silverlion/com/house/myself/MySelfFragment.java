package silverlion.com.house.myself;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.hannesdorfmann.mosby.mvp.MvpFragment;
import com.nostra13.universalimageloader.core.ImageLoader;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import silverlion.com.house.R;
import silverlion.com.house.commous.ActivityUtils;
import silverlion.com.house.components.ProgressDialogFragment;
import silverlion.com.house.login.LoginActivity;
import silverlion.com.house.myself.favorite.FavoriteActivity;
import silverlion.com.house.myself.mymark.BookActivity;
import silverlion.com.house.myself.safety.SafetyActivity;
import silverlion.com.house.personal.PersonalActivity;

public class MySelfFragment extends MvpFragment<MySelfView,MySelfPersenter> implements MySelfView {
    private ActivityUtils activityUtils;
    private ProgressDialogFragment progressDialogFragment;
    @Bind(R.id.user_hand)ImageView hand_iv;
    @Bind(R.id.user)TextView user_tv;
    private String account_id;

    @Override
    public MySelfPersenter createPresenter() {
        return new MySelfPersenter();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_self,null);
        ButterKnife.bind(this,view);
        SharedPreferences spf = getActivity().getSharedPreferences("first", 0);
        account_id = spf.getString("user_id", null);
        return view;
    }

    @Override
    public void onStart() {//从这里才写逻辑
        super.onStart();
        presenter.GetMyMessage(account_id);//获取数据
    }

    private final int MYSELF = 2;
    @OnClick({R.id.hand,R.id.depo,R.id.book,R.id.safety,R.id.exit})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.hand:
                Intent intent = new Intent(getActivity(),PersonalActivity.class);
                intent.putExtra("accout_id",account_id);
                intent.putExtra("select",MYSELF);
                startActivity(intent);
                break;
            case R.id.depo:
                activityUtils.startActivity(FavoriteActivity.class);
                break;
            case R.id.book:
                activityUtils.startActivity(BookActivity.class);
                break;
            case R.id.safety:
                activityUtils.startActivity(SafetyActivity.class);
                break;
            case R.id.exit:
                activityUtils.startActivity(LoginActivity.class);
                //本地广播
                break;
        }
    }

    @Override
    public void shouProgress() {
        activityUtils.hideSoftKeyboard();
        if (progressDialogFragment == null) {
            progressDialogFragment = new ProgressDialogFragment();
        }
        if (progressDialogFragment.isVisible()) return;
        progressDialogFragment.show(getFragmentManager(), "fragment_progress_dialog");
    }

    @Override
    public void hideProgress() {
        progressDialogFragment.dismiss();
    }

    @Override
    public void showMessage(String msg) {
        activityUtils.showToast(msg);
    }

    @Override
    public void updaUi(MySelfResult result) {
        user_tv.setText(result.getName());
        if(result.getList().get(0).getImage() != null){
            Uri uri = Uri.parse("http://casadiario.com/OpenDC/Public/Uploads/Account/account_head/"+result.getList().get(0).getImage());
            ImageLoader.getInstance().displayImage("http://casadiario.com/OpenDC/Public/Uploads/Account/account_head/"+result.getList().get(0).getImage(),hand_iv);
        }
    }
}
