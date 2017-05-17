package silverlion.com.house.forget;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
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
import silverlion.com.house.forgetemail.ForgetEmailActivity;
import silverlion.com.house.newpassphone.NewPassPhoneActivity;
import silverlion.com.house.register.User;
import silverlion.com.house.register.sortlistview.SoetiListActivity;

public class ForgetActivity extends MvpActivity<ForgetView,ForgetPresenter> implements ForgetView {
    private ActivityUtils activityUtils;
    private ProgressDialogFragment progressDialogFragment;
    private TextView area_tv,phone_tv,cancel,agree;
    private final int REQUST = 0x12;
    private String area = "0086";
    private String phone;
    private String verify;
    private String id;
    private boolean canRegister;
    private boolean canVerify;
    private AlertDialog alertDialog;
    @Bind(R.id.country)TextView country;
    @Bind(R.id.area_code)TextView area_code;
    @Bind(R.id.notphone)TextView notphone;
    @Bind(R.id.phone)EditText phone_et;
    @Bind(R.id.verification)EditText verify_et;
    @Bind(R.id.no_ver)TextView no_ver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
        setContentView(R.layout.activity_forget);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        phone_et.addTextChangedListener(mTextWatcher);
        verify_et.addTextChangedListener(mTextWatcher);
    }

    @OnClick({R.id.forget_email,R.id.cancel,R.id.number,R.id.forget,R.id.verification_text})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.forget_email:
                    activityUtils.startActivity(ForgetEmailActivity.class);
                break;
            case R.id.cancel:
                    finish();
                break;
            case R.id.number:
                    Intent intent = new Intent(ForgetActivity.this, SoetiListActivity.class);
                    startActivityForResult(intent,REQUST);
                break;
            case R.id.forget:
                    if (canRegister){
                        presenter.Verify(new User(verify,phone),id);
                    }else {
                        activityUtils.showToast("请输入手机号码和验证码");
                    }
                break;
            case R.id.verification_text:
                    if (canVerify) {
                        View aler = LayoutInflater.from(this).inflate(R.layout.context_aler_phone, null, false);
                        area_tv = (TextView) aler.findViewById(R.id.area);
                        phone_tv = (TextView) aler.findViewById(R.id.phone);
                        area_tv.setText("我们将发送验证手机号码 +"+area);
                        phone_tv.setText(phone);
                        aler.findViewById(R.id.agree).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                presenter.Forget(new User(area, phone));
                                alertDialog.dismiss();
                            }
                        });
                        aler.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.dismiss();
                            }
                        });
                        alertDialog = new AlertDialog.Builder(this).setView(aler).create();
                        alertDialog.show();
                    }else {
                        activityUtils.showToast("请输入正确的手机号码");
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
            notphone.setVisibility(View.INVISIBLE);
            no_ver.setVisibility(View.INVISIBLE);
            phone = phone_et.getText().toString();
            verify = verify_et.getText().toString();
            canRegister = RegexUtils.isPhone(phone) && !TextUtils.isEmpty(verify);
            canVerify = !TextUtils.isEmpty(phone)&&RegexUtils.isPhone(phone);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == resultCode){
            country.setText(data.getStringExtra("name"));
            if (data.getStringExtra("area") != null )area = data.getStringExtra("area");
            area_code.setText("+"+data.getStringExtra("area"));
        }
    }

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
        if (progressDialogFragment != null) progressDialogFragment.dismiss();
    }
    @Override
    public void showMessage(String msg) {
        activityUtils.showToast(msg);
    }
    @NonNull
    @Override
    public ForgetPresenter createPresenter() {
        return new ForgetPresenter();
    }

    @Override
    public void showPhoneText() {
        notphone.setVisibility(View.VISIBLE);
    }

    @Override
    public void showVerText() {
        no_ver.setVisibility(View.VISIBLE);
    }

    @Override
    public void getACcountID(String id) {
        this.id = id;
    }

    @Override
    public void GotoNewPassword(ForgetVerResult result) {
        Intent intent = new Intent(ForgetActivity.this,NewPassPhoneActivity.class);
        intent.putExtra("phone",result.getPhone());
        intent.putExtra("id",1);
        startActivity(intent);
        finish();
    }
}
