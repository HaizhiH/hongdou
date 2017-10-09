package top.huahaizhi.onlyu.thread;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by haizhi on 2017/10/9.
 */
public class BaseRequest extends Thread {

    private Context context;

    protected String url;

    private BaseRequest.RequestListener listener;

    public BaseRequest(Context context) {
        this.context = context;
        this.url = "";
    }

    public BaseRequest setUrl(String url) {
        this.url = url;
        return this;
    }

    public void go(BaseRequest.RequestListener listener) {
        this.listener = listener;
        start();
    }

    @Override
    public void run() {
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                listener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(context).add(request);
    }

    public interface RequestListener {
        void onSuccess(String response);
    }
}
