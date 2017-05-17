package com.example.fdcapp.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ab.bitmap.AbImageDownloader;
import com.ab.global.AbConstant;
import com.example.fdcapp.R;
import com.example.fdcapp.activity.CusrportSonActivity;
import com.example.fdcapp.obj.HousesObject;

public class CusRportAdapter extends BaseAdapter {
	private ArrayList<HousesObject> mList;
	private Context mContext;
	private LayoutInflater mInflater;

	public void CusRportAdapter(Context context, ArrayList<HousesObject> mList) {
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
		final HousesObject object = mList.get(position);
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_cusrep, null);
			viewHolder.ImgUrl = (ImageView) convertView.findViewById(R.id.img_cusrep_tv);
			viewHolder.eAddress = (TextView) convertView.findViewById(R.id.addr_cusper_tv);
			viewHolder.eName = (TextView) convertView.findViewById(R.id.lname_cusrep_tv);
			viewHolder.eMomey = (TextView) convertView.findViewById(R.id.price_cusper_tv);
			viewHolder.Tel = (TextView) convertView.findViewById(R.id.tel_cusper_tv);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.eAddress.setText(object.geteAddress());
		viewHolder.eName.setText(object.geteName());
		viewHolder.eMomey.setText(object.geteMomey());
		viewHolder.Tel.setText(object.getTel());
		AbImageDownloader imageDownloader;
		imageDownloader = new AbImageDownloader(mContext);
		imageDownloader.setWidth(200);
		imageDownloader.setHeight(200);
		imageDownloader.setType(AbConstant.SCALEIMG);
		String imageUrl = object.getImgUrl();
		imageDownloader.display(viewHolder.ImgUrl, imageUrl);
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mContext, CusrportSonActivity.class);
				intent.putExtra("eCode", object.geteCode());
				mContext.startActivity(intent);
			}
		});
		return convertView;
	}

	class ViewHolder {
		public ImageView eCode;
		public TextView eName;
		public ImageView ImgUrl;
		public TextView eAddress;
		public TextView Tel;
		public TextView eType;
		public TextView eMomey;
		public TextView eTime;
	}

}
