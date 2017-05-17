package com.lele.kanfang.ui.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lele.kanfang.R;

import java.util.List;

/**
 * Created by wuping on 2017/1/12.
 */

public class FirstHomeAdapter  extends RecyclerView.Adapter<FirstHomeAdapter.MyViewHolder> {

    private Context context;

    private List<KanFangShi> list;

    public FirstHomeAdapter(Context context, List<KanFangShi> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_home_1,parent,false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View itemView) {
            super(itemView);

        }
    }

}
