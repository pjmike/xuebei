package cn.pjmike.xuebei.web.dao;

import cn.pjmike.xuebei.domain.User;
import cn.pjmike.xuebei.web.chat.Model.UserGroup;
import cn.pjmike.xuebei.web.chat.Model.UserGroupInfo;
import cn.pjmike.xuebei.web.chat.Model.UserGroupRelation;
import cn.pjmike.xuebei.web.chat.Model.UserTemp;
import com.mongodb.WriteResult;

import java.util.List;

/**
 * @author pjmike
 * @create 2018-02-27 11:52
 */
public interface UserGroupRelationDao {
    /**
     * 批量插入信息
     *
     * @param groupRelations
     */
   void insertBatch(List<UserGroupRelation> groupRelations);

    /**
     * 面对面建群的群员信息
     *
     * @param group
     * @return
     */
    List<UserTemp> selectGroupUser(UserGroup group);

    /**
     * 退出面对面建群
     *
     * @param groupId
     * @param uuid
     */
    WriteResult deleteUserGroupRelation(String groupId, String uuid);

    /**
     * 查询用户是否已经在某个群中
     *
     * @param uuid
     * @param groupId
     * @return
     */
    UserGroupRelation selectByUuidAndGroupId(String uuid,String groupId);
}
