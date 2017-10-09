package top.huahaizhi.onlyu.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import com.google.gson.Gson;

import java.sql.SQLException;

import top.huahaizhi.onlyu.bean.ResultBean;
import top.huahaizhi.onlyu.bean.SettingsBean;
import top.huahaizhi.onlyu.database.SQLiteHelper;
import top.huahaizhi.onlyu.receiver.YiYanReceiver;
import top.huahaizhi.onlyu.thread.BaseRequest;
import top.huahaizhi.onlyu.thread.YiYanRequest;
import top.huahaizhi.onlyu.widget.MissView;

/**
 * Created by 海智 on 2017/7/8.
 */

public class YiYanService extends Service {

    private SettingsBean settingsBean;

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            settingsBean = new SQLiteHelper(this).getSettingsDao().queryForAll().get(0);
        } catch (SQLException e) {
            settingsBean = null;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent==null)
            return super.onStartCommand(null, flags, startId);
        if(intent.getBooleanExtra("Update",true)) {
            new YiYanRequest(this).go(new BaseRequest.RequestListener() {
                @Override
                public void onSuccess(String response) {
                    int[] appWidgetIds = AppWidgetManager.getInstance(YiYanService.this).getAppWidgetIds(new ComponentName(YiYanService.this, MissView.class));
                    for (int i : appWidgetIds) {
                        MissView.updateAppWidget(YiYanService.this, AppWidgetManager.getInstance(YiYanService.this), i, new Gson().fromJson(response,ResultBean.class));
                    }
                }
            });
            if (settingsBean.getRequestTime() != 3) {
                int time[] = new int[]{1000 * 60 * 30, 1000 * 60 * 60, 1000 * 60 * 120};
                AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + time[settingsBean.getRequestTime()], PendingIntent.getBroadcast(this, 0x001, new Intent(this, YiYanReceiver.class), 0));
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
