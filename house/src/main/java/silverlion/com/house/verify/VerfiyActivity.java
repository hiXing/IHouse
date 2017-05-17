package silverlion.com.house.verify;

import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import silverlion.com.house.personal.PersonalActivity;
import silverlion.com.house.register.User;

public class VerfiyActivity extends MvpActivity<VerfiyView,VerfiyPresenter> implements VerfiyView {
    private ActivityUtils activityUtils;
    private ProgressDialogFragment progressDialogFragment;
    @Bind(R.id.verfiy)EditText verfiy_et;
    @Bind(R.id.notverfiy)TextView not_verfiy;
    @Bind(R.id.title)TextView title;
    @Bind(R.id.service)TextView service;

    private String phone;
    private String password;
    private String email;
    private String verfiy_id;
    private String verfiy;
    private String area;
    private int id ;
    private Handler handler = new Handler(){
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            int what = msg.what;
            if (msg.what == 0x18){
                int i = (int) msg.obj;
                service.setEnabled(false);
                service.setText("重新获取验证码（"+i+"）");
            }else if(msg.what == 0x20){
                service.setEnabled(true);
                service.setText("重新获取验证码");
            }else if (msg.what == 0x30){
                not_verfiy.setVisibility(View.VISIBLE);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
        setContentView(R.layout.activity_verfiy);
        Intent intent = getIntent();
        verfiy_id = intent.getStringExtra("verfiy_id");
        password = intent.getStringExtra("password");
        phone = intent.getStringExtra("phone");
        email = intent.getStringExtra("email");
        area = intent.getStringExtra("area");
        id = intent.getIntExtra("id",3);
        init();
    }

    private void init(){
       if (phone != null){
           title.setText("验证码以发送至"+"+"+area+" "+phone+"，请填写验证码");
       }else if (email != null){
           title.setText("验证码以发送至"+email+"，请填写验证码");
       }
       new Thread(new Runnable() {
           @Override
           public void run() {
               for (int i = 60;i >0;i-- ){
                   try {
                       Thread.sleep(1000);
                       Message msg = Message.obtain();
                       msg.what = 0x18;
                       msg.obj = i;
                       handler.sendMessage(msg);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }
               Message msg = Message.obtain();
               msg.what = 0x20;
               handler.sendMessage(msg);
           }
       }).start();
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        verfiy_et.addTextChangedListener(mTextWatcher);
    }

    private final TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            verfiy = verfiy_et.getText().toString();
        }
    };

    @Override
    public void GetAccouID(String id) {
        this.verfiy_id = id;
    }

    @OnClick({R.id.register,R.id.cancel,R.id.service})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.register:
                Log.i("result",verfiy_id+"----"+verfiy);
                presenter.Verfiy(new User(verfiy_id,verfiy),area,phone,password,email,id);
                break;
            case R.id.cancel:
                finish();
                break;
            case R.id.service:
                init();
                if (id == 1) presenter.register(new User(area,phone),id);
                if (id == 0) presenter.register(new User(email,null),id);
                break;
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
        progressDialogFragment.dismiss();
    }

    @Override
    public void showMessage(String msg) {
        activityUtils.showToast(msg);
    }


    private final int VERFIY = 1;

    @Override
    public void GotoHome(VerfiyResponse response) {
        Intent intent = new Intent(VerfiyActivity.this,PersonalActivity.class);
        intent.putExtra("accout_id",response.getAccout_id());
        intent.putExtra("select",VERFIY);
        startActivity(intent);
        finish();
    }

    @Override
    public void sendMessage() {
        Message msg = Message.obtain();
        msg.what = 0x30;
        handler.sendMessage(msg);
    }

    @NonNull
    @Override
    public VerfiyPresenter createPresenter() {
        return new VerfiyPresenter();
    }

}
