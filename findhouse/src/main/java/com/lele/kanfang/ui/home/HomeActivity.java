package com.lele.kanfang.ui.home;

import android.app.SearchManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.PointerIcon;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.lele.kanfang.R;
import com.lele.kanfang.base.BaseActivity;
import com.lele.kanfang.util.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity {

    @BindView(R.id.bottom_bar)
    BottomNavigationBar bottomBar;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.slider)
    SliderLayout sliderLayout;

    @BindView(R.id.recycleView1)
    RecyclerView recycleView1;

    @BindView(R.id.recycleView2)
    RecyclerView recycleView2;

    @BindArray(R.array.bottom_bar_array)
    String[] bottom_bar_name_array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        initToolbar();

        initBanner();

        initRecycleView1();

        initRecycleView2();

        pluginBottomBar();
    }

    private void initRecycleView2() {
        recycleView2.setLayoutManager(new LinearLayoutManager(this));
        recycleView2.setAdapter(new FirstHomeAdapter2(this,new ArrayList<KanFangShi>()));
    }

    private void initRecycleView1() {
        recycleView1.setLayoutManager(new LinearLayoutManager(this));
        recycleView1.setAdapter(new FirstHomeAdapter(this,new ArrayList<KanFangShi>()));

    }

    @Override
    protected void onStart() {
        sliderLayout.startAutoCycle();
        super.onStart();
    }

    @Override
    protected void onStop() {
        sliderLayout.stopAutoCycle();
        super.onStop();
    }

    private void initBanner() {

        HashMap<String,Integer> file_maps = new HashMap<>();
        file_maps.put("Hannibal",R.drawable.hannibal);
        file_maps.put("Big Bang Theory",R.drawable.bigbang);
        file_maps.put("House of Cards",R.drawable.house);
        file_maps.put("Game of Thrones", R.drawable.game_of_thrones);

        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {
                            ToastUtils.showDefaultSuperToast(HomeActivity.this,slider.getBundle().get("extra") + "");
                        }
                    });

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Tablet);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
//        sliderLayout.setCustomIndicator(pagerIndicator);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(4000);
        sliderLayout.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                ToastUtils.showDefaultSuperToast(HomeActivity.this,"onPageSelected:"+position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
    }

    private void pluginBottomBar() {

        bottomBar.setMode(BottomNavigationBar.MODE_FIXED)
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
                .addItem(new BottomNavigationItem(R.drawable.bottom_bar_home, bottom_bar_name_array[0]).setActiveColorResource(R.color.yellow))
                .addItem(new BottomNavigationItem(R.drawable.bottom_bar_live, bottom_bar_name_array[1]).setActiveColorResource(R.color.yellow))
                .addItem(new BottomNavigationItem(R.drawable.bottom_bar_find, bottom_bar_name_array[2]).setActiveColorResource(R.color.yellow))
                .addItem(new BottomNavigationItem(R.drawable.bottom_bar_me, bottom_bar_name_array[3]).setActiveColorResource(R.color.yellow))
                .setFirstSelectedPosition(0)
                .initialise();

        bottomBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                ToastUtils.showDefaultSuperToast(HomeActivity.this, "onTabSelected:" + position);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_home, menu);

        //初始化SearchView
        initSearchView(menu);

        return true;
    }

    private void initSearchView(Menu menu) {

        SearchManager searchManager =
                (SearchManager) getSystemService(SEARCH_SERVICE);

        final SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();

        searchView.setQueryHint(getString(R.string.hint_tool_bar_search));

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ToastUtils.showDefaultSuperToast(HomeActivity.this, query + ":搜索中。。。");
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

}
