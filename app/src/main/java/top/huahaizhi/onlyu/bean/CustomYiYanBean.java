package top.huahaizhi.onlyu.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by haizhi on 2017/12/2.
 */
@DatabaseTable
public class CustomYiYanBean {

    @DatabaseField
    private String customString;
    @DatabaseField
    private Date date;

    public String getCustomString() {
        return customString;
    }

    public void setCustomString(String customString) {
        this.customString = customString;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
