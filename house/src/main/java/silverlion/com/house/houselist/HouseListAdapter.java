package silverlion.com.house.houselist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import silverlion.com.house.R;

/**
 * Created by k8190 on 2016/7/27.
 */
public class HouseListAdapter extends BaseAdapter {
    private Context context;
    private List<HouseListResult> list;

    public HouseListAdapter(Context context, List<HouseListResult> list) {
        this.context = context;
        this.list = list;
    }

    public void updateListView(List<HouseListResult> list){
        this.list = list;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {return list.size()==0?0:list.size();}

    @Override
    public HouseListResult getItem(int position) {return list.get(position)==null?null:list.get(position);}

    @Override
    public long getItemId(int position) {return position;}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler hodler = null;
        if (convertView == null){
            hodler = new ViewHodler();
            convertView = LayoutInflater.from(context).inflate(R.layout.houselist_item,null);
            hodler.iamge = (ImageView) convertView.findViewById(R.id.image);
            hodler.address = (TextView) convertView.findViewById(R.id.address);
            hodler.time = (TextView) convertView.findViewById(R.id.time);
            convertView.setTag(hodler);
        }else {
            hodler = (ViewHodler) convertView.getTag();
        }
        hodler.address.setText(list.get(position).getPlace()+list.get(position).getAddress());
        hodler.time.setText(list.get(position).getTime());
        return convertView;
    }

    private class ViewHodler{
        ImageView iamge;
        TextView address;
        TextView time;
    }
}
