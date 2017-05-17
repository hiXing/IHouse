package com.zufangwang.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zufangwang.activity.GoodDesActivity;
import com.zufangwang.activity.HouseDesActivity;
import com.zufangwang.adapter.HouseItemAdapter;
import com.zufangwang.base.BaseAdapter;
import com.zufangwang.base.BaseFragment;
import com.zufangwang.base.Configs;
import com.zufangwang.entity.CollectInfo;
import com.zufangwang.entity.GoodsInfo;
import com.zufangwang.entity.HouseInfo;
import com.zufangwang.listener.OnItemClickListener;
import com.zufangwang.listener.OnItemLongClickListener;
import com.zufangwang.francis.zufangwang.R;
import com.zufangwang.utils.OkHttpClientManager;
import com.squareup.okhttp.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Francis on 2016/4/15.
 */
public class MyCollectFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    private TextView tv_hint_my_collect;
    private RecyclerView recycler_my_collect;
    private SwipeRefreshLayout swipe_collect_list;
    private ArrayList<CollectInfo> collectInfos;
    private ArrayList<HouseInfo> houseInfos;
    private HouseItemAdapter houseItemAdapter;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_collect;
    }

    @Override
    protected void initView() {
        tv_hint_my_collect=(TextView)view.findViewById(R.id.tv_hint_my_collect);
        recycler_my_collect=(RecyclerView)view.findViewById(R.id.recycle_my_collect);
        swipe_collect_list=(SwipeRefreshLayout)view.findViewById(R.id.swipe_collect_list);
        swipe_collect_list.setVisibility(View.VISIBLE);
        swipe_collect_list.setColorSchemeColors(R.color.colorPrimary);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_my_collect.setLayoutManager(layoutManager);
    }

    @Override
    protected void initData() {
        getCollect();
    }

    @Override
    protected void initListener() {
        swipe_collect_list.setOnRefreshListener(this);
    }

    @Override
    protected void loadData() {

    }
    private void getCollect(){
        collectInfos=new ArrayList<>();
        swipe_collect_list.setRefreshing(true);
        OkHttpClientManager.postAsyn(Configs.GETCOLLECT_HOUSE, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(getActivity(), Configs.URLERROR,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                Log.i("ming","GETCOLLECT_HOUSE    onResponse:  "+response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject;
                    CollectInfo collectInfo;
                    for (int i=0;i<jsonArray.length();i++){
                        jsonObject=jsonArray.getJSONObject(i);
                        collectInfo=new Gson().fromJson(jsonObject.toString(),CollectInfo.class);
                        collectInfos.add(collectInfo);
                    }
                    if (collectInfos.size()>0)
                    getGoodsList();
                    else {
                        swipe_collect_list.setRefreshing(false);
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new OkHttpClientManager.Param("user_id", getActivity().getSharedPreferences("user",0).getString("user_id","")));
    }
    private void initCollectList(){
        swipe_collect_list.setRefreshing(false);
        if (collectInfos.size()>0){
            tv_hint_my_collect.setVisibility(View.GONE);
            recycler_my_collect.setVisibility(View.VISIBLE);
        }
//        collectAdapter=new CollectAdapter(getActivity(),goodsInfos);
        Log.i("ming","houseInfos   houseInfos :  "+houseInfos.size());
        houseItemAdapter=new HouseItemAdapter(getActivity(),houseInfos);
//        recycler_my_collect.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler_my_collect.setAdapter(houseItemAdapter);

        //item点击事件
        houseItemAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(getActivity(), HouseDesActivity.class);
                intent.putExtra("house_info",houseInfos.get(position));
                startActivity(intent);
            }
        });
        //长按item弹出对话框是否删除收藏记录
        houseItemAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(View view, final int position) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                builder.setTitle("提示");
                builder.setMessage("是否删除该条收藏?");
                builder.setNegativeButton("取消",null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteMyCollect(houseInfos.get(position));
                    }
                });
                builder.create().show();
                return true;
            }
        });


    }

    @Override
    public void onRefresh() {
        getCollect();
    }

    class CollectAdapter extends BaseAdapter<GoodsInfo> {
        public CollectAdapter(Context mContext, List<GoodsInfo> mDataList) {
            super(mContext, mDataList);
        }

        @Override
        protected int getItemLayoutId(int viewType) {
            return R.layout.item_goods_list;
        }

        @Override
        protected void bindData(ViewHolder holder, int position, GoodsInfo item) {
            TextView tv_goods_name,tv_goods_price,tv_goods_publisher,tv_goods_publish_date;
            ImageView iv_collect;
            tv_goods_name = (TextView) holder.getViewById(R.id.tv_goods_name);
            tv_goods_price= (TextView) holder.getViewById(R.id.tv_goods_price);
            tv_goods_publisher= (TextView) holder.getViewById(R.id.tv_goods_publisher);
            tv_goods_publish_date= (TextView) holder.getViewById(R.id.tv_goods_publish_date);

            iv_collect=(ImageView)holder.getViewById(R.id.iv_collect);

            iv_collect.setVisibility(View.GONE);
            tv_goods_name.setText(item.getGoods_name());
            tv_goods_price.setText(item.getGoods_price());
            tv_goods_publisher.setText(item.getGoods_publisher());
            tv_goods_publish_date.setText(item.getGoods_publish_date());

        }

    }
    //由收藏列表获得商品列表
    private void getGoodsList(){
        houseInfos=new ArrayList<>();
        for (final CollectInfo collectInfo: collectInfos){
            Log.i("ming","collectinfo user_no :  "+collectInfos.get(0).getGoods_no());
            OkHttpClientManager.postAsyn(Configs.QUERYGOODS_BY_HOUSENO, new OkHttpClientManager.ResultCallback<String>() {
                @Override
                public void onError(Request request, Exception e) {
                    Toast.makeText(getActivity(),Configs.URLERROR,Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(String response) {
                    Log.i("ming","getGoodsList  response:  "+response);
                    HouseInfo houseInfo=new Gson().fromJson(response,HouseInfo.class);
                    houseInfos.add(houseInfo);
                    Log.i("ming","houseInfos in ok:  "+houseInfos.size());
                    if (houseInfos.size()==collectInfos.size())
                        initCollectList();
                }
            },new OkHttpClientManager.Param("house_no",collectInfo.getGoods_no()));
        }

    }
    //删除我的收藏
    private void deleteMyCollect(final HouseInfo houseInfo){
        OkHttpClientManager.postAsyn(Configs.DELETECOLLECT_HOUSE, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(getActivity(),Configs.URLERROR,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                if (response.equals("1")){
                    houseInfos.remove(houseInfo);
                    initCollectList();
                }

            }
        },new OkHttpClientManager.Param[]{
                new OkHttpClientManager.Param("house_no",String.valueOf(houseInfo.getHouse_no())),
                new OkHttpClientManager.Param("user_name",getActivity().getSharedPreferences("user",0).getString("user_name","")),
                new OkHttpClientManager.Param("user_id",getActivity().getSharedPreferences("user",0).getString("user_id",""))
        });
    }

}
