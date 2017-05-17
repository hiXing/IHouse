package com.sky.house.city;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.eroad.base.BaseFragment;
import com.eroad.base.util.CityUtil;
import com.eroad.base.util.ConfigDefinition;
import com.eroad.base.util.ViewInit;
import com.next.intf.ITaskListener;
import com.next.net.SHPostTaskM;
import com.next.net.SHTask;
import com.sky.house.R;
import com.sky.house.adapter.CityAdapter;
import com.sky.widget.SHDialog;
import com.sky.widget.SHEditText;
import com.sky.widget.sweetdialog.SweetDialog;

/**
 * 
 * 
 * @author skypan
 * 
 */
public class HouseCityFragment extends BaseFragment implements ITaskListener {

	private SHPostTaskM taskCity;

	@ViewInit(id = R.id.lv_city)
	private ListView mLvCity;

	@ViewInit(id = R.id.lv_letter)
	private ListView mLvLetter;

	@ViewInit(id = R.id.et_content)
	private SHEditText mEtContent;

	private JSONArray jsonArray;// 原始数组

	private JSONArray newJSONArray;// 格式化之后的数组

	private String[] mLetters;

	private LetterListAdapter mLetterListAdapter;

	private HashMap<String, Integer> mLetterAndIndexMap = new HashMap<String, Integer>();

	private int mChooseIndex = -1;

	private CityAdapter adapter;

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		mDetailTitlebar.setTitle("城市");
		setListeners();
		requestCity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_city, container, false);
		return view;
	}

	private void setListeners() {
		mEtContent.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				String text = mEtContent.getText().toString();
				if (text.length() == 0) {
					adapter.setJsonArray(newJSONArray);
					mLvLetter.setVisibility(View.VISIBLE);
				} else {
					try {
						newJSONArray = CityUtil.getBrandList(jsonArray, text, true);
						adapter.setJsonArray(newJSONArray);
						mLvLetter.setVisibility(View.GONE);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		
		mLvCity.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				try {
					intent.putExtra("cityName", newJSONArray.getJSONObject(arg2).getString("cityName"));
					intent.putExtra("id", newJSONArray.getJSONObject(arg2).getInt("id"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				getActivity().setResult(Activity.RESULT_OK, intent);
				finish();
			}
		});
	}

	private void requestCity() {
		SHDialog.ShowProgressDiaolg(getActivity(), null);
		taskCity = new SHPostTaskM();
		taskCity.setUrl(ConfigDefinition.URL + "getAllCity");
		taskCity.setListener(this);
		taskCity.start();
	}

	private void initData() throws JSONException {

		// jsonA.addAll(BrandUtils.getLocalContactList(ContactActivity.this,
		// false, true));
		newJSONArray = CityUtil.getBrandList(jsonArray, "", false);
		// mLetters = new String[] { "A", "B", "C" };
		mLetters = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

		for (int i = 0; i < newJSONArray.length(); i++) {
			if ("-1".equals(newJSONArray.optJSONObject(i).optString("carcategoryname")))
				mLetterAndIndexMap.put(newJSONArray.optJSONObject(i).optString("sort"), i);
		}

		mLetterListAdapter = new LetterListAdapter(getActivity());
		mLetterListAdapter.setSelect("A");
		mLvLetter.setAdapter(mLetterListAdapter);
		adapter = new CityAdapter(getActivity(), newJSONArray);
		mLvCity.setAdapter(adapter);
		mLvCity.setOnScrollListener(new OnScrollListener() {

			private int mScrollState;

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				mScrollState = scrollState;
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				if (mScrollState == SCROLL_STATE_FLING) {
					String letter = adapter.getFirstPinyin(firstVisibleItem);
					mLetterListAdapter.setSelect(letter);

				} else if (mScrollState == SCROLL_STATE_TOUCH_SCROLL) {
					String letter = adapter.getFirstPinyin(firstVisibleItem);
					mLetterListAdapter.setSelect(letter);
				}
			}
		});

		mLvLetter.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				float y = event.getY();
				int dex = (int) (mLetters.length * y / mLvLetter.getHeight());
				if (dex >= mLetters.length)
					dex = mLetters.length - 1;
				if (dex < 0)
					dex = 0;
				String letter = mLetters[dex];
				int index = -1;
				if (mLetterAndIndexMap.containsKey(letter))
					index = mLetterAndIndexMap.get(letter);
				int oldChooseIndex = mChooseIndex;

				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					v.setBackgroundColor(Color.LTGRAY);
					mLetterListAdapter.setSelect(letter);
					if (index != -1 && index != oldChooseIndex) {
						mLvCity.setSelection(index);
						mChooseIndex = index;
					}

					break;
				case MotionEvent.ACTION_MOVE:
					mLetterListAdapter.setSelect(letter);
					if (index != -1 && index != oldChooseIndex) {
						mLvCity.setSelection(index);
						mChooseIndex = index;
					}
					break;
				case MotionEvent.ACTION_UP:
					v.setBackgroundColor(Color.TRANSPARENT);
					break;
				}

				return false;
			}
		});
	}

	private class LetterListAdapter extends ArrayAdapter<String> {

		private String mSelected;

		public LetterListAdapter(Context context) {
			super(context, 0, mLetters);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			TextView letterView;
			if (convertView == null) {
				letterView = new TextView(getActivity());
				letterView.setLayoutParams(new ListView.LayoutParams(LayoutParams.MATCH_PARENT, mLvCity.getMeasuredHeight() / 26));
				// letterView.setTextSize(mLvCity.getMeasuredHeight() / 26 *
				// 0.6f);
				letterView.setGravity(Gravity.CENTER);
				letterView.setTextColor(getResources().getColor(R.color.color_gray_dark));
				convertView = letterView;
			} else {
				letterView = (TextView) convertView;
			}

			letterView.setText(getItem(position));

			if (getItem(position).equalsIgnoreCase(mSelected))
				letterView.setTextColor(0xff00cc00);
			else
				letterView.setTextColor(Color.GRAY);

			return letterView;
		}

		public void setSelect(String letter) {
			// if (letter == null || letter.matches("\\d*"))
			// mSelected = "#";
			// else
			mSelected = letter;

			notifyDataSetChanged();
		}

		public String getSelect() {
			return mSelected;
		}

	}

	@Override
	public void onTaskFinished(SHTask task) throws Exception {
		// TODO Auto-generated method stub
		SHDialog.dismissProgressDiaolg();
		JSONObject json = (JSONObject) task.getResult();
		jsonArray = json.getJSONArray("cities");
		initData();
	}

	@Override
	public void onTaskFailed(SHTask task) {
		// TODO  Auto-generated method stub
		SHDialog.dismissProgressDiaolg();
		new SweetDialog(getActivity(), SweetDialog.ERROR_TYPE).setTitleText("提示").setContentText(task.getRespInfo().getMessage()).show();
	}

	@Override
	public void onTaskUpdateProgress(SHTask task, int count, int total) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTaskTry(SHTask task) {
		// TODO Auto-generated method stub

	}
}
