package cn.pjmike.xuebei.web.service.Impl;

import cn.pjmike.xuebei.domain.User;
import cn.pjmike.xuebei.utils.ResponseResult;
import cn.pjmike.xuebei.web.chat.Model.UserGroup;
import cn.pjmike.xuebei.web.chat.Model.UserGroupRelation;
import cn.pjmike.xuebei.web.chat.Model.UserGroupInfo;
import cn.pjmike.xuebei.web.chat.Model.UserTemp;
import cn.pjmike.xuebei.web.dao.UserGroupDao;
import cn.pjmike.xuebei.web.dao.UserGroupRelationDao;
import cn.pjmike.xuebei.web.service.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author pjmike
 * @create 2018-02-25 21:55
 */
@Service
public class UserGroupServiceImpl implements UserGroupService{
    @Autowired
    private UserGroupDao userGroupDao;
    @Autowired
    private UserGroupRelationDao groupRelationDao;

    private ResponseResult<Object> baseResult;

    private Map<String, Object> map;
    @Override
    public synchronized ResponseResult<Object> joinAroundGroup(UserGroupInfo groupInfo) {
        baseResult = new ResponseResult<Object>();
        map = new HashMap<String, Object>(16);
        //寻找目标位置10米内是否有群存在
        UserGroup userGroup = userGroupDao.findGroupByPwdAndLoc(groupInfo.getPassword(), groupInfo.getLocation());
        if (userGroup != null) {
            //将群信息放入map中
            map.put("group", userGroup);
            //该群已存在
            //判断用户是否在群中
            if (groupRelationDao.selectByUuidAndGroupId(groupInfo.getUuid(), userGroup.getGroupId()) != null) {
                //将已在群中的用户放入map中
                map.put("users", groupRelationDao.selectGroupUser(userGroup));
                //把map放入响应结果类中
                baseResult.setData(map);
                return baseResult;
            }
            //用户不在群中，加入该群
            insertGroupUser(userGroup, groupInfo);
            //将已在群中的用户放入map中
            map.put("users", groupRelationDao.selectGroupUser(userGroup));
            //把map放入响应结果类中
            baseResult.setData(map);
            return baseResult;
        } else {
            //TODO
            //建群
            UserGroup group = new UserGroup();
            group.setPassword(groupInfo.getPassword());
            group.setOwner(groupInfo.getUuid());
            group.setLocation(groupInfo.getLocation());
            group.setGroupName("群聊");
            group.setDate(System.currentTimeMillis());
            //创建群
            UserGroup result = userGroupDao.insertGroup(group);
            //打印调试
            System.out.println(result.getGroupId());
            map.put("group", result);
            //在群成员表中添加用户
            //调用insertGroupUser方法对UserGroupRelation做增操作
            insertGroupUser(result, groupInfo);
            map.put("users", groupRelationDao.selectGroupUser(result));

            baseResult.setData(map);

            return baseResult;
        }
    }


    @Override
    public Object getAroundGroup(UserGroupInfo groupInfo) {
        //查找用户，通过uuid,password,geo信息搜索附近是否有人
        return null;
    }
    @Override
    public void quitAroudGroup(UserGroupInfo groupInfo) {
        UserGroup group = userGroupDao.findGroupByPwdAndLoc(groupInfo.getPassword(), groupInfo.getLocation());
        groupRelationDao.deleteUserGroupRelation(group.getGroupId(), groupInfo.getUuid());
    }
    @Override
    public void getGroupQRCode() {

    }
    @Override
    public UserGroup updateUserGroup(UserGroup group) {
        return userGroupDao.updateGroupName(group);
    }

    /**
     * 插入群成员关系表
     *
     * @param userGroup
     * @param groupInfo
     */
    void insertGroupUser(UserGroup userGroup,UserGroupInfo groupInfo) {
        List<UserGroupRelation> userGroupRelations = new ArrayList<UserGroupRelation>();
        Map<String, String> map = new HashMap<String, String>(16);
        //将群id,进群人id,用户名，头像信息放入一个map中
        map.put("groupId", userGroup.getGroupId());
        map.put("uuid", groupInfo.getUuid());
        map.put("alias", groupInfo.getNickname());
        map.put("avatar", groupInfo.getAvatar());
        //在将map放入一个list中，以便后来进行for循环
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        list.add(map);
        UserTemp user = new UserTemp();
        user.setId(groupInfo.getUuid());
        user.setAlias(groupInfo.getNickname());
        user.setAvatar(groupInfo.getAvatar());
        for (Map m : list) {
            UserGroupRelation userGroupRelation = new UserGroupRelation();
            userGroupRelation.setIsMember((byte) 1);
            userGroupRelation.setIsOwner((byte) 1);
            userGroupRelation.setUuid((String) m.get("uuid"));
            userGroupRelation.setAlias((String) m.get("alias"));
            userGroupRelation.setGroupId((String) m.get("groupId"));
            userGroupRelation.setUser(user);
            userGroupRelations.add(userGroupRelation);
        }
        groupRelationDao.insertBatch(userGroupRelations);
    }
}
