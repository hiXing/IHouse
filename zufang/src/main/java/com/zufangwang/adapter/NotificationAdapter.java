package com.zufangwang.adapter;

import android.content.Context;
import android.widget.TextView;

import com.zufangwang.base.BaseAdapter;
import com.zufangwang.francis.zufangwang.R;

import java.util.List;

/**
 * Created by nan on 2016/3/21.
 */
public class NotificationAdapter extends BaseAdapter {
    public NotificationAdapter(Context mContext, List mDataList) {
        super(mContext, mDataList);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_notification;
    }

    @Override
    protected void bindData(ViewHolder holder, int position, Object item) {
        TextView courseName = (TextView) holder.getViewById(R.id.item_notification_courseName);
        TextView content = (TextView) holder.getViewById(R.id.item_notification_content);
    }

}
