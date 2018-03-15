package cn.pjmike.xuebei.web.chat.controller;

import cn.pjmike.xuebei.utils.ResponseResult;
import cn.pjmike.xuebei.web.chat.Model.GroupRelation;
import cn.pjmike.xuebei.web.service.GroupRelationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author pjmike
 * @create 2018-02-28 21:47
 */
@RestController
@RequestMapping("/groups/users")
@Api(value = "群成员相关操作")
public class GroupRelationController {
    @Autowired
    private GroupRelationService groupRelationService;
    private ResponseResult<Object> result;

    /**
     * 修改群昵称,需要传群昵称，用户id,群id
     *
     * @param id
     * @param groupRelation
     * @return
     */
    @PutMapping(value = "/{id}/alias")
    @ApiOperation(value = "修改群昵称")
    public ResponseResult<Object> changeGroupAlias(@PathVariable("id") String id,@RequestBody GroupRelation groupRelation) {
        Map<String, String> map = new HashMap<String, String>(16);
        map.put("groupName", groupRelationService.changeGroupAlias(groupRelation).getAlias());
        return new ResponseResult<Object>(0, "修改成功",map );
    }

    /**
     * 主动退群，要求参数:用户id,群id
     *
     * @param id
     * @param relation
     * @return
     */
    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "主动退群")
    public ResponseResult<Object> leaveGroup(@PathVariable("id") String id, @RequestBody GroupRelation relation) {
        return groupRelationService.leaveGroup(relation);
    }

    /**
     * 设置群管理员,要求参数:用户id，群id
     *
     * @param groupRelation
     * @return
     */
    @PutMapping(value = "/{id}/manager")
    @ApiOperation(value = "设置群管理员")
    public ResponseResult<Object> setManager(@PathVariable("id") String id,@RequestBody GroupRelation groupRelation) {
        groupRelationService.setManager(groupRelation);
        return new ResponseResult<Object>(0, "设置管理员成功");
    }

    /**
     * 取消管理员,要求参数:用户id，群id
     *
     * @param groupRelation
     * @return
     */
    @DeleteMapping(value = "/{id}/manager")
    @ApiOperation(value = "取消管理员")
    public ResponseResult<Object> deleteManager(@PathVariable("id") String id,@RequestBody GroupRelation groupRelation) {
        groupRelationService.delManager(groupRelation);
        return new ResponseResult<Object>(0, "取消管理员成功");
    }
}
