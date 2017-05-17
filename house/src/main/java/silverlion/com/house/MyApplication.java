package silverlion.com.house;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import silverlion.com.house.commous.UserPrefs;

/**
 * Created by k8190 on 2016/7/26.
 */
public class MyApplication extends Application {

    @Override public void onCreate() {
        super.onCreate();
        ImageLoader.getInstance().init(new ImageLoaderConfiguration.Builder(this).writeDebugLogs().build());
        UserPrefs.init(this);
    }
}
