package com.example.fdcapp.activity;

import com.example.fdcapp.R;
import com.example.fdcapp.R.id;
import com.example.fdcapp.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class WithdrawcashActivity extends Activity {

	private Button topRightButton;
	private TextView midTextView;
	private ImageView leftImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(layout.activity_withdrawcash);
		topRightButton = (Button) findViewById(id.top_right_button);
		midTextView = (TextView) findViewById(id.top_mid_tv);
		leftImageView = (ImageView) findViewById(id.left_top_iv);
		midTextView.setText("������ϸ");
		topRightButton.setVisibility(View.INVISIBLE);
	}
}
