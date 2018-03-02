package cn.pjmike.xuebei.web.dao;

import cn.pjmike.xuebei.web.chat.Model.UserGroup;
import cn.pjmike.xuebei.web.chat.Model.GroupRelation;
import cn.pjmike.xuebei.web.chat.Model.UserTemp;
import com.mongodb.WriteResult;

import java.util.List;

/**
 * @author pjmike
 * @create 2018-02-27 11:52
 */
public interface GroupRelationDao {
    /**
     * 批量插入信息
     *
     * @param groupRelations
     */
   void insertBatch(List<GroupRelation> groupRelations);

    /**
     * 面对面建群的群员信息
     *
     * @param group
     * @return
     */
    List<UserTemp> selectGroupUser(UserGroup group);

    /**
     * 退群
     *
     * @param groupId
     * @param uuid
     */
    WriteResult deleteGroupRelationByuidAndGid(String groupId, String uuid);

    /**
     * 查询用户是否已经在某个群中
     *
     * @param uuid
     * @param groupId
     * @return
     */
    GroupRelation selectByUuidAndGroupId(String uuid, String groupId);

    /**
     * 修改群昵称
     * @param groupRelation
     */
    WriteResult changeGroupAlias(GroupRelation groupRelation);

    /**
     * 查询一个群用户数
     *
     * @param groupId
     * @return
     */
    long selectGroupRelationCount(String groupId);

    /**
     * 更新群成员的是否为管理员
     *
     * @param groupRelation
     * @return
     */
    WriteResult updateGroupRelationWithIsManager(GroupRelation groupRelation);
}
