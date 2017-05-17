package com.example.fdcapp.activity;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fdcapp.AbsSubActivity;
import com.example.fdcapp.R;
import com.example.fdcapp.adapter.CersAdapter;
import com.example.fdcapp.obj.CusRportObject;
import com.jucai.wuliu.net.MainServerListener;
import com.jucai.wuliu.net.MainServerRequest;

public class CersActivity extends AbsSubActivity {

	ListView listView;
	private Button topRightButton;
	private TextView midTextView;
	private ImageView leftImageView;
	private ArrayList<CusRportObject> mList;
	private CersAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cers);
		topRightButton = (Button) findViewById(R.id.top_right_button);
		midTextView = (TextView) findViewById(R.id.top_mid_tv);
		leftImageView = (ImageView) findViewById(R.id.left_top_iv);
		midTextView.setText("我的客户");
		listView = (ListView) findViewById(R.id.cers_lv);
		mList = new ArrayList<CusRportObject>();
		adapter = new CersAdapter(this, mList);
		getCers(1, true);
		listView.setAdapter(adapter);
	}

	private void getCers(int page, boolean isIni) {
		if (isIni) {
			mList.clear();
			page = 1;
		}
		MainServerRequest request = MainServerRequest.getInstance();
		request.setListener(new MainServerListener() {
			@Override
			public void requestSuccess(JSONObject s) {
				// TODO Auto-generated method stub
				int err;

				try {
					err = Integer.parseInt(s.getString("reason"));
					if (err == 100) {
						JSONArray array = s.getJSONArray("data");
						CusRportObject object = new CusRportObject();
						for (int i = 0; i < array.length(); i++) {
							object.parseFromJson((JSONObject) array.get(i));
							mList.add(object);
						}
						adapter.notifyDataSetChanged();
					} else
						valErrCode(err);
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
		map.put("eCode", "");
		map.put("page", page + "");
		map.put("keyword", "");
		map.put("eType", "");
		request.customer(map);
	}
}