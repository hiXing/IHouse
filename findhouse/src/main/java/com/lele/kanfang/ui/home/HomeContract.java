package com.lele.kanfang.ui.home;

import com.lele.kanfang.base.BasePresenter;
import com.lele.kanfang.base.BaseView;

/**
 * Created by wuping on 2017/1/5.
 */

public interface HomeContract {

    interface Presenter extends BasePresenter{

        void getBannerList();//获取banner列表URL

        void openBannerDetail();//点击banner

        void search();//搜索

        void zhengZu();//整租

        void heZu();//合租

        void zhouBian();//周边

        void oneVSOne();//1v1专区

        void getStarLandlord();//明星看房师

        void getAllStarLandlord();//全部明星看房师

        void getStarLandlordDetail();//查看一个明星看房师的详情

        void getStarHourseRes();//明星房源

        void getAllStarHourseRes();//全部明星房源

        void getStarHourseResDetail();//查看一个明星房源的详情
    }

    interface View extends BaseView<Presenter>{

        void showCityListUi();//选择城市

        void showBannerDetailUi();//点击Banner进入详情页

        void showZhengZuUi();//进入整组模块

        void showHeZuUi();//进入合租模块

        void showZhouBianUi();//进入周边模块

        void show1V1Ui();//进入1v1专区

        void showStarLandlordDetaiUi();

        void showAllStarLandlordUi();

        void showStarHourseResDetailUi();

        void showAllStarHourseRes();

    }

}
