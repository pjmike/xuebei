package cn.pjmike.xuebei.web.service;

import cn.pjmike.xuebei.utils.ResponseResult;
import cn.pjmike.xuebei.web.chat.Model.UserGroupInfo;
import cn.pjmike.xuebei.web.chat.Model.UserGroup;
import cn.pjmike.xuebei.web.chat.Model.UserTemp;

import java.util.List;

/**
 * 建群
 *
 * @author pjmike
 * @create 2018-02-25 21:48
 */
public interface UserGroupService {
    /**
     * 创建群聊
     *
     * @param uuid
     * @param nickname
     * @param userTemps
     * @param avatar
     */
    ResponseResult<Object> createGroup(String uuid, String nickname, List<UserTemp> userTemps, String avatar) throws Exception;

    /**
     * 添加群成员
     *
     * @param uuid
     * @param userTemps
     */
    ResponseResult<Object> addMembers(String groupId,String groupName,String uuid,String nickname,List<UserTemp> userTemps) throws Exception;
    /**
     * 面对面建群
     *
     * @param groupInfo
     * @return
     */
    ResponseResult<Object> joinAroundGroup(UserGroupInfo groupInfo) throws Exception;

    /**
     * 获取面对面建群人消息
     *
     * @param groupInfo
     * @return
     */
    Object getAroundGroup(UserGroupInfo groupInfo);

    /**
     * 退出面对面建群，即对应界面上的返回按钮
     *
     * @param groupInfo
     */
    void quitAroudGroup(UserGroupInfo groupInfo);

    /**
     * 获取群二维码信息
     */
    void getGroupQRCode();

    /**
     * 更改群名称
     *
     * @param group
     */
    UserGroup updateUserGroupName(UserGroup group);

    /**
     * 发布群公告
     *
     * @param group
     * @return
     */
    UserGroup updateUserGroupAnnoucment(UserGroup group);
}