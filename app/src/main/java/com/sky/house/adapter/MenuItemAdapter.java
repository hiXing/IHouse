package com.sky.house.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sky.house.R;
import com.sky.house.entity.MenuItem;

/**
 * 菜单列表List适配器
 * @author skypan
 * 
 */
public class MenuItemAdapter extends BaseAdapter {
    //上下文
	private Context mContext;
	//菜单列表
	private List<MenuItem> mListData;
	private int selectedPos = -1;
	private String selectedText = "";
	private int normalDrawbleId;
	private Drawable selectedDrawble;
	private float textSize;
	private OnClickListener onClickListener;
	private OnItemClickListener mOnItemClickListener;

	public MenuItemAdapter(Context context, List<MenuItem> listData, int sId, int nId) {
		mContext = context;
		mListData = listData;
		selectedDrawble = mContext.getResources().getDrawable(sId);
		normalDrawbleId = nId;
		init();
	}

	private void init() {
		onClickListener = new OnClickListener() {

			@Override
			public void onClick(View view) {
				selectedPos = (Integer) view.getTag();
				setSelectedPosition(selectedPos);
				if (mOnItemClickListener != null) {
					mOnItemClickListener.onItemClick(view, selectedPos);
				}
			}
		};
	}
	/**
	 * 设置选中的position,并通知刷新其它列表
	 */
	public void setSelectedPosition(int pos) {
		if (mListData != null && pos < mListData.size()) {
			selectedPos = pos;
			selectedText = mListData.get(pos).getName();
			notifyDataSetChanged();
		}
	}

	/**
	 * 设置选中的position,但不通知刷新
	 */
	public void setSelectedPositionNoNotify(int pos) {
		selectedPos = pos;
		if (mListData != null && pos < mListData.size()) {
			selectedText = mListData.get(pos).getName();
		} 
	}

	/**
	 * 获取选中的position
	 */
	public int getSelectedPosition() {
		
		if (mListData != null && selectedPos < mListData.size()) {
			return selectedPos;
		}

		return -1;
	}

	/**
	 * 设置列表字体大小
	 */
	public void setTextSize(float tSize) {
		textSize = tSize;
	}

	@SuppressWarnings("deprecation")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView view;
		if (convertView == null) {
			view = (TextView) LayoutInflater.from(mContext).inflate(R.layout.choose_item, parent, false);
		} else {
			view = (TextView) convertView;
		}
		view.setTag(position);
		String mString = "";
		if (mListData != null) {
			if (position < mListData.size()) {
				mString = mListData.get(position).getName();
			}
		}
		if (mString.contains("不限"))
			view.setText("不限");
		else
			view.setText(mString);
		view.setTextSize(TypedValue.COMPLEX_UNIT_SP,textSize);

		if (selectedText != null && selectedText.equals(mString)) {
			view.setBackgroundDrawable(selectedDrawble);//设置选中的背景图�?
		} else {
			view.setBackgroundDrawable(mContext.getResources().getDrawable(normalDrawbleId));//设置未�?中状态背景图�?
		}
		view.setPadding(20, 0, 0, 0);
		view.setOnClickListener(onClickListener);
		return view;
	}

	public void setOnItemClickListener(OnItemClickListener l) {
		mOnItemClickListener = l;
	}

	/**
	 * 重新定义菜单选项单击接口
	 */
	public interface OnItemClickListener {
		public void onItemClick(View view, int position);
	}

	@Override
	public int getCount() {
		return mListData.size();
	}

	@Override
	public Object getItem(int position) {
		return mListData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

}
