package com.zufangwang.fragment;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.zufangwang.activity.RentalActivity;
import com.zufangwang.base.BaseFragment;
import com.zufangwang.base.Configs;
import com.zufangwang.entity.HouseInfo;
import com.zufangwang.francis.zufangwang.R;
import com.zufangwang.utils.OkHttpClientManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Francis on 2016/4/28.
 */
public class RentalFragment extends BaseFragment implements View.OnClickListener,DatePicker.OnDateChangedListener{
    private TextView tv_start_date,tv_end_date,tv_all_price;
    private EditText et_start_date,et_end_date;
    private Button btn_confirm;
    private AlertDialog dialog;
    private String date;
    private int index=0;
    private String price;
    private HouseInfo houseInfo;

    public void setHouseInfo(HouseInfo houseInfo) {
        this.houseInfo = houseInfo;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_rental;
    }

    @Override
    protected void initView() {
        tv_start_date=(TextView)view.findViewById(R.id.tv_start_date);
        tv_end_date=(TextView)view.findViewById(R.id.tv_end_date);
        tv_all_price=(TextView)view.findViewById(R.id.tv_all_price);
        et_start_date=(EditText) view.findViewById(R.id.et_start_date);
        et_end_date=(EditText) view.findViewById(R.id.et_end_date);
        btn_confirm=(Button) view.findViewById(R.id.btn_confirm);
    }

    @Override
    protected void initData() {
        SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd ");
        et_start_date.setText(formatter.format(new Date()));
        et_end_date.setText(formatter.format(new Date()));
        AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
        dialog=builder.create();
        View timeView= LayoutInflater.from(mContext).inflate(R.layout.dialog_select_time,null);
        Button btn_cancle,btn_confirm;
        DatePicker datePicker;
        btn_cancle=(Button)timeView.findViewById(R.id.btn_cancle);
        btn_confirm=(Button)timeView.findViewById(R.id.btn_confirm);
        datePicker=(DatePicker)timeView.findViewById(R.id.datepicker);
        Calendar calendar = Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int monthOfYear=calendar.get(Calendar.MONTH);
        int dayOfMonth=calendar.get(Calendar.DAY_OF_MONTH);
        datePicker.init(year,monthOfYear,dayOfMonth,this);
        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index==0){
                    et_start_date.setText(date);
                    tv_all_price.setText("");
                    calculationPrice();
                    dialog.dismiss();
                }
                else{
                    et_end_date.setText(date);
                    tv_all_price.setText("");
                    dialog.dismiss();
                    calculationPrice();
                }

            }
        });

        dialog.setView(timeView);
    }
    //计算价格
    private void calculationPrice() {
        Log.i("ming","calculation");
        int i=0;
        try {
            i=daysBetween(et_start_date.getText().toString(),et_end_date.getText().toString());
            Log.i("ming","date:  "+daysBetween(et_start_date.getText().toString(),et_end_date.getText().toString()));
            if (i<=0){
                Toast.makeText(mContext,"结束日期应大于开始日期",Toast.LENGTH_SHORT).show();
                return;
            }
            int money=Integer.valueOf(price)/30*i;
            tv_all_price.setText(money+"元");
        } catch (ParseException e) {
            Log.i("ming","error");
            e.printStackTrace();
        }
    }

    @Override
    protected void initListener() {
        tv_start_date.setOnClickListener(this);
        tv_end_date.setOnClickListener(this);
        btn_confirm.setOnClickListener(this);

    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_start_date:
                index=0;
                dialog.show();

                break;
            case R.id.tv_end_date:
                index=1;
                dialog.show();
                break;
            case R.id.btn_confirm:
                setConfirm();
                break;
        }
    }
    //确定租房
    private void setConfirm() {
        AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
        builder.setTitle("提示");
        builder.setMessage("确定要租房?");
        builder.setNegativeButton("取消",null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(mContext,"租房成功",Toast.LENGTH_SHORT).show();
                deleteHouse();

            }
        });
        builder.create().show();
    }
    //租房成功后删除该房源
    private void deleteHouse() {
        Log.i("ming","house_no:  "+houseInfo.getHouse_no());
        OkHttpClientManager.postAsyn(Configs.DELETE_HOUSE, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                Log.i("ming","house_no  onResponse:  "+response);
                if (response.equals("1"))
                ((RentalActivity)getActivity()).getSucceed();
            }
        },new OkHttpClientManager.Param("house_no",String.valueOf(houseInfo.getHouse_no())));
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        if (monthOfYear<10)
        date=year+"-0"+(monthOfYear+1)+"-"+dayOfMonth;
        else
            date=year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
    }
    public static int daysBetween(String smdate,String bdate) throws ParseException{
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(smdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(bdate));
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);

        return Integer.parseInt(String.valueOf(between_days));
    }
}
