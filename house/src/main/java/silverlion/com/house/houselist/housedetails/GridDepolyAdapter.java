package silverlion.com.house.houselist.housedetails;

import android.content.Context;
import android.util.Log;
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
public class GridDepolyAdapter extends BaseAdapter {
    private Context context;
    private List<HouseDaployResult> list;

    public GridDepolyAdapter(Context context, List<HouseDaployResult> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public HouseDaployResult getItem(int position) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_depoly,null);
            viewHolder.amount = (TextView) convertView.findViewById(R.id.amount);
            viewHolder.fac_name = (TextView) convertView.findViewById(R.id.fac);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        Log.i("house",list.get(position).getAmount());
        Log.i("house",list.get(position).getFacName());
        viewHolder.amount.setText(list.get(position).getAmount());
        viewHolder.fac_name.setText(list.get(position).getFacName());
        return convertView;
    }

    private class ViewHolder{
        TextView amount;
        TextView fac_name;
    }
}
