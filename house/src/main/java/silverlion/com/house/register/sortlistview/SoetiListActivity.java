package silverlion.com.house.register.sortlistview;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import silverlion.com.house.R;
import silverlion.com.house.commous.ActivityUtils;

public class SoetiListActivity extends AppCompatActivity {
	private final int RESULT = 0x12;
	private ListView sortListView;
	private SideBar sideBar;
	private TextView dialog;
	private SortAdapter adapter;
	private ClearEditText mClearEditText;
	private CharacterParser characterParser;
	private List<SortModel> SourceDateList;
	private PinyinComparator pinyinComparator;
	private final String AREA_CODE = "http://casadiario.com/OpenDC/index.php/App/Account/area_code";
	private List<SortModel> list = new ArrayList<SortModel>();
	private final char[]  sequence = new char[]{'A','B','C','D','E','F','G','H','I','J','K','L'
			,'M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
	private Intent intent;
	private ActivityUtils activityUtils;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activityUtils = new ActivityUtils(this);
		setContentView(R.layout.activity_soeilist);
		intent = getIntent();
		net();
		initViews();
	}
	private void net(){
		if (!list.isEmpty())list.clear();
		HttpUtils utils = new HttpUtils();
		utils.send(HttpMethod.POST, AREA_CODE, new RequestCallBack<String>() {
			@Override
			public void onFailure(HttpException error, String msg) {
				activityUtils.showToast("链接失败，请检查网络连接");
			}
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				try {
					JSONObject object = new JSONObject(responseInfo.result);
					Log.i("result",responseInfo.result);
					for (int j = 0;j < sequence.length;j++){
						JSONArray jsonA = object.getJSONArray(""+sequence[j]);
						for (int i = 0;i < jsonA.length();i++){
							JSONObject objectA = jsonA.getJSONObject(i);
							String name =objectA.getString("name");
							String code =objectA.getString("code");
							String stand = objectA.getString("stand");
							SortModel model = new SortModel(name,stand,code);
							list.add(model);//添加到集合里
						}
					}
					} catch (JSONException e) {
						activityUtils.showToast(e.toString());
					}
			}
		});
	}

	private void initViews() {
		characterParser = CharacterParser.getInstance();
		pinyinComparator = new PinyinComparator();
		sideBar = (SideBar) findViewById(R.id.sidrbar);
		dialog = (TextView) findViewById(R.id.dialog);
		sideBar.setTextView(dialog);

		sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

			@Override
			public void onTouchingLetterChanged(String s) {
				//����ĸ�״γ��ֵ�λ��
				int position = adapter.getPositionForSection(s.charAt(0));
				if(position != -1){
					sortListView.setSelection(position);
				}
			}
		});
		sortListView = (ListView) findViewById(R.id.country_lvcountry);
		//listview的点击事件
		sortListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				activityUtils.showToast(((SortModel) adapter.getItem(position)).getName());
				intent.putExtra("name", ((SortModel) adapter.getItem(position)).getName());
				intent.putExtra("area",(((SortModel) adapter.getItem(position)).getArea_code()));
				setResult(RESULT, intent);
				finish();
			}
		});
		//添加数据源
//		filledData(getResources().getStringArray(R.array.date))
		SourceDateList = list;
		// ����a-z��������Դ����
		Collections.sort(SourceDateList, pinyinComparator);
		//适配数据
		adapter = new SortAdapter(this, SourceDateList);
		sortListView.setAdapter(adapter);
		mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);

		mClearEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				//������������ֵΪ�գ�����Ϊԭ�����б�����Ϊ���������б�
				filterData(s.toString());
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}
	/**
	 * ΪListView�������
	 * @param date
	 * @return
	 */
	//处理数据
	private List<SortModel> filledData(String [] date){
		List<SortModel> mSortList = new ArrayList<SortModel>();
		//获取数据源，将第一个字的首字母提出来，然后赋值给sortLetters。
		for(int i=0; i<date.length; i++){
			SortModel sortModel = new SortModel();
			sortModel.setName(date[i]);
			//����ת����ƴ��
			String pinyin = characterParser.getSelling(date[i]);
			String sortString = pinyin.substring(0, 1).toUpperCase();
			// ������ʽ���ж�����ĸ�Ƿ���Ӣ����ĸ
			if(sortString.matches("[A-Z]")){
				sortModel.setSortLetters(sortString.toUpperCase());
			}else{
				sortModel.setSortLetters("#");
			}
			mSortList.add(sortModel);
		}
		return mSortList;
	}

	/**
	 * ����������е�ֵ���������ݲ�����ListView
	 * @param filterStr
	 */
	private void filterData(String filterStr){
		List<SortModel> filterDateList = new ArrayList<SortModel>();

		if(TextUtils.isEmpty(filterStr)){
			filterDateList = SourceDateList;
		}else{
			filterDateList.clear();
			for(SortModel sortModel : SourceDateList){
				String name = sortModel.getName();
				if(name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())){
					filterDateList.add(sortModel);
				}
			}
		}
		// ����a-z��������
		Collections.sort(filterDateList, pinyinComparator);
		adapter.updateListView(filterDateList);
	}

}
