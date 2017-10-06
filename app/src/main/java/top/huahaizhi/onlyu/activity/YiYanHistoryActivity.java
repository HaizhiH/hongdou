package top.huahaizhi.onlyu.activity;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import top.huahaizhi.onlyu.R;
import top.huahaizhi.onlyu.adapter.HistoryAdapter;
import top.huahaizhi.onlyu.bean.ResultBean;
import top.huahaizhi.onlyu.database.SQLiteHelper;

public class YiYanHistoryActivity extends AppCompatActivity implements  BaseQuickAdapter.OnItemLongClickListener {

    private List<ResultBean> results;
    private RecyclerView recyclerView;

    private HistoryAdapter mAdapter;

    private static final String TAG = "YiYanHistoryActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yi_yan_history);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_history_searchResult);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("本地的一言");
        try {
            results = new SQLiteHelper(this).getResultDao().queryForAll();
        } catch (SQLException e) {
            results = new ArrayList<>();
        }
        mAdapter = new HistoryAdapter(R.layout.history_item, results);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemLongClickListener(this);
        Toast.makeText(this, "长按可以复制哦～～", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        menu.add("收藏").setIcon(R.drawable.ic_star_white_24dp).setShowAsAction(SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, final int position) {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(100);
        new AlertDialog.Builder(this).setTitle("是否复制？").setPositiveButton("👌", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ClipData clipData = ClipData.newPlainText("text",results.get(position).getHitokoto());
                ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                cm.setPrimaryClip(clipData);
                Toast.makeText(YiYanHistoryActivity.this, "复制成功～", Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("取消",null).show();
        return false;
    }
}
