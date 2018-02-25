package cn.pjmike.xuebei.chat.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * 群信息类
 *
 * @author pjmike
 * @create 2018-02-25 0:45
 */
@Document
public class UserGroup implements Serializable{

    private static final long serialVersionUID = -5106758901091243815L;

    @Id
    private String groupId;
    /**
     * 群名称
     */
    private String groupName;
    /**
     * 群主
     */
    private String owner;
    /**
     * 群公告
     */
    private String annoucement;
    /**
     * 创建时间
     */
    private Long date;
    /**
     * 是否可用
     */
    private int status;
    /**
     * 4位密码
     */
    private Short password;
    /**
     * 位置
     */
    private String geohash;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAnnoucement() {
        return annoucement;
    }

    public void setAnnoucement(String annoucement) {
        this.annoucement = annoucement;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    @Override
    public String toString() {
        return "UserGroup{" +
                "groupId='" + groupId + '\'' +
                ", groupName='" + groupName + '\'' +
                ", owner='" + owner + '\'' +
                ", annoucement='" + annoucement + '\'' +
                ", date=" + date +
                ", status=" + status +
                ", password=" + password +
                ", geohash='" + geohash + '\'' +
                '}';
    }
}
