package top.huahaizhi.onlyu.widget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.RemoteViews;

import com.google.gson.Gson;

import top.huahaizhi.onlyu.R;
import top.huahaizhi.onlyu.activity.SplashActivity;
import top.huahaizhi.onlyu.bean.ResultBean;
import top.huahaizhi.onlyu.bean.SettingsBean;
import top.huahaizhi.onlyu.database.SQLiteHelper;
import top.huahaizhi.onlyu.receiver.YiYanReceiver;
import top.huahaizhi.onlyu.service.YiYanService;
import top.huahaizhi.onlyu.thread.BaseRequest;
import top.huahaizhi.onlyu.thread.YiYanRequest;

import static android.util.TypedValue.COMPLEX_UNIT_SP;

public class MissView extends AppWidgetProvider {

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId, ResultBean result) {
        SettingsBean bean = new SQLiteHelper(context).getBean(context);
        RemoteViews view = new RemoteViews(context.getPackageName(), R.layout.miss_view);

        view.setTextViewTextSize(R.id.appwidget_text, COMPLEX_UNIT_SP, bean.getTextSize());
        view.setTextViewTextSize(R.id.appwidget_text_from, COMPLEX_UNIT_SP, bean.getTextSize());

        view.setTextColor(R.id.appwidget_text, bean.getTextColor());
        view.setTextColor(R.id.appwidget_text_from, bean.getTextColor());

        //点击事件
        if (bean.getClickEvent() == 0)
            view.setOnClickPendingIntent(R.id.appwidget_text, PendingIntent.getService(context, 0x001, new Intent(context, YiYanService.class), 0));
        else if (bean.getClickEvent() == 1)
            view.setOnClickPendingIntent(R.id.appwidget_text, PendingIntent.getActivity(context, 0x001, new Intent(context, SplashActivity.class), 0));

        //文字居中
        if (bean.getTextGravity() == 0)
            view.setInt(R.id.appwidget_root, "setGravity", Gravity.START | Gravity.CENTER);
        else if (bean.getTextGravity() == 1)
            view.setInt(R.id.appwidget_root, "setGravity", Gravity.END | Gravity.CENTER);
        else
            view.setInt(R.id.appwidget_root, "setGravity", Gravity.CENTER);

        //显示来源
        view.setViewVisibility(R.id.appwidget_text_from, bean.isTextFrom() ? View.VISIBLE : View.GONE);

        //追加句号
        if (bean.isAddTextDot() && !TextUtils.isEmpty(result.getHitokoto()))
            if (!(result.getHitokoto().endsWith("。") || result.getHitokoto().endsWith("！") || result.getHitokoto().endsWith("？")))
                result.setHitokoto(result.getHitokoto() + "。");

        view.setTextViewText(R.id.appwidget_text, result.getHitokoto());
        if (!TextUtils.isEmpty(result.getFrom()))
            view.setTextViewText(R.id.appwidget_text_from, "——来自 " + result.getFrom());

        //保存到本地
        if (bean.isSaveYiYanToLocal())
            new SQLiteHelper(context).saveYiYan(result);

        appWidgetManager.updateAppWidget(appWidgetId, view);
    }

    @Override
    public void onUpdate(final Context context, final AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (final int appWidgetId : appWidgetIds) {
            new YiYanRequest(context).go(new BaseRequest.RequestListener() {
                @Override
                public void onSuccess(String response) {
                    if(!TextUtils.isEmpty(response)){
                        ResultBean yiyanResult = new Gson().fromJson(response,ResultBean.class);
                        if(yiyanResult != null)
                            updateAppWidget(context, appWidgetManager, appWidgetId, yiyanResult);
                    }
                }
            });
        }
    }

    @Override
    public void onEnabled(Context context) {
        if (context != null)
            context.startService(new Intent(context, YiYanService.class));
    }

    @Override
    public void onDisabled(Context context) {
        if(context!=null) {
            context.stopService(new Intent(context, YiYanService.class));
            AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            manager.cancel(PendingIntent.getBroadcast(context, 0x001, new Intent(context, YiYanReceiver.class), 0));
        }
    }
}