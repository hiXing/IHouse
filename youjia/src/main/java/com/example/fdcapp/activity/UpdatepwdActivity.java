package com.example.fdcapp.activity;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.fdcapp.AbsSubActivity;
import com.example.fdcapp.R;
import com.example.fdcapp.R.id;
import com.example.fdcapp.R.layout;
import com.jucai.wuliu.net.MainServerListener;
import com.jucai.wuliu.net.MainServerRequest;

public class UpdatepwdActivity extends AbsSubActivity {
	private Button subButton;
	private EditText name_et, oldpwd_et, newpwd_et, new2pwd_et, telcode_et;
	private TextView getcode_et;
	private String SerialNum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_updatepassword);
		findID();
		setValue();
	}

	private void submitUpatePwd() {
		String tMsg = "请补全信息";
		if (name_et.getText().equals("")) {
			showToast("tMsg");
			return;
		}
		if (oldpwd_et.getText().equals("")) {
			showToast("tMsg");
			return;
		}
		if (newpwd_et.getText().equals("")) {
			showToast("tMsg");
			return;
		}
		if (new2pwd_et.getText().equals("")) {
			showToast("tMsg");
			return;
		}
		if (telcode_et.getText().equals("")) {
			showToast("tMsg");
			return;
		}
		if (!newpwd_et.getText().toString().equals(new2pwd_et.getText().toString())) {
			showToast("两次输入的新密码不同");
			return;
		}
		String oldString = oldpwd_et.getText().toString();
		String newString = newpwd_et.getText().toString();
		String telCodeString = telcode_et.getText().toString();
		SerialNum = "14";
		MainServerRequest request = MainServerRequest.getInstance();
		request.setListener(new MainServerListener() {

			@Override
			public void requestSuccess(JSONObject s) {
				// TODO Auto-generated method stub
				try {
					int err = Integer.parseInt(s.getString("reason"));
					if (err == 100) {
						showToast("修改成功");
						delayFinish();
					} else
						valErrCode(err);

				} catch (Exception e) {
					// TODO: handle exception
				}
			}

			@Override
			public void requestFailure(String s) {
				// TODO Auto-generated method stub

			}
		});
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("open_id", settings.getOpen_id());
		map.put("token_id", settings.getToken_id());
		map.put("eTime", getTime());
		map.put("eCode", eCode);
		map.put("OldPass", md5(oldString));
		map.put("NewPass", md5(newString));
		map.put("TelCode", telCodeString);
		map.put("SerialNum", SerialNum);
		request.EditPass(map);
	}

	private void findID() {
		subButton = (Button) findViewById(R.id.submit_uppwd_bt);
		name_et = (EditText) findViewById(R.id.name_upwd_et);
		oldpwd_et = (EditText) findViewById(R.id.opwd_uppwd_et);
		newpwd_et = (EditText) findViewById(R.id.npwd_uppwd_et);
		new2pwd_et = (EditText) findViewById(R.id.npwd2_uppwd_et);
		telcode_et = (EditText) findViewById(R.id.valcode_uppwd_et);
		getcode_et = (TextView) findViewById(R.id.getcode_uppwd_tv);
	}

	private void getCode() {
		MainServerRequest serverRequest = MainServerRequest.getInstance();
		serverRequest.setListener(new MainServerListener() {
			@Override
			public void requestSuccess(JSONObject s) {
				// TODO Auto-generated method stub
				try {
					int errorCode = Integer.parseInt(s.getString("reason"));
					if (errorCode == 100) {
						SerialNum = s.getJSONArray("data").getJSONObject(0).getString("SerialNum");
						showToast("验证码已发送");
					} else
						valErrCode(errorCode);
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
		String time = getTime();
		serverRequest.sms(settings.getUserObject().getTel(), time, md5(time + eKey));
	}

	private void delayFinish() {
		new Handler().postDelayed(new Runnable() {
			public void run() {
				UpdatepwdActivity.this.finish();
			}
		}, 1000);
	}

	private void setValue() {

		subButton.setOnClickListener(l);
		getcode_et.setOnClickListener(l);
		name_et.setText(settings.getUserObject().getNick());
	}

	OnClickListener l = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
				case R.id.submit_uppwd_bt:
					submitUpatePwd();
					break;
				case R.id.getcode_uppwd_tv:
					getCode();
					break;
				default:
					break;
			}
		}
	};
}