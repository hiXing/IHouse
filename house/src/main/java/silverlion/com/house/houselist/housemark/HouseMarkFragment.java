package silverlion.com.house.houselist.housemark;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.hannesdorfmann.mosby.mvp.MvpFragment;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.views.DatePicker;
import retrofit2.Call;
import silverlion.com.house.R;
import silverlion.com.house.commous.ActivityUtils;
import silverlion.com.house.components.ProgressDialogFragment;
import silverlion.com.house.houselist.HouseActivity;
import silverlion.com.house.houselist.housedetails.HouseDetailsFragment;
import silverlion.com.house.register.User;
import silverlion.com.house.register.sortlistview.SoetiListActivity;


public class HouseMarkFragment extends MvpFragment<HouseMarkView,HouseMarkPersenter> implements HouseMarkView {
    private ActivityUtils activityUtils;
    private ProgressDialogFragment progressDialogFragment;
    private Calendar c;
    private final int REQUEST = 0x12;
    private String area = "0086";
    private String time1,time2,address,name,phone,email;
    private String list_id = HouseActivity.list_id;//获取房屋ID
    private String accout_id = HouseActivity.accout_id;//获取用户ID
    private String promulgator_id;//发布者的ID
    @Bind(R.id.data)TextView data;
    @Bind(R.id.time)TextView time;
    @Bind(R.id.calendarView)DatePicker datePicker;//日历
    @Bind(R.id.time1)TextView time1_et;
    @Bind(R.id.time2)TextView time2_et;
    @Bind(R.id.address)EditText address_et;
    @Bind(R.id.name)EditText name_et;
    @Bind(R.id.area)TextView area_tv;
    @Bind(R.id.phone)EditText phonr_et;
    @Bind(R.id.email)EditText email_et;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_house_mark,null);
        ButterKnife.bind(this,view);
//        time1_et.addTextChangedListener(mTextWatcher);
//        time2_et.addTextChangedListener(mTextWatcher);
        address_et.addTextChangedListener(mTextWatcher);
        name_et.addTextChangedListener(mTextWatcher);
        phonr_et.addTextChangedListener(mTextWatcher);
        email_et.addTextChangedListener(mTextWatcher);
        c = Calendar.getInstance();//获取当前时间
        datePicker.setDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1);
        datePicker.setMode(DPMode.SINGLE);
        //日历点击
        datePicker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
            @Override
            public void onDatePicked(String date) {
                activityUtils.showToast(date);
                presenter.Busy(new User(promulgator_id,date));
            }
        });
        return view;
    }

    /**
     * 获取发布者ID
     *
     * */
    @Override
    public void onResume() {
        super.onResume();
        promulgator_id = HouseDetailsFragment.promulgator_id;
    }

    @OnClick({R.id.area_liner,R.id.time1,R.id.time2})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.area_liner:
                Intent intent = new Intent(getActivity(), SoetiListActivity.class);
                startActivityForResult(intent,REQUEST);
                break;
            case R.id.time1:
				int hour = c.get(Calendar.HOUR_OF_DAY);
				int minute = c.get(Calendar.MINUTE);
                //时间点击
				new TimePickerDialog(getActivity(),new TimePickerDialog.OnTimeSetListener() {
					@Override
					public void onTimeSet(TimePicker view,int hourOfDay,int minute){
                        activityUtils.hideSoftKeyboard();
                        String day,min;
                        if (hourOfDay < 10){
                            day = "0"+hourOfDay;
                        }else {
                            day = ""+hourOfDay;
                        }
                        if (minute < 10){
                            min = "0"+minute;
                        }else {
                            min = ""+minute;
                        }
						Log.d("onTimeSet", "hourOfDay: " + hourOfDay + "Minute: " + minute);
                        time1_et.setText(day + ":" + min);
                        time1 = day+":"+min;//获取时间
					}
				}, hour, minute, true).show();
                break;
            case R.id.time2:
                int hours = c.get(Calendar.HOUR_OF_DAY);
                int minutes = c.get(Calendar.MINUTE);
                //时间点击
                new TimePickerDialog(getActivity(),new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view,int hourOfDay,int minute){
                        activityUtils.hideSoftKeyboard();
                        String day,min;
                        if (hourOfDay < 10){
                            day = "0"+hourOfDay;
                        }else {
                            day = ""+hourOfDay;
                        }
                        if (minute < 10){
                            min = "0"+minute;
                        }else {
                            min = ""+minute;
                        }
                        Log.d("onTimeSet", "hourOfDay: " + hourOfDay + "Minute: " + minute);
                        time2_et.setText(day + ":" + min);
                        time2 = day+":"+min;//获取时间
                    }
                }, hours, minutes, true).show();
                break;
        }
    }

    private final TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override//改变后
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == resultCode){
            area_tv.setText("+ "+data.getStringExtra("area"));
            area = data.getStringExtra("area");
        }
    }

    @Override
    public HouseMarkPersenter createPresenter() {
        return new HouseMarkPersenter();
    }

    @Override
    public void showProgress() {
        activityUtils.hideSoftKeyboard();
        if (progressDialogFragment == null) {
            progressDialogFragment = new ProgressDialogFragment();
        }
        if (progressDialogFragment.isVisible()) return;
        progressDialogFragment.show(getFragmentManager(), "fragment_progress_dialog");
    }

    @Override
    public void hideProgress() {
        progressDialogFragment.dismiss();
    }

    @Override
    public void showMessage(String msg) {
        activityUtils.showToast(msg);
    }

    @Override
    public void updateUI() {

    }
}
