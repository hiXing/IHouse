package silverlion.com.house.houselist.housedetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import silverlion.com.house.R;

/**
 * Created by k8190 on 2016/8/3.
 */
public class GridConvenienceAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;

    public GridConvenienceAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public String  getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_convenience,null);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.fac);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.textView.setText(list.get(position));
        return convertView;
    }

    private class ViewHolder{
        TextView textView;
    }
}
