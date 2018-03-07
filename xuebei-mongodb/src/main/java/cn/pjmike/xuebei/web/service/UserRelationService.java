package cn.pjmike.xuebei.web.service;

import cn.pjmike.xuebei.web.chat.Model.UserRelation;

/**
 * 好友关系操作
 *
 * @author pjmike
 * @create 2018-03-07 19:24
 */
public interface UserRelationService {
    /**
     * 添加好友
     *
     * @param userRelation
     */
    void addFriend(UserRelation userRelation);

    /**
     * 确认加好友
     *
     * @param userRelation
     */
    void comfirmFriend(UserRelation userRelation);

    /**
     * 拒绝加好友
     *
     * @param userUuid
     * @param friendUuid
     */
    void rejectFriend(String userUuid, String friendUuid);
}
