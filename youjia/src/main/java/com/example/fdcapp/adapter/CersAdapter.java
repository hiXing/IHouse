package com.example.fdcapp.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.fdcapp.R;
import com.example.fdcapp.obj.CusRportObject;

public class CersAdapter extends BaseAdapter {
	private ArrayList<CusRportObject> mList;
	private Context mContext;
	private LayoutInflater mInflater;

	public CersAdapter(Context context, ArrayList<CusRportObject> mList) {
		this.mList = mList;
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (mList == null) {
			return 0;
		}
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder;
		CusRportObject object = mList.get(position);
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_cers, null);
			viewHolder.eName = (TextView) convertView.findViewById(R.id.name_cers_tv);
			viewHolder.Tel = (TextView) convertView.findViewById(R.id.phone_cers_tv);
			viewHolder.Prop = (TextView) convertView.findViewById(R.id.fname_cers_tv);
			viewHolder.eTime = (TextView) convertView.findViewById(R.id.time_cers_tv);
			viewHolder.eType = (TextView) convertView.findViewById(R.id.iscome_cers_tv);
			viewHolder.Sex = (TextView) convertView.findViewById(R.id.sex_cers_tv);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.eName.setText(object.geteName());
		viewHolder.Tel.setText(object.getTel());
		viewHolder.Prop.setText(object.getProp());
		viewHolder.eTime.setText(object.geteTime());
		viewHolder.Sex.setText(object.getSex());
		viewHolder.eType.setText(object.geteType());
		return convertView;
	}

	class ViewHolder {
		public TextView eName;
		public TextView Tel;
		public TextView Sex;
		public TextView Prop;
		public TextView eCode;
		public TextView eTime;
		public TextView eType;
	}

}
