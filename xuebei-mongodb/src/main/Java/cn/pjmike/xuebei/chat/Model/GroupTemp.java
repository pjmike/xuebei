package cn.pjmike.xuebei.chat.Model;

import java.io.Serializable;

/**
 * @author pjmike
 * @create 2018-02-25 1:00
 */
public class GroupTemp implements Serializable{

    private static final long serialVersionUID = 472816437772958005L;

    private String groupTempId;
    /**
     * 成员编号
     */
    private String uuid;
    /**
     *  4位密码
     */
    private Short password;

    /**
     * 位置
     */
    private String geohash;

    /**
     * 时间
     */
    private Long date;

    public String getGroupTempId() {
        return groupTempId;
    }

    public void setGroupTempId(String groupTempId) {
        this.groupTempId = groupTempId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Short getPassword() {
        return password;
    }

    public void setPassword(Short password) {
        this.password = password;
    }

    public String getGeohash() {
        return geohash;
    }

    public void setGeohash(String geohash) {
        this.geohash = geohash;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }
}
