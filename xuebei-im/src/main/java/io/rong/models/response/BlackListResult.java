package io.rong.models.response;

import io.rong.models.Result;
import io.rong.models.UserInfo;
import io.rong.util.GsonUtil;

/**
 * @author RongCloud
 */
public class BlackListResult extends Result {
    /**
     * 黑名单用户列表
     */
    UserInfo[] userInfos;

    public BlackListResult(Integer code, String errorMessage) {
        super(code, errorMessage);
    }

    public BlackListResult(Integer code, String msg, UserInfo[] userInfos) {
        super(code, msg);
        this.userInfos = userInfos;
    }
    /**
     * 获取users
     *
     * @return UserInfo[]
     */
    public UserInfo[] getUserInfos() {
        return this.userInfos;
    }
    /**
     * 设置users
     *
     */
    public void setUserInfos(UserInfo[] userInfos) {
        this.userInfos = userInfos;
    }

    @Override
    public String toString() {
        return GsonUtil.toJson(this, BlackListResult.class);
    }

}
