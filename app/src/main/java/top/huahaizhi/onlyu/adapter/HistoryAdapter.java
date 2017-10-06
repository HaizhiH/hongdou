package top.huahaizhi.onlyu.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import top.huahaizhi.onlyu.R;
import top.huahaizhi.onlyu.bean.ResultBean;

/**
 * Created by 海智 on 2017/7/10.
 */

public class HistoryAdapter extends BaseQuickAdapter<ResultBean, BaseViewHolder> {


    public HistoryAdapter(@LayoutRes int layoutResId, @Nullable List<ResultBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ResultBean item) {
        helper.setText(R.id.tv_history_yiyan, item.getHitokoto());
        helper.setText(R.id.tv_history_yiyanFrom, item.getFrom());
    }
}
