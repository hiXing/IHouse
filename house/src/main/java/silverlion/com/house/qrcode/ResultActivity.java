package silverlion.com.house.qrcode;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.Toast;
import android.content.Intent;

import silverlion.com.house.R;

public class ResultActivity extends AppCompatActivity {
	private Bundle bundle;
	private WebView web;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		initdata();
	}

	@Override
	public void onContentChanged() {
		super.onContentChanged();
	}

	private void initdata() {
		Intent intentvalue = getIntent();
		bundle = intentvalue.getExtras();
		String str = bundle.getString("result");
		String substr = str.substring(0, 4);
		if(substr.equals("http")){
			web = new WebView(ResultActivity.this);
			web.loadUrl(str);
			setContentView(web);
			Toast.makeText(ResultActivity.this, "success!",Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(ResultActivity.this, "失败",Toast.LENGTH_SHORT).show();
		}

	}


//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if((keyCode == KeyEvent.KEYCODE_BACK) && web.canGoBack()) {
//			web.goBack();
//			return true;
//		}
//		return false;
//		
//	}
	

}
