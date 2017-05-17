package com.example.fdcapp;

import android.content.Intent;
import android.os.Bundle;

import com.example.fdcapp.activity.LoginActivity;
import com.example.fdcapp.activity.MainadviserActivity;
import com.example.fdcapp.activity.MainmanagerActivity;
import com.example.fdcapp.activity.MainsalesmanActivity;

public class MainActivity extends AbsSubActivity {
	int userRole = 0;// Integer.parseInt(initUserInfo().get("userRole"));
	int intLayoutMain;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		if (settings.getIsLogin().equals("0")) {
			Intent intent = new Intent(MainActivity.this, LoginActivity.class);
			startActivity(intent);
			finish();
			return;
		}
		if (userRole == 0) {
			Intent intent = new Intent(this, MainsalesmanActivity.class);
			startActivity(intent);
		} else if (userRole == 1) {
			Intent intent = new Intent(this, MainadviserActivity.class);
			startActivity(intent);
		} else {
			Intent intent = new Intent(this, MainmanagerActivity.class);
			startActivity(intent);
		}
	}
}
