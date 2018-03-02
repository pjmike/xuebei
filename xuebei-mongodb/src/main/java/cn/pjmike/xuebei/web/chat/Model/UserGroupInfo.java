package cn.pjmike.xuebei.web.chat.Model;

import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;

/**
 * 创建群时用的dto类
 *
 * @author pjmike
 * @create 2018-02-25 21:51
 */
public class UserGroupInfo {
    /**
     * 建群或进群人id
     */
    private String uuid;
    /**
     * 面对面建群所加密码
     */
    private Integer password;
    /**
     * GPS位置
     */
    @GeoSpatialIndexed
    private double[] location;
    /**
     * 用户名
     */
    private String nickname;
    /**
     * 用户头像
     */
    private String avatar;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getPassword() {
        return password;
    }

    public void setPassword(Integer password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public double[] getLocation() {
        return location;
    }

    public void setLocation(double[] location) {
        this.location = location;
    }
}
