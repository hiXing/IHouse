package com.jucai.wuliu.net;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class MainServerRequest {

    private static MainServerRequest instance = null;
    public static String serverUrl = "http://112.74.130.140:8080";
    public static String serverHost = "http://112.74.130.140:8080/";
    public static int PAGE_SIZE = 20;

    public MainServerListener getListener() {
        return listener;
    }

    public void setListener(MainServerListener listener) {
        this.listener = listener;
    }

    public static MainServerRequest getInstance() {
        if (instance == null) {
            instance = new MainServerRequest();
        }
        return instance;
    }

    public MainServerListener listener = null;

    private void publicGetFunction(String urlPath) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(urlPath, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                String response = new String(arg2);
                try {
                    JSONObject json = new JSONObject(response);
                    if (json != null) {
                        listener.requestSuccess(json);
                        return;
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (listener != null) {
                    listener.requestFailure(response);
                }
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
                if (arg1 != null) {
                    if (listener != null) {
                        listener.requestFailure(arg1.toString());
                    }
                }
            }

        });
    }

    private void publicFunPost(final MainServerListener listener, String urlPath, RequestParams params) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(urlPath, params, new AsyncHttpResponseHandler() {

            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
                // TODO Auto-generated method stub
                if (arg1 != null) {
                    // Log.i("mpzhy", String.format("%d,header=%s",arg0,
                    // arg1.toString()));
                    if (listener != null) {
                        listener.requestFailure(arg1.toString());
                    }
                }

            }

            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                // TODO Auto-generated method stub

                String response = new String(arg2);
                JSONObject json;
                try {
                    json = new JSONObject(response);
                    // Log.i("mpzhy", response);
                    if (listener != null) {
                        listener.requestSuccess(json);
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });
    }

    /** 上传图片 */
    public void uploadPhotos(File file, String userid) throws FileNotFoundException {
        String urlPath = serverHost;
        urlPath += "user/user_getDownLoad";
        RequestParams param = new RequestParams();
        param.put("upload", file);
        param.put("userid", userid);
        param.setContentEncoding("UTF-8");
        publicFunPost(listener, urlPath, param);
    }

    /** 短信验证 */
    public void sms(String userTel, String eTime, String eKey) {
        String urlPath = serverHost;
        urlPath += "Sms/";
        RequestParams param = new RequestParams();
        param.put("UserTel", userTel);
        param.put("eTime", eTime);
        param.put("eKey", eKey);
        param.setContentEncoding("UTF-8");
        publicFunPost(listener, urlPath, param);
    }

    /** 注册 */
    public void reg(HashMap<String, String> map) {
        String urlPath = serverHost;
        urlPath += "Reg/";
        RequestParams param = new RequestParams();
        param.put("Nick", map.get("Nick"));
        param.put("UserTel", map.get("UserTel"));
        param.put("CompanyCode", map.get("CompanyCode"));
        param.put("TelCode", map.get("TelCode"));
        param.put("SerialNum", map.get("SerialNum"));
        param.put("eType", map.get("eType"));
        param.put("eCode", map.get("eCode"));
        param.put("eTime", map.get("eTime"));
        param.put("eKey", map.get("eKey"));
        param.setContentEncoding("UTF-8");
        publicFunPost(listener, urlPath, param);
    }

    /** 服务器时间 */
    public void stime(String time) {
        String urlPath = serverHost;
        urlPath += "stime/";
        RequestParams param = new RequestParams();
        param.put("eTime", time);
        param.setContentEncoding("UTF-8");
        publicFunPost(listener, urlPath, param);
    }

    /** 登陆 */
    public void login(HashMap<String, String> map) {
        String urlPath = serverHost;
        urlPath += "Login/";
        RequestParams param = new RequestParams();
        param.put("UserName", map.get("UserName"));
        param.put("UserPass", map.get("UserPass"));
        param.put("eType", map.get("eType"));
        param.put("eTime", map.get("eTime"));
        param.put("eKey", map.get("eKey"));
        param.put("eCode", map.get("eCode"));
        param.setContentEncoding("UTF-8");
        publicFunPost(listener, urlPath, param);
    }

    /** 退出登陆 */
    public void outlogin(HashMap<String, String> map) {
        String urlPath = serverHost;
        urlPath += "Exit/";
        RequestParams param = new RequestParams();
        param.put("open_id", map.get("open_id"));
        param.put("token_id", map.get("token_id"));
        param.put("eTime", map.get("eTime"));
        param.put("eCode", map.get("eCode"));
        param.setContentEncoding("UTF-8");
        publicFunPost(listener, urlPath, param);
    }

    /** 用户基本信息 */
    public void userInfo(HashMap<String, String> map) {
        String urlPath = serverHost;
        urlPath += "User/";
        RequestParams param = new RequestParams();
        param.put("open_id", map.get("open_id"));
        param.put("token_id", map.get("token_id"));
        param.put("eTime", map.get("eTime"));
        param.put("eCode", map.get("eCode"));
        param.setContentEncoding("UTF-8");
        publicFunPost(listener, urlPath, param);
    }

    /** 客户报备 */
    public void customerUp(HashMap<String, String> map) {
        String urlPath = serverHost;
        urlPath += "CustomerUp/";
        RequestParams param = new RequestParams();
        param.put("open_id", map.get("open_id"));
        param.put("token_id", map.get("token_id"));
        param.put("eTime", map.get("eTime"));
        param.put("eCode", map.get("eCode"));
        param.put("eName", map.get("eName"));
        param.put("eTel", map.get("eTel"));
        param.put("sex", map.get("sex"));
        param.put("PropCode", map.get("PropCode"));
        param.setContentEncoding("UTF-8");
        publicFunPost(listener, urlPath, param);
    }

    /** 业务员报备客户列表 */
    public void customer(HashMap<String, String> map) {
        String urlPath = serverHost;
        urlPath += "Customer/";
        RequestParams param = new RequestParams();
        param.put("open_id", map.get("open_id"));
        param.put("token_id", map.get("token_id"));
        param.put("eTime", map.get("eTime"));
        param.put("eCode", map.get("eCode"));
        param.put("page", map.get("page"));
        param.put("keyword", map.get("keyword"));
        param.put("eType", map.get("eType"));
        param.setContentEncoding("UTF-8");
        publicFunPost(listener, urlPath, param);
    }

    /** 楼盘列表 */
    public void project(HashMap<String, String> map) {
        String urlPath = serverHost;
        urlPath += "Project/";
        RequestParams param = new RequestParams();
        param.put("open_id", map.get("open_id"));
        param.put("token_id", map.get("token_id"));
        param.put("eTime", map.get("eTime"));
        param.put("eCode", map.get("eCode"));
        param.put("page", map.get("page"));
        param.put("keyword", map.get("keyword"));
        param.setContentEncoding("UTF-8");
        publicFunPost(listener, urlPath, param);
    }

    /** 找回密码 */
    public void RetrievePassword(HashMap<String, String> map) {
        String urlPath = serverHost;
        urlPath += "RetrievePassword/";
        RequestParams param = new RequestParams();
        param.put("eName", map.get("eName"));
        param.put("Tel", map.get("Tel"));
        param.put("eTime", map.get("eTime"));
        param.put("eCode", map.get("eCode"));
        param.put("eKey", map.get("eKey"));
        param.put("TelCode", map.get("TelCode"));
        param.put("SerialNum", map.get("SerialNum"));
        param.setContentEncoding("UTF-8");
        publicFunPost(listener, urlPath, param);
    }

    /** 修改密码 */
    public void EditPass(HashMap<String, String> map) {
        String urlPath = serverHost;
        urlPath += "EditPass/";
        RequestParams param = new RequestParams();
        param.put("open_id", map.get("open_id"));
        param.put("token_id", map.get("token_id"));
        param.put("eTime", map.get("eTime"));
        param.put("eCode", map.get("eCode"));
        param.put("OldPass", map.get("OldPass"));
        param.put("NewPass", map.get("NewPass"));
        param.put("TelCode", map.get("TelCode"));
        param.put("SerialNum", map.get("SerialNum"));
        param.setContentEncoding("UTF-8");
        publicFunPost(listener, urlPath, param);
    }

    /** 用户身份信息 */
    public void Information(HashMap<String, String> map) {
        String urlPath = serverHost;
        urlPath += "Information/";
        RequestParams param = new RequestParams();
        param.put("open_id", map.get("open_id"));
        param.put("token_id", map.get("token_id"));
        param.put("eTime", map.get("eTime"));
        param.put("eCode", map.get("eCode"));
        param.setContentEncoding("UTF-8");
        publicFunPost(listener, urlPath, param);
    }

    /**
     * 用户身份信息
     *
     * @throws FileNotFoundException
     */
    public void UpImg(HashMap<String, String> map, File file) throws FileNotFoundException {
        String urlPath = serverHost;
        urlPath += "UpImg/";
        RequestParams param = new RequestParams();
        param.put("open_id", map.get("open_id"));
        param.put("token_id", map.get("token_id"));
        param.put("eTime", map.get("eTime"));
        param.put("eCode", map.get("eCode"));
        param.put("ImgBase64", file);
        param.put("eType", map.get("eType"));
        param.setContentEncoding("UTF-8");
        publicFunPost(listener, urlPath, param);
    }

    /** 修改信息数据 */
    public void ExitInformation(HashMap<String, String> map) {
        String urlPath = serverHost;
        urlPath += "ExitInformation/";
        RequestParams param = new RequestParams();
        param.put("open_id", map.get("open_id"));
        param.put("token_id", map.get("token_id"));
        param.put("eTime", map.get("eTime"));
        param.put("eCode", map.get("eCode"));
        param.put("eName", map.get("eName"));
        param.put("Nick", map.get("Nick"));
        param.put("eAddress", map.get("eAddress"));
        param.put("CardUrl1", map.get("CardUrl1"));
        param.put("CardUrl2", map.get("CardUrl2"));
        param.put("CardID", map.get("CardID"));
        param.put("BankAccount", map.get("BankAccount"));
        param.put("BankCard", map.get("BankCard"));
        param.put("TelCode", map.get("TelCode"));
        param.put("SerialNum", map.get("SerialNum"));
        param.setContentEncoding("UTF-8");
        publicFunPost(listener, urlPath, param);
    }

    /** 客户列表接口（置业顾问） */
    public void CustomerAdviser(HashMap<String, String> map) {
        String urlPath = serverHost;
        urlPath += "CustomerAdviser/";
        RequestParams param = new RequestParams();
        param.put("open_id", map.get("open_id"));
        param.put("token_id", map.get("token_id"));
        param.put("eTime", map.get("eTime"));
        param.put("eCode", map.get("eCode"));
        param.put("page", map.get("page"));
        param.put("keyword", map.get("keyword"));

        param.setContentEncoding("UTF-8");
        publicFunPost(listener, urlPath, param);
    }

    /** 客户动态接口（置业顾问） */
    public void ModifyDynamic(HashMap<String, String> map) {
        String urlPath = serverHost;
        urlPath += "ModifyDynamic/";
        RequestParams param = new RequestParams();
        param.put("open_id", map.get("open_id"));
        param.put("token_id", map.get("token_id"));
        param.put("eTime", map.get("eTime"));
        param.put("eCode", map.get("eCode"));
        param.put("Customer", map.get("Customer"));
        param.put("eType", map.get("eType"));

        param.setContentEncoding("UTF-8");
        publicFunPost(listener, urlPath, param);
    }

    /** 补全客户资料接口（置业顾问） */
    public void SuppCustomer(HashMap<String, String> map) {
        String urlPath = serverHost;
        urlPath += "SuppCustomer/";
        RequestParams param = new RequestParams();
        param.put("open_id", map.get("open_id"));
        param.put("token_id", map.get("token_id"));
        param.put("eTime", map.get("eTime"));
        param.put("eCode", map.get("eCode"));
        param.put("Customer", map.get("Customer"));
        param.put("eName", map.get("eName"));
        param.put("Tel", map.get("Tel"));
        param.put("Sex", map.get("Sex"));
        param.put("CardID", map.get("CardID"));
        param.put("Deposit", map.get("Deposit"));
        param.put("Deposit1", map.get("Deposit1"));
        param.put("RoomNumber", map.get("RoomNumber"));
        param.put("Measure", map.get("Measure"));
        param.put("Atavistic", map.get("Atavistic"));
        param.put("Reversion", map.get("Reversion"));
        param.put("Life", map.get("Life"));
        param.put("eMomey", map.get("eMomey"));
        param.setContentEncoding("UTF-8");
        publicFunPost(listener, urlPath, param);
    }
}