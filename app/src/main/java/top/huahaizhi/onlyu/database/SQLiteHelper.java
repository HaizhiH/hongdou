package top.huahaizhi.onlyu.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import top.huahaizhi.onlyu.bean.ResultBean;
import top.huahaizhi.onlyu.bean.SettingsBean;

/**
 * Created by 海智 on 2017/7/8.
 */

public class SQLiteHelper extends OrmLiteSqliteOpenHelper {

    public SQLiteHelper(Context context) {
        super(context, "Settings", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, SettingsBean.class);
            TableUtils.createTable(connectionSource, ResultBean.class);
            getSettingsDao().create(new SettingsBean());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }

    public Dao<ResultBean, Integer> getResultDao() {
        try {
            return getDao(ResultBean.class);
        } catch (SQLException e) {
            return null;
        }
    }

    public Dao<SettingsBean, Integer> getSettingsDao() {
        try {
            return getDao(SettingsBean.class);
        } catch (SQLException e) {
            return null;
        }
    }

    public SettingsBean getBean(Context context) {
        try {
            return getSettingsDao().queryForAll().get(0);
        } catch (SQLException e) {
            return null;
        }
    }

    public void saveYiYan(ResultBean resultBean) {
        try {
            getResultDao().create(resultBean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

