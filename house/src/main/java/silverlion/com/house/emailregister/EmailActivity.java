package silverlion.com.house.emailregister;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.hannesdorfmann.mosby.mvp.MvpActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import silverlion.com.house.R;
import silverlion.com.house.commous.ActivityUtils;
import silverlion.com.house.commous.RegexUtils;
import silverlion.com.house.components.ProgressDialogFragment;
import silverlion.com.house.register.RegisterResult;
import silverlion.com.house.register.User;
import silverlion.com.house.verify.VerfiyActivity;

public class EmailActivity extends MvpActivity<EmailView,EmailPresenter> implements EmailView {
    private final int EMAIL_ID = 0;
    private ActivityUtils activityUtils;
    private ProgressDialogFragment progressDialogFragment;
    @Bind(R.id.email)EditText email;
    @Bind(R.id.password)EditText password;
    private String email_id;
    private String passW;
    private boolean canRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
        setContentView(R.layout.activity_email);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        email.addTextChangedListener(mTextWatcher);
        password.addTextChangedListener(mTextWatcher);
    }
    @OnClick({R.id.cancel,R.id.register})
    public void onCilck(View view){
        switch (view.getId()){
            case R.id.cancel:
                finish();
                break;
            case R.id.register:
                if (canRegister){
                    presenter.EmailRegister(new User(email_id,passW));
                }else {
                    activityUtils.showToast("请检查邮箱和密码是否输入正确");
                }
                break;
        }
    }
    @OnCheckedChanged(R.id.showpassword)
    public void onChangeclick(boolean isChecked){
        if (isChecked) {
            password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
        else {
            password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }

    private final TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            email_id = email.getText().toString();
            passW = password.getText().toString();
            canRegister = RegexUtils.isEmail(email_id) && RegexUtils.verifyPassword(passW)==0;
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
    public void GoToverify(RegisterResult result) {
        Intent intent = new Intent(EmailActivity.this, VerfiyActivity.class);
        intent.putExtra("verfiy_id",result.getCode_id());
        intent.putExtra("verfiy",result.getVerify_code());
        intent.putExtra("id",EMAIL_ID);
        intent.putExtra("email",email_id);
        intent.putExtra("password",passW);
        startActivity(intent);
    }

    @NonNull
    @Override
    public EmailPresenter createPresenter() {
        return new EmailPresenter();
    }
}
