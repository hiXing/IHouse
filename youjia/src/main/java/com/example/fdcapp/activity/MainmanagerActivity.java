package com.example.fdcapp.activity;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.ab.bitmap.AbImageDownloader;
import com.ab.global.AbConstant;
import com.example.fdcapp.AbsSubActivity;
import com.example.fdcapp.R;
import com.example.fdcapp.obj.UserObject;
import com.jucai.wuliu.net.MainServerListener;
import com.jucai.wuliu.net.MainServerRequest;

public class MainmanagerActivity extends AbsSubActivity {
	ImageView uhead_iv;
	TextView uName_tv, fName_tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_manager);
		findID();
		getUserInfos();

	}

	private void findID() {
		uhead_iv = (ImageView) findViewById(R.id.head_mmain_iv);
		uName_tv = (TextView) findViewById(R.id.name_mmain_tv);
		fName_tv = (TextView) findViewById(R.id.fname_mmain_tv);
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

	private void setValue() {
		UserObject userObject = settings.getUserObject();
		setHead();
		uName_tv.setText(userObject.getNick());
		fName_tv.setText(userObject.geteCode());
	}

	private void setHead() {
		AbImageDownloader imageDownloader;
		imageDownloader = new AbImageDownloader(this);
		imageDownloader.setWidth(200);
		imageDownloader.setHeight(200);
		imageDownloader.setType(AbConstant.SCALEIMG);
		String imageUrl = settings.getUserObject().getUserImg();
		imageDownloader.display(uhead_iv, imageUrl);
	}
}
