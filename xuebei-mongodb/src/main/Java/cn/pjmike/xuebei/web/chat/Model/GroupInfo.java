package cn.pjmike.xuebei.web.chat.Model;

import java.util.List;

/**
 * GroupController的参数提交的数据传输对象
 *
 * @author pjmike
 * @create 2018-02-28 18:11
 */
public class GroupInfo {
    private String uuid;
    private String nickname;
    private String avatar;
    private List<UserTemp> userTemps;
    private String groupId;

    public GroupInfo() {
    }

    public GroupInfo(String uuid, String nickname, String avatar, List<UserTemp> userTemps, String groupId) {
        this.uuid = uuid;
        this.nickname = nickname;
        this.avatar = avatar;
        this.userTemps = userTemps;
        this.groupId = groupId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<UserTemp> getUserTemps() {
        return userTemps;
    }

    public void setUserTemps(List<UserTemp> userTemps) {
        this.userTemps = userTemps;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
