package com.example.fdcapp.activity;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fdcapp.AbsSubActivity;
import com.example.fdcapp.MainActivity;
import com.example.fdcapp.R;
import com.jucai.wuliu.net.MainServerListener;
import com.jucai.wuliu.net.MainServerRequest;

public class LoginActivity extends AbsSubActivity {

	private Button topRightButton;
	private TextView midTextView;
	private EditText phoneEditText, pwdEditText;
	private ImageView leftImageView;
	private TextView rpwd;
	private TextView regist;
	private RelativeLayout llLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		topRightButton = (Button) findViewById(R.id.top_right_button);
		midTextView = (TextView) findViewById(R.id.top_mid_tv);
		leftImageView = (ImageView) findViewById(R.id.left_top_iv);
		midTextView.setText("登陆");
		topRightButton.setText("注册");
		topRightButton.setVisibility(View.VISIBLE);
		topRightButton.setOnClickListener(l);
		llLayout=(RelativeLayout) findViewById(R.id.top_rl);
		llLayout.setVisibility(View.INVISIBLE);
		phoneEditText = (EditText) findViewById(R.id.phone_login_et);
		pwdEditText = (EditText) findViewById(R.id.pwd_login_et);
		rpwd = (TextView) findViewById(R.id.rpwd_login_tv);
		rpwd.setOnClickListener(l);
		regist = (TextView) findViewById(R.id.fname_peral_tv);
		regist.setOnClickListener(l);

	}

	public void submit(View view) {
		submitLogin();
	}

	private void submitLogin() {
		if (phoneEditText.getText().equals("")) {
			showToast("手机不可为空");
			return;
		} else if (pwdEditText.getText().equals("")) {
			showToast("密码不可为空");
			return;
		}
		String UserName = phoneEditText.getText().toString();
		String UserPass = pwdEditText.getText().toString();
		String eType = "1";
		String eCode = "";
		String time = getTime();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("UserName", UserName);
		map.put("UserPass", md5(UserPass));
		map.put("eType", eType);
		map.put("eTime", time);
		map.put("eKey", md5(time + eKey));
		map.put("eCode", eCode);
		MainServerRequest serverRequest = MainServerRequest.getInstance();
		serverRequest.setListener(new MainServerListener() {
			@Override
			public void requestSuccess(JSONObject s) {
				// TODO Auto-generated method stub
				try {
					int errorCode = Integer.parseInt(s.getString("reason"));
					switch (errorCode) {
						case 100:
							JSONObject object = s.getJSONArray("data").getJSONObject(0);
							settings.saveUserPhone(phoneEditText.getText().toString());
							settings.saveOpen_id(object.getString("open_id"));
							settings.saveToken_id(object.getString("token_id"));
							settings.saveIsLogin("1");
							delayFinish();
							break;
						default:
							valErrCode(errorCode);
							break;
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					showToast(s.toString());
				}
			}

			@Override
			public void requestFailure(String s) {
				// TODO Auto-generated method stub
				showToast(s);
			}
		});
		serverRequest.login(map);
	}

	private void delayFinish() {
		new Handler().postDelayed(new Runnable() {
			public void run() {
				Intent intent = new Intent(LoginActivity.this, MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		}, 1000);
	}

	OnClickListener l = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent;
			// TODO Auto-generated method stub
			switch (v.getId()) {
				case R.id.top_right_button:
					intent = new Intent(LoginActivity.this, RegisterActivity.class);
					startActivity(intent);
					break;
				case R.id.rpwd_login_tv:
					intent = new Intent(LoginActivity.this, RetrievepasswordActivity.class);
					startActivity(intent);
					break;
				case R.id.fname_peral_tv:
					intent = new Intent(LoginActivity.this, RegisterActivity.class);
					startActivity(intent);
					break;
				default:
					break;
			}
		}
	};
}