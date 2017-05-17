package silverlion.com.house.newpassphone;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import silverlion.com.house.R;
import silverlion.com.house.commous.ActivityUtils;
import silverlion.com.house.commous.RegexUtils;
import silverlion.com.house.components.ProgressDialogFragment;
import silverlion.com.house.login.LoginActivity;
import silverlion.com.house.register.User;

public class NewPassPhoneActivity extends MvpActivity<NewPassPhoneView,NewPassPhonePresenter> implements NewPassPhoneView {
    private ProgressDialogFragment progressDialogFragment;
    private ActivityUtils activityUtils;
    private String phone;
    private String password;
    private String agree;
    private String email;
    private int id;
    private boolean action;
    @Bind(R.id.password)EditText password_et;
    @Bind(R.id.agree)EditText agree_et;
    @Bind(R.id.notphone)TextView not;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
        setContentView(R.layout.activity_new_pass_phone);
        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");
        email = intent.getStringExtra("email");
        id = intent.getIntExtra("id",0);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        agree_et.addTextChangedListener(listener);
    }

    @OnClick({R.id.cancel,R.id.action})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.cancel:
                finish();
                break;
            case R.id.action:
                    if (action){
                        if (id == 1){
                            presenter.Action(new User(password, phone));
                        }else if (id == 2){
                            presenter.EmailACtion(new User(password,email));
                        }
                    }else {
                        activityUtils.showToast("请输入正确的密码");
                        not.setVisibility(View.VISIBLE);
                    }
                break;
        }
    }

    private TextWatcher listener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            not.setVisibility(View.INVISIBLE);
            password = password_et.getText().toString();
            agree = agree_et.getText().toString();
            action = password.equals(agree)&& RegexUtils.verifyPassword(password)==0 && !TextUtils.isEmpty(password);
        }
    };

    @Override
    public void showProgress() {
        activityUtils.hideSoftKeyboard();
        if (progressDialogFragment == null) {
            progressDialogFragment = new ProgressDialogFragment();
        }
        if (progressDialogFragment.isVisible()) return;
        progressDialogFragment.show(getSupportFragmentManager(), "fragment_progress_dialog");
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
    public void GotoLogin() {
        activityUtils.startActivity(LoginActivity.class);
        finish();
    }

    @NonNull
    @Override
    public NewPassPhonePresenter createPresenter() {
        return new NewPassPhonePresenter();
    }
}
