package top.huahaizhi.onlyu.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import top.huahaizhi.onlyu.service.YiYanService;

public class YiYanReceiver extends BroadcastReceiver {

    private static final String TAG = "YiYanReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, YiYanService.class));
    }
}
