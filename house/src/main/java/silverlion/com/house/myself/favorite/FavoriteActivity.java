package silverlion.com.house.myself.favorite;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.hannesdorfmann.mosby.mvp.MvpActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import silverlion.com.house.R;

public class FavoriteActivity extends MvpActivity<FavoriteView,FavoritePersenter> implements FavoriteView {
    @Bind(R.id.select)EditText select_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        Resources res = this.getResources();
        Drawable drawable = res.getDrawable(R.drawable.search_btn);
        drawable.setBounds(0,0,50,50);
        select_tv.setCompoundDrawables(drawable,null,null,null);
    }

    @NonNull
    @Override
    public FavoritePersenter createPresenter() {
        return new FavoritePersenter();
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
    }
}
