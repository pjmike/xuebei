package cn.pjmike.xuebei.web.service.impl;

import cn.pjmike.xuebei.utils.ResponseResult;
import cn.pjmike.xuebei.web.chat.Model.UserGroup;
import cn.pjmike.xuebei.web.chat.Model.GroupRelation;
import cn.pjmike.xuebei.web.chat.Model.UserGroupInfo;
import cn.pjmike.xuebei.web.chat.Model.UserTemp;
import cn.pjmike.xuebei.web.dao.UserGroupDao;
import cn.pjmike.xuebei.web.dao.GroupRelationDao;
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
    private GroupRelationDao groupRelationDao;

    private ResponseResult<Object> baseResult;

    private Map<String, Object> map;

    @Override
    public ResponseResult<Object> createGroup(String uuid, String nickname, List<UserTemp> userTemps, String avatar) {
        baseResult = new ResponseResult<Object>();
        map = new HashMap<String, Object>(16);
        //建群
        UserGroup userGroup = new UserGroup();
        userGroup.setGroupName("群聊");
        userGroup.setDate(System.currentTimeMillis());
        userGroup.setOwner(uuid);
        //本地创群
        userGroupDao.insertGroup(userGroup);
        map.put("group", userGroup);
        //插入成员
        List<GroupRelation> groupRelationList = new ArrayList<GroupRelation>();
        //先插入群主,设置群主和管理员属性均为0
        groupRelationList.add(new GroupRelation(userGroup.getGroupId(), uuid,new UserTemp(uuid, nickname, avatar), (byte) 0, (byte) 0, nickname));
        for (UserTemp userTemp : userTemps) {
            GroupRelation groupRelation = new GroupRelation(userGroup.getGroupId(), userTemp.getId(), userTemp, (byte) 1, (byte) 1, userTemp.getAlias());
            groupRelationList.add(groupRelation);
        }
        groupRelationDao.insertBatch(groupRelationList);
        //设置响应结果类,将用户信息和群信息都返回
        map.put("users", groupRelationDao.selectGroupUser(userGroup));
        baseResult.setData(map);
        return baseResult;
    }
    @Override
    public ResponseResult<Object> addMembers(String groupId,String uuid, String nickname, List<UserTemp> userTemps) {
        baseResult = new ResponseResult<Object>();
        map = new HashMap<String, Object>(16);
        //插入成员
        List<GroupRelation> groupRelationList = new ArrayList<GroupRelation>();
        for (UserTemp userTemp : userTemps) {
            GroupRelation groupRelation = new GroupRelation(groupId, userTemp.getId(), userTemp, (byte) 1, (byte) 1, userTemp.getAlias());
            groupRelationList.add(groupRelation);
        }
        groupRelationDao.insertBatch(groupRelationList);
        map.put("users", groupRelationDao.selectGroupUser(new UserGroup(groupId)));
        //设置邀请人信息
        map.put("inviter", new UserTemp(uuid, nickname));
        baseResult.setData(map);
        return baseResult;
    }

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
        groupRelationDao.deleteGroupRelationByuidAndGid(group.getGroupId(), groupInfo.getUuid());
    }
    @Override
    public void getGroupQRCode() {

    }
    @Override
    public UserGroup updateUserGroupName(UserGroup group) {
        return userGroupDao.updateGroupName(group);
    }
    @Override
    public UserGroup updateUserGroupAnnoucment(UserGroup group) {
        return userGroupDao.updateGroupAnnouncement(group);
    }

    /**
     * 插入群成员关系表
     *
     * @param userGroup
     * @param groupInfo
     */
    void insertGroupUser(UserGroup userGroup,UserGroupInfo groupInfo) {
        List<GroupRelation> groupRelations = new ArrayList<GroupRelation>();
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
            GroupRelation groupRelation = new GroupRelation();
            groupRelation.setIsManager((byte) 1);
            groupRelation.setIsOwner((byte) 1);
            groupRelation.setUuid((String) m.get("uuid"));
            groupRelation.setAlias((String) m.get("alias"));
            groupRelation.setGroupId((String) m.get("groupId"));
            groupRelation.setUser(user);
            groupRelations.add(groupRelation);
        }
        groupRelationDao.insertBatch(groupRelations);
    }
}
