package cn.pjmike.xuebei.web.chat.Model;

import cn.pjmike.xuebei.domain.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * 朋友关系
 *
 * @author pjmike
 * @create 2018-02-25 18:14
 */
@Document(collection = "groupRelation")
public class UserGroupRelation implements Serializable{

    private static final long serialVersionUID = -8199718395688170724L;
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
    private Byte isMember;

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

    public Byte getIsMember() {
        return isMember;
    }

    public void setIsMember(Byte isMember) {
        this.isMember = isMember;
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
        return "UserGroupRelation{" +
                "id='" + id + '\'' +
                ", groupId='" + groupId + '\'' +
                ", uuid='" + uuid + '\'' +
                ", user=" + user +
                ", isMember=" + isMember +
                ", isOwner=" + isOwner +
                ", alias='" + alias + '\'' +
                '}';
    }
}
