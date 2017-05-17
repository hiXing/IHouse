package com.example.fdcapp.activity;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fdcapp.AbsSubActivity;
import com.example.fdcapp.R;
import com.example.fdcapp.adapter.CusRportAdapter;
import com.example.fdcapp.obj.HousesObject;
import com.jucai.wuliu.net.MainServerListener;
import com.jucai.wuliu.net.MainServerRequest;

public class CusReportActivity extends AbsSubActivity {

	private Button topRightButton;
	private TextView midTextView;
	private ImageView leftImageView;
	private ListView rList_lv;
	private ArrayList<HousesObject> mList;
	private int page;
	private CusRportAdapter adapter;
	private EditText search_et;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cus_report);
		topRightButton = (Button) findViewById(R.id.top_right_button);
		midTextView = (TextView) findViewById(R.id.top_mid_tv);
		leftImageView = (ImageView) findViewById(R.id.left_top_iv);
		midTextView.setText("客户报备");
		topRightButton.setVisibility(View.INVISIBLE);
		mList = new ArrayList<HousesObject>();
		iniList(1, true);
		findID();
		rList_lv = (ListView) findViewById(R.id.cusrep_lv);
		adapter = new CusRportAdapter();
		rList_lv.setAdapter(adapter);
	}

	private void setValue() {

	}

	private void findID() {
		rList_lv = (ListView) findViewById(R.id.cusrep_lv);
		search_et = (EditText) findViewById(R.id.search_cusrep_et);
	}

	private void iniList(int pgae, boolean iFlag) {
		if (iFlag) {
			mList.clear();
		}
		MainServerRequest request = MainServerRequest.getInstance();
		request.setListener(new MainServerListener() {

			@Override
			public void requestSuccess(JSONObject s) {
				// TODO Auto-generated method stub
				try {
					int err = Integer.parseInt(s.getString("reason"));
					if (err == 100) {
						HousesObject object;
						JSONArray array = s.getJSONArray("data");
						for (int i = 0; i < array.length(); i++) {
							object = new HousesObject();
							object.parseFromJson(array.getJSONObject(i));
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
		String keyword = "";
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("open_id", settings.getOpen_id());
		map.put("token_id", settings.getToken_id());
		map.put("eTime", getTime());
		map.put("eCode", eCode);
		map.put("page", page + "");
		map.put("keyword", keyword);
		request.project(map);
	}
}