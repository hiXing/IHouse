package com.example.fdcapp.activity;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.example.fdcapp.AbsSubActivity;
import com.example.fdcapp.R;
import com.jucai.wuliu.net.MainServerListener;
import com.jucai.wuliu.net.MainServerRequest;

public class RegisterActivity extends AbsSubActivity {

	String SerialNum = "";
	EditText et1, et2, et3, et4, et5, et6, et7, et8;
	TextView telCode_tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		et1 = (EditText) findViewById(R.id.name_reg_et);
		et2 = (EditText) findViewById(R.id.phone_reg_et);
		et3 = (EditText) findViewById(R.id.comcode_reg_et);
		et4 = (EditText) findViewById(R.id.valcode_reg_et);
		telCode_tv = (TextView) findViewById(R.id.telcode_reg_tv);
		telCode_tv.setOnClickListener(l);

	}

	OnClickListener l = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			getCode();
		}
	};

	private void submitRegister() {
		String Nick = et1.getText().toString();
		String UserTel = et2.getText().toString();
		String CompanyCode = et3.getText().toString();
		String TelCode = et4.getText().toString();
		String eType = "1";
		String eCode = "";
		String eTime = getTime();
		// SerialNum = "14";
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("Nick", Nick);
		map.put("UserTel", UserTel);
		map.put("CompanyCode", CompanyCode);
		map.put("TelCode", TelCode);
		map.put("SerialNum", SerialNum);
		map.put("eType", eType);
		map.put("eCode", eCode);
		map.put("eTime", eTime + "");
		map.put("eKey", md5(eTime + eKey));
		MainServerRequest serverRequest = MainServerRequest.getInstance();
		serverRequest.setListener(new MainServerListener() {
			@Override
			public void requestSuccess(JSONObject s) {
				// TODO Auto-generated method stub
				try {
					int errorCode = Integer.parseInt(s.getString("reason"));
					switch (errorCode) {
						case 100:
							showToast("注册成功,请查看短信");
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
		serverRequest.reg(map);
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
		serverRequest.sms(et2.getText().toString(), getTime(), md5(getTime() + eKey));
	}

	public void submit(View view) {
		submitRegister();
	}

	private void delayFinish() {
		new Handler().postDelayed(new Runnable() {
			public void run() {
				RegisterActivity.this.finish();
			}
		}, 1000);
	}
}
