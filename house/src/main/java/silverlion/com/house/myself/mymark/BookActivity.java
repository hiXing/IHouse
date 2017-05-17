package silverlion.com.house.myself.mymark;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hannesdorfmann.mosby.mvp.MvpActivity;

import silverlion.com.house.R;

public class BookActivity extends MvpActivity<BookView,BookPersenter> implements BookView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
    }

    @NonNull
    @Override
    public BookPersenter createPresenter() {
        return new BookPersenter();
    }
}
