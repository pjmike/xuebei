package cn.pjmike.xuebei.web.chat.controller;

import cn.pjmike.xuebei.utils.ResponseResult;
import cn.pjmike.xuebei.web.chat.Model.GroupRelation;
import cn.pjmike.xuebei.web.service.GroupRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author pjmike
 * @create 2018-02-28 21:47
 */
@RestController
@RequestMapping("/api/groups/user")
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
    public ResponseResult<Object> changeGroupAlias(@PathVariable("id") String id,@RequestBody GroupRelation groupRelation) {
        return new ResponseResult<Object>(0, "修改成功", groupRelationService.changeGroupAlias(groupRelation));
    }

    /**
     * 主动退群，要求参数:用户id,群id
     *
     * @param id
     * @param relation
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public ResponseResult<Object> leaveGroup(@PathVariable("id") String id, @RequestBody GroupRelation relation) {
        return groupRelationService.leaveGroup(relation);
    }

    /**
     * 设置群管理员,要求参数:用户id，群id
     *
     * @param groupRelation
     * @return
     */
    @PutMapping(value = "/{id}/Manager")
    public ResponseResult<Object> setManager(GroupRelation groupRelation) {
        groupRelationService.setManager(groupRelation);
        return new ResponseResult<Object>(0, "设置管理员成功");
    }

    /**
     * 取消管理员,要求参数:用户id，群id
     *
     * @param groupRelation
     * @return
     */
    @DeleteMapping(value = "/{id}/Manager")
    public ResponseResult<Object> deleteManager(GroupRelation groupRelation) {
        groupRelationService.delManager(groupRelation);
        return new ResponseResult<Object>(0, "取消管理员成功");
    }
}
