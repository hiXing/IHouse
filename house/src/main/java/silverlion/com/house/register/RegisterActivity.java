package silverlion.com.house.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import silverlion.com.house.R;
import silverlion.com.house.commous.ActivityUtils;
import silverlion.com.house.commous.RegexUtils;
import silverlion.com.house.components.ProgressDialogFragment;
import silverlion.com.house.emailregister.EmailActivity;
import silverlion.com.house.register.sortlistview.SoetiListActivity;
import silverlion.com.house.verify.VerfiyActivity;

/**
 * Created by k8190 on 2016/7/20.
 */
public class RegisterActivity extends MvpActivity<RegisterView,RegisterPresenter> implements RegisterView{
    private final int PHOME_ID = 1;
    private final int REQUEST = 0x12;
    private ActivityUtils activityUtils;
    private ProgressDialogFragment progressDialogFragment;
    @Bind(R.id.country)TextView country;
    @Bind(R.id.area_code)TextView area_code;
    @Bind(R.id.phone)EditText phone_number;
    @Bind(R.id.password)EditText password;
    @Bind(R.id.showpassword)CheckBox showpassword;
    private String phone;
    private String passW;
    private String  area = "0086";
    private boolean canRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
        setContentView(R.layout.activity_register);
    }
    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        phone_number.addTextChangedListener(mTextWatcher);
        password.addTextChangedListener(mTextWatcher);
    }

    private final TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            phone = phone_number.getText().toString();
            passW = password.getText().toString();
            canRegister = RegexUtils.isPhone(phone) && RegexUtils.verifyPassword(passW)==0;
        }
    };
    @OnCheckedChanged(R.id.showpassword)
    public void onChangeclick(boolean isChecked){
        if (isChecked) {
            password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            }
        else {
            password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }

    @OnClick({R.id.email,R.id.cancel,R.id.number,R.id.register})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.email:
                activityUtils.startActivity(EmailActivity.class);
                break;
            case R.id.cancel:
                finish();
                break;
            case R.id.number:
                Intent intent = new Intent(RegisterActivity.this, SoetiListActivity.class);
                startActivityForResult(intent,REQUEST);
                break;
            case R.id.register:
                if (canRegister){
                    presenter.register(new User(area,phone));
                }else {
                    activityUtils.showToast("请检查手机和密码是否输入正确");
                }
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == resultCode){
            country.setText(data.getStringExtra("name"));
            area = data.getStringExtra("area");
            area_code.setText("+"+data.getStringExtra("area"));
        }
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
    public void HideProgress() {progressDialogFragment.dismiss();}
    @Override
    public void ShowMassage(String msg) {activityUtils.showToast(msg);}
    @Override
    public void GoToverify(RegisterResult result) {
        Intent intent = new Intent(RegisterActivity.this, VerfiyActivity.class);
        intent.putExtra("verfiy_id",result.getCode_id());
        intent.putExtra("verfiy",result.getVerify_code());
        intent.putExtra("area",area);
        intent.putExtra("id",PHOME_ID);
        intent.putExtra("password",passW);
        intent.putExtra("phone",phone);
        startActivity(intent);
    }
    @NonNull
    @Override
    public RegisterPresenter createPresenter() {
        return new RegisterPresenter();
    }
}
