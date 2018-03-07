package cn.pjmike.xuebei.web.chat.Model;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 好友关系类
 *
 * @author pjmike
 * @create 2018-03-07 19:19
 */
@Document(collection = "userRelations")
public class UserRelation {
    /**
     * 用户id
     */
    private String userUuid;
    /**
     * 用户名
     */
    private String userAlias;
    /**
     * 头像
     */
    private String userAatar;
    /**
     * 朋友id
     */
    private String friendUuid;
    /**
     * 附加消息
     */
    private String msg;
    /**
     * 备注
     */
    private String remark;
    /**
     * 朋友昵称
     */
    private String friendAlias;
    /**
     * 朋友头像
     */
    private String friendAvatar;
    /**
     * 添加好友时的状态,好友添加成功为0，被拒绝为1,默认为1
     */
    private int status = 1;
    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public String getFriendUuid() {
        return friendUuid;
    }

    public void setFriendUuid(String friendUuid) {
        this.friendUuid = friendUuid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFriendAlias() {
        return friendAlias;
    }

    public void setFriendAlias(String friendAlias) {
        this.friendAlias = friendAlias;
    }

    public String getFriendAvatar() {
        return friendAvatar;
    }

    public void setFriendAvatar(String friendAvatar) {
        this.friendAvatar = friendAvatar;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {

        this.msg = msg;
    }

    public String getUserAlias() {
        return userAlias;
    }

    public void setUserAlias(String userAlias) {
        this.userAlias = userAlias;
    }

    public String getUserAatar() {
        return userAatar;
    }

    public void setUserAatar(String userAatar) {
        this.userAatar = userAatar;
    }
}
