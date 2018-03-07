package cn.pjmike.xuebei.web.dao;

import cn.pjmike.xuebei.web.chat.Model.UserRelation;

/**
 * @author pjmike
 * @create 2018-03-07 19:39
 */
public interface UserRelationDao {
    /**
     * 数据库中添加好友
     *
     * @param userRelation
     */
    void addFriend(UserRelation userRelation);
}
