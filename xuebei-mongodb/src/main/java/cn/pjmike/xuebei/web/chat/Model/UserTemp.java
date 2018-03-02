package cn.pjmike.xuebei.web.chat.Model;

import java.io.Serializable;

/**
 * 只保存用户头像，昵称，id的类，用于群聊中
 *
 * @author pjmike
 * @create 2018-02-28 10:34
 */
public class UserTemp implements Serializable{

    private static final long serialVersionUID = 949740389254982556L;

    public UserTemp() {
    }

    public UserTemp(String id) {
        this.id = id;
    }

    public UserTemp(String id, String alias) {
        this.id = id;
        this.alias = alias;
    }

    public UserTemp(String id, String alias, String avatar) {
        this.id = id;
        this.alias = alias;
        this.avatar = avatar;
    }

    /**
     * 用户id
     */
    private String id;
    /**
     * 用户群昵称
     */
    private String alias;
    /**
     * 用户头像
     */
    private String avatar;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "UserTemp{" +
                "id='" + id + '\'' +
                ", alias='" + alias + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
