package top.huahaizhi.onlyu.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by 海智 on 2017/7/9.
 */
@DatabaseTable
public class ResultBean {

    /**
     * id : 277
     * hitokoto : 一起去看星星吧。
     * type : b
     * from : 未来日记
     * creator : lies
     * cearted_at : null
     */
    @DatabaseField
    private String id;
    @DatabaseField
    private String hitokoto;
    @DatabaseField
    private String type;
    @DatabaseField
    private String from;
    @DatabaseField
    private String creator;
    @DatabaseField
    private String cearted_at;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHitokoto() {
        return hitokoto;
    }

    public void setHitokoto(String hitokoto) {
        this.hitokoto = hitokoto;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCearted_at() {
        return cearted_at;
    }

    public void setCearted_at(String cearted_at) {
        this.cearted_at = cearted_at;
    }
}
