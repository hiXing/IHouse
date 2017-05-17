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
import com.example.fdcapp.R.id;
import com.example.fdcapp.R.layout;
import com.jucai.wuliu.net.MainServerListener;
import com.jucai.wuliu.net.MainServerRequest;

public class RetrievepasswordActivity extends AbsSubActivity {
	String SerialNum = "";
	EditText et1, et2, et3, et4, et5, et6, et7, et8;
	TextView telCode_tv;
	Button subButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(layout.activity_retrievepassword);
		et1 = (EditText) findViewById(id.name_rpwd_et);
		et2 = (EditText) findViewById(id.phone_rpwd_et);
		et3 = (EditText) findViewById(id.comcode_rpwd_et);
		et4 = (EditText) findViewById(id.valcode_rpwd_et);
		telCode_tv = (TextView) findViewById(id.telcode_rpwd_tv);
		telCode_tv.setOnClickListener(l);
		subButton = (Button) findViewById(id.submit_rpwd_bt);
		subButton.setOnClickListener(l);
	}

	OnClickListener l = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case id.telcode_rpwd_tv:
				getCode();
				break;
			case id.submit_rpwd_bt:
				submitRetrievepassword();
				break;
			default:
				break;
			}
		}
	};

	private void submitRetrievepassword() {
		String Nick = et1.getText().toString();
		String UserTel = et2.getText().toString();
		String CompanyCode = et3.getText().toString();
		String TelCode = et4.getText().toString();
		String eType = "1";
		String eTime = getTime();
		SerialNum = "26";
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("eName", Nick);
		map.put("Tel", UserTel);
		map.put("eTime", eTime);
		map.put("eCode", eCode);
		map.put("eKey", md5(eTime + eKey));
		map.put("TelCode", TelCode);
		map.put("SerialNum", SerialNum);
		MainServerRequest serverRequest = MainServerRequest.getInstance();
		serverRequest.setListener(new MainServerListener() {
			@Override
			public void requestSuccess(JSONObject s) {
				// TODO Auto-generated method stub
				try {
					int errorCode = Integer.parseInt(s.getString("reason"));
					switch (errorCode) {
					case 100:
						showToast("成功找回，请注意短信");
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
		serverRequest.RetrievePassword(map);
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

	private void delayFinish() {
		new Handler().postDelayed(new Runnable() {
			public void run() {
				RetrievepasswordActivity.this.finish();
			}
		}, 1000);
	}
}
