package silverlion.com.house.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import com.hannesdorfmann.mosby.mvp.MvpActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import silverlion.com.house.MainActivity;
import silverlion.com.house.R;
import silverlion.com.house.commous.ActivityUtils;
import silverlion.com.house.commous.RegexUtils;
import silverlion.com.house.components.ActionProcessButton;
import silverlion.com.house.components.ProgressDialogFragment;
import silverlion.com.house.components.ProgressGenerator;
import silverlion.com.house.forget.ForgetActivity;
import silverlion.com.house.register.RegisterActivity;
import silverlion.com.house.register.User;
import android.content.SharedPreferences.Editor;

public class LoginActivity extends MvpActivity<LoginView,LoginPresenter> implements LoginView,ProgressGenerator.OnCompleteListener{
    @Bind(R.id.account_editext)EditText account_ed;
    @Bind(R.id.password_editext)EditText password_ed;
    @Bind(R.id.login_btn)ActionProcessButton login_btn;
    private ProgressDialogFragment progressDialogFragment;
    private ProgressGenerator progressGenerator;

    @Override
    public void onComplete() {
        presenter.Login(new User(account,password));
    }

    private ActivityUtils activityUtils;
    private String account;
    private String password;
    private boolean CanLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
        setContentView(R.layout.activity_login);
        login_btn.setMode(ActionProcessButton.Mode.ENDLESS);
    }
    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        account_ed.addTextChangedListener(mTextWatcher);
        password_ed.addTextChangedListener(mTextWatcher);
    }
    @NonNull
    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @OnClick({R.id.register, R.id.forget_password,R.id.login_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register:
                    activityUtils.startActivity(RegisterActivity.class);
                break;
            case R.id.forget_password:
                    activityUtils.startActivity(ForgetActivity.class);
                break;
            case R.id.login_btn:
                if (CanLogin){
                    progressGenerator = new ProgressGenerator(this);
                    progressGenerator.start(login_btn);
//                    presenter.Login(new User(account, password));
                }else {
                    activityUtils.showToast("请输入账号和密码");
                }
                break;
        }
    }

    private final TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            account = account_ed.getText().toString();
            password = password_ed.getText().toString();
            CanLogin = !TextUtils.isEmpty(account) && RegexUtils.verifyPassword(password)==0;
        }
    };
    @Override
    public void ShowMassage(String msg) {
        activityUtils.showToast(msg);
    }

    @Override
    public void ShowProgress() {
        activityUtils.hideSoftKeyboard();
        if (progressDialogFragment == null) {
            progressDialogFragment = new ProgressDialogFragment();
        }
        if (progressDialogFragment.isVisible()) return;
        progressDialogFragment.show(getSupportFragmentManager(), "fragment_progress_dialog");
    }

    @Override
    public void HideProgress() {
        if (progressDialogFragment != null)progressDialogFragment.dismiss();
    }

    @Override
    public void GotoMain(LoginResult result) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        SharedPreferences spf = getSharedPreferences("first",0);//保存用户信息
        Editor edit = spf.edit();
        edit.putString("user_id",result.getAccout());
        edit.commit();
        intent.putExtra("account_id",result.getAccout());
        intent.putExtra("nofi",result.getNo_file());
        startActivity(intent);
        finish();
    }
}
