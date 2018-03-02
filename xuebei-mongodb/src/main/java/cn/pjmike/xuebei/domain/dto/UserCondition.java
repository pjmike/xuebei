package cn.pjmike.xuebei.domain.dto;

import java.io.Serializable;

/**
 * @author pjmike
 * @create 2018-02-27 10:43
 */
public class UserCondition implements Serializable{

    private static final long serialVersionUID = -5906625902901373947L;

    public UserCondition() {
    }

    public UserCondition(String id, String username, String icon) {
        this.id = id;
        this.username = username;
        this.icon = icon;
    }

    private String id;
    private String username;
    private String icon;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
