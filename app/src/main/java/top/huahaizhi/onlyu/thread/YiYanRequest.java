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

public class YiYanRequest extends BaseRequest {

    public YiYanRequest(Context context) {
        super(context);
        SettingsBean bean = new SQLiteHelper(context).getBean(context);
        String type = "abcdefg ".charAt(bean.getRequestType()) + "";
        url = "https://sslapi.hitokoto.cn?c=" + type + "&encode=json";
    }

}
