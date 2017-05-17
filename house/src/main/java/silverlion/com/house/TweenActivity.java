package silverlion.com.house;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.OnClick;
import silverlion.com.house.R;
import silverlion.com.house.commous.ActivityUtils;
import silverlion.com.house.login.LoginActivity;
import silverlion.com.house.register.RegisterActivity;

public class TweenActivity extends AppCompatActivity{
	private ActivityUtils activityUtils;
	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activityUtils = new ActivityUtils(this);
		setContentView(R.layout.activity_welcome);
		ButterKnife.bind(this);
	}
	@OnClick({R.id.login_btn, R.id.register_btn})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.login_btn:
				activityUtils.startActivity(LoginActivity.class);
				finish();
//				c = Calendar.getInstance();
//				int hour = c.get(Calendar.HOUR_OF_DAY);
//				int minute = c.get(Calendar.MINUTE);
//				new TimePickerDialog(TweenActivity.this,new TimePickerDialog.OnTimeSetListener() {
//					@Override
//					public void onTimeSet(TimePicker view,int hourOfDay,int minute){
//						Log.d("onTimeSet", "hourOfDay: " + hourOfDay + "Minute: " + minute);
//					}
//				}, hour, minute, true).show();
				break;
			case R.id.register_btn:
				activityUtils.startActivity(RegisterActivity.class);
				break;
		}
	}


}
