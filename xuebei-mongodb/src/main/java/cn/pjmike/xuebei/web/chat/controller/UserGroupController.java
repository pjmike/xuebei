package cn.pjmike.xuebei.web.chat.controller;

import cn.pjmike.xuebei.domain.User;
import cn.pjmike.xuebei.web.chat.Model.GroupInfo;
import cn.pjmike.xuebei.web.chat.Model.UserGroup;
import cn.pjmike.xuebei.web.chat.Model.UserGroupInfo;
import cn.pjmike.xuebei.utils.ResponseResult;
import cn.pjmike.xuebei.web.chat.Model.UserTemp;
import cn.pjmike.xuebei.web.service.UserGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 建群
 *
 * @author pjmike
 * @create 2018-02-25 21:27
 */
@RestController
@RequestMapping("/groups")
@Api(value = "建群操作")
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
    @ApiOperation(value = "创建群聊")
    public ResponseResult<Object> createGroup(@RequestBody GroupInfo groupInfo) throws Exception {
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
    @PostMapping(value = "/members")
    @ApiOperation(value = "添加群成员")
    public ResponseResult<Object> addGroupMemebers(@RequestBody GroupInfo groupInfo) throws Exception {
        if (groupInfo.getUserTemps() == null) {
            return new ResponseResult<Object>(1, "未添加群成员");
        }
        ResponseResult<Object> result = userGroupService.addMembers(groupInfo.getGroupId(),groupInfo.getGroupName(), groupInfo.getUuid(), groupInfo.getNickname(), groupInfo.getUserTemps());
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
    @ApiOperation(value = "面对面建群")
    public ResponseResult<Object> creatGroup(@RequestBody UserGroupInfo groupInfo) throws Exception {
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
    @ApiOperation(value = "修改群名称")
    public ResponseResult<Object> updateUserGroupName(@PathVariable("groupId") String groupId, @RequestBody UserGroup group) {
        UserGroup groupResult = userGroupService.updateUserGroupName(group);
        Map<String, String> map = new HashMap<String, String>(16);
        map.put("groupName", groupResult.getGroupName());
        return new ResponseResult<Object>(0, "修改群名称成功",map);
    }

    /**
     * 发布群公告
     *
     * @param groupId
     * @param group
     * @return
     */
    @PutMapping(value = "/{groupId}/annoucement")
    @ApiOperation(value = "发布群公告")
    public ResponseResult<Object> updateUserGroupAnnoucement(@PathVariable("groupId") String groupId, @RequestBody UserGroup group) {
        UserGroup groupResult = userGroupService.updateUserGroupAnnoucment(group);
        Map<String, String> map = new HashMap<String, String>(16);
        map.put("annoucement", groupResult.getGroupName());
        return new ResponseResult<Object>(0, "发布群公告成功", map);
    }

    /**
     * 退出面对面建群
     *
     * @param uuid
     * @param groupInfo
     * @return
     */
    @DeleteMapping(value = "/face/users/{uuid}")
    @ApiOperation(value = "退出面对面建群")
    public ResponseResult<Object> deleteUserGroupRelation(@PathVariable("uuid") String uuid, @RequestBody UserGroupInfo groupInfo) throws Exception {
        userGroupService.quitAroudGroup(groupInfo);
        return new ResponseResult<Object>(0, "退出面对面建群成功");
    }
}
