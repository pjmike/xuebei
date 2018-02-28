package cn.pjmike.xuebei.web.chat.controller;

import cn.pjmike.xuebei.domain.User;
import cn.pjmike.xuebei.web.chat.Model.GroupInfo;
import cn.pjmike.xuebei.web.chat.Model.UserGroup;
import cn.pjmike.xuebei.web.chat.Model.UserGroupInfo;
import cn.pjmike.xuebei.utils.ResponseResult;
import cn.pjmike.xuebei.web.chat.Model.UserTemp;
import cn.pjmike.xuebei.web.service.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;

/**
 * 建群
 *
 * @author pjmike
 * @create 2018-02-25 21:27
 */
@RestController
@RequestMapping("/api/groups")
public class UserGroupController {
    @Autowired
    private UserGroupService userGroupService;

    /**
     * 创建群聊
     *
     * @param groupInfo
     * @return
     */
    @PostMapping
    public ResponseResult<Object> createGroup(GroupInfo groupInfo) {
        ResponseResult<Object> result = userGroupService.createGroup(groupInfo.getUuid(), groupInfo.getNickname(), groupInfo.getUserTemps(), groupInfo.getAvatar());
        result.setCode(0);
        result.setMsg("建群成功");
        return result;
    }

    /**
     * 添加群成员
     *
     * @param groupInfo
     * @return
     */
    @PostMapping(value = "/memebers")
    public ResponseResult<Object> addGroupMemebers(GroupInfo groupInfo) {
        ResponseResult<Object> result = userGroupService.addMembers(groupInfo.getGroupId(), groupInfo.getUuid(), groupInfo.getNickname(), groupInfo.getUserTemps());
        result.setCode(0);
        result.setMsg("建群成功");
        return result;
    }
    /**
     * 面對面建群
     *
     * @param groupInfo
     * @return
     */
    @PostMapping(value = "/face")
    public ResponseResult<Object> creatGroup(@RequestBody UserGroupInfo groupInfo) {
        ResponseResult<Object> result = userGroupService.joinAroundGroup(groupInfo);
        result.setCode(0);
        result.setMsg("建群成功");
        return result;
    }

    /**
     * 修改群名称
     *
     * @param groupId
     * @param group
     * @return
     */
    @PutMapping(value = "/{groupId}/name")
    public ResponseResult<Object> updateUserGroupName(@PathVariable("groupId") String groupId, @RequestBody UserGroup group) {
        UserGroup groupResult = userGroupService.updateUserGroupName(group);
        return new ResponseResult<Object>(0, "修改群名称成功", groupResult);
    }

    /**
     * 发布群公告
     *
     * @param groupId
     * @param group
     * @return
     */
    @PutMapping(value = "/{groupId}/annoucement")
    public ResponseResult<Object> updateUserGroupAnnoucement(@PathVariable("groupId") String groupId, @RequestBody UserGroup group) {
        UserGroup groupResult = userGroupService.updateUserGroupAnnoucment(group);
        return new ResponseResult<Object>(0, "发布群公告成功", groupResult);
    }

    /**
     * 退出面对面建群
     *
     * @param uuid
     * @param groupInfo
     * @return
     */
    @DeleteMapping(value = "/{uuid}")
    public ResponseResult<Object> deleteUserGroupRelation(@PathVariable("uuid") String uuid, @RequestBody UserGroupInfo groupInfo) {
        userGroupService.quitAroudGroup(groupInfo);
        return new ResponseResult<Object>(0, "退出面对面建群成功");
    }
}
