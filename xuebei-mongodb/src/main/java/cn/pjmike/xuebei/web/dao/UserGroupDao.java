package cn.pjmike.xuebei.web.dao;

import cn.pjmike.xuebei.web.chat.Model.UserGroup;

/**
 * @author pjmike
 * @create 2018-02-25 22:02
 */
public interface UserGroupDao {
    /**
     * 在数据库中查看群是否已经存在
     *
     * @param password
     * @param location
     * @return
     */
    UserGroup findGroupByPwdAndLoc(Integer password, double[] location);

    /**
     * 新建群
     *
     * @param group
     */
    UserGroup insertGroup(UserGroup group);

    /**
     * 编辑群名称
     *
     * @param group
     * @return
     */
    UserGroup updateGroupName(UserGroup group);

    /**
     * 编辑群公告
     *
     * @param group
     * @return
     */
    UserGroup updateGroupAnnouncement(UserGroup group);
    UserGroup selectUserGroup();
}
