package top.huahaizhi.onlyu.bean;

import android.graphics.Color;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by 海智 on 2017/7/8.
 */
@DatabaseTable
public class SettingsBean {
    @DatabaseField(id = true, defaultValue = "0")
    private int id = 0;
    @DatabaseField
    private int requestType = 0;
    @DatabaseField
    private int requestTime = 0;
    @DatabaseField
    private boolean saveYiYanToLocal = true;
    @DatabaseField
    private int textSize= 14;
    @DatabaseField
    private int textColor= Color.parseColor("#ffffff");
    @DatabaseField
    private int clickEvent= 0;
    @DatabaseField
    private int textGravity= 0;
    @DatabaseField
    private boolean isTextVertical= false;
    @DatabaseField
    private boolean isTextShadow= false;
    @DatabaseField
    private boolean isTextFrom= false;
    @DatabaseField
    private boolean addTextDot= false;

    public int getRequestType() {
        return requestType;
    }

    public void setRequestType(int requestType) {
        this.requestType = requestType;
    }

    public int getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(int requestTime) {
        this.requestTime = requestTime;
    }

    public boolean isSaveYiYanToLocal() {
        return saveYiYanToLocal;
    }

    public void setSaveYiYanToLocal(boolean saveYiYanToLocal) {
        this.saveYiYanToLocal = saveYiYanToLocal;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getClickEvent() {
        return clickEvent;
    }

    public void setClickEvent(int clickEvent) {
        this.clickEvent = clickEvent;
    }

    public int getTextGravity() {
        return textGravity;
    }

    public void setTextGravity(int textGravity) {
        this.textGravity = textGravity;
    }

    public boolean isTextVertical() {
        return isTextVertical;
    }

    public void setTextVertical(boolean textVertical) {
        isTextVertical = textVertical;
    }

    public boolean isTextShadow() {
        return isTextShadow;
    }

    public void setTextShadow(boolean textShadow) {
        isTextShadow = textShadow;
    }

    public boolean isTextFrom() {
        return isTextFrom;
    }

    public void setTextFrom(boolean textFrom) {
        isTextFrom = textFrom;
    }

    public boolean isAddTextDot() {
        return addTextDot;
    }

    public void setAddTextDot(boolean addTextDot) {
        this.addTextDot = addTextDot;
    }
}
