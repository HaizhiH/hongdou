package top.huahaizhi.onlyu.thread;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.sql.SQLException;

import top.huahaizhi.onlyu.bean.ResultBean;
import top.huahaizhi.onlyu.bean.SettingsBean;
import top.huahaizhi.onlyu.database.SQLiteHelper;

/**
 * Created by 海智 on 2017/7/9.
 */

public class YiYanRequest extends Thread {

    private Context context;

    private String type;

    private RequestListener listener;

    private SettingsBean bean;

    public YiYanRequest(Context context) {
        this.context = context;
        bean = new SQLiteHelper(context).getBean(context);
        type= "abcdefg ".charAt(bean.getRequestType()) + "";
    }

    public void go(RequestListener listener) {
        this.listener = listener;
        start();
    }

    @Override
    public void run() {
        StringRequest request = new StringRequest(Request.Method.GET, "https://sslapi.hitokoto.cn?c="+type+"&encode=json", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                ResultBean resultBean = new Gson().fromJson(response, ResultBean.class);
                if (bean.isSaveYiYanToLocal())
                    try {
                        new SQLiteHelper(context).getResultDao().create(resultBean);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                listener.onSuccess(resultBean);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(context).add(request);
    }

    public interface RequestListener {
        void onSuccess(ResultBean bean);
    }
}
