package com.example.fdcapp.activity;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ab.bitmap.AbImageDownloader;
import com.ab.global.AbConstant;
import com.example.fdcapp.AbsSubActivity;
import com.example.fdcapp.R;
import com.example.fdcapp.obj.UserObject;
import com.jucai.wuliu.net.MainServerListener;
import com.jucai.wuliu.net.MainServerRequest;

public class MainsalesmanActivity extends AbsSubActivity {

	ImageView uhead_iv;
	TextView uName_tv, fName_tv, vol_tv, report_tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_salesman);
		findID();
		getUserInfos();
	}

	private void findID() {
		uhead_iv = (ImageView) findViewById(R.id.head_speral_iv);
		uName_tv = (TextView) findViewById(R.id.name_speral_tv);
		fName_tv = (TextView) findViewById(R.id.fname_speral_tv);
		report_tv = (TextView) findViewById(R.id.report_smain_tv);
		vol_tv = (TextView) findViewById(R.id.vol_smain_tv);
	}

	private void setValue() {
		UserObject userObject = settings.getUserObject();
		setHead();
		uName_tv.setText(userObject.getNick());
		fName_tv.setText(userObject.getCompany());
		report_tv.setText("报备 " + userObject.geteNum() + " 人");
		vol_tv.setText("成交 " + userObject.getoNum() + " 人");
	}

	private void setHead() {
		AbImageDownloader imageDownloader;
		imageDownloader = new AbImageDownloader(this);
		imageDownloader.setWidth(200);
		imageDownloader.setHeight(200);
		imageDownloader.setType(AbConstant.SCALEIMG);
		String imageUrl = serverUrl + settings.getUserObject().getUserImg();
		imageDownloader.display(uhead_iv, imageUrl);
	}

	private void getUserInfos() {
		MainServerRequest serverRequest = MainServerRequest.getInstance();
		serverRequest.setListener(new MainServerListener() {
			@Override
			public void requestSuccess(JSONObject s) {
				// TODO Auto-generated method stub
				try {
					int errorCode = Integer.parseInt(s.getString("reason"));
					switch (errorCode) {
						case 100:
							settings.saveUser(s.getJSONArray("data").getJSONObject(0));
							setValue();
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
		HashMap<String, String> map = new HashMap<String, String>();
		String open_id = settings.getOpen_id();
		String token_id = settings.getToken_id();
		String eCode = null;
		map.put("open_id", open_id);
		map.put("token_id", token_id);
		map.put("eTime", getTime());
		map.put("eCode", eCode);
		serverRequest.userInfo(map);
	}

	public void jumpCusrep(View view) {
		Intent intent = new Intent(MainsalesmanActivity.this, CusReportActivity.class);
		startActivity(intent);
	}

	public void jumpCers(View view) {
		Intent intent = new Intent(MainsalesmanActivity.this, CersActivity.class);
		startActivity(intent);
	}

	public void jumpUpPwd(View view) {
		Intent intent = new Intent(MainsalesmanActivity.this, UpdatepwdActivity.class);
		startActivity(intent);
	}

	public void out(View view) {
		// settings.saveIsLogin("0");
		MainServerRequest request = MainServerRequest.getInstance();
		request.setListener(new MainServerListener() {

			@Override
			public void requestSuccess(JSONObject s) {
				// TODO Auto-generated method stub
				try {
					int err = Integer.parseInt(s.getString("reason"));
					if (err == 100) {
						showToast("成功注销");
						settings.saveIsLogin("0");
						delayFinishab();
					} else {
						valErrCode(err);
					}
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
		request.outlogin(map);
	}

	private void delayFinishab() {
		new Handler().postDelayed(new Runnable() {
			public void run() {
				Intent intent = new Intent(MainsalesmanActivity.this, LoginActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		}, 1000);
	}
}
