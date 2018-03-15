package cn.pjmike.xuebei.web.chat.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * 群成员信息表
 *
 * @author pjmike
 * @create 2018-02-25 18:14
 */
@Document(collection = "groupRelation")
public class GroupRelation implements Serializable{

    private static final long serialVersionUID = -8199718395688170724L;

    public GroupRelation() {
    }

    public GroupRelation(String groupId, String uuid, UserTemp user, Byte isManager, Byte isOwner, String alias) {
        this.groupId = groupId;
        this.uuid = uuid;
        this.user = user;
        this.isManager = isManager;
        this.isOwner = isOwner;
        this.alias = alias;
    }

    @Id
    private String id;
    /**
     * 群id
     */
    private String groupId;
    /**
     * 群成员id
     */

    private String uuid;
//    @DBRef
    private UserTemp user;
    /**
     * 是否是管理员
     */
    private Byte isManager;

    /**
     * 是否是群主
     */
    private Byte isOwner;
    /**
     * 群昵称
     */
    private String alias;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Byte getIsManager() {
        return isManager;
    }

    public void setIsManager(Byte isManager) {
        this.isManager = isManager;
    }

    public Byte getIsOwner() {
        return isOwner;
    }

    public void setIsOwner(Byte isOwner) {
        this.isOwner = isOwner;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {

        this.groupId = groupId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserTemp getUser() {
        return user;
    }

    public void setUser(UserTemp user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "GroupRelation{" +
                "id='" + id + '\'' +
                ", groupId='" + groupId + '\'' +
                ", uuid='" + uuid + '\'' +
                ", userRongCloud=" + user +
                ", isManager=" + isManager +
                ", isOwner=" + isOwner +
                ", alias='" + alias + '\'' +
                '}';
    }
}
