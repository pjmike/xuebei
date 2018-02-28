package cn.pjmike.xuebei.web.chat.controller;

import cn.pjmike.xuebei.domain.User;
import cn.pjmike.xuebei.web.chat.Model.UserGroupInfo;
import cn.pjmike.xuebei.utils.ResponseResult;
import cn.pjmike.xuebei.web.chat.Model.UserTemp;
import cn.pjmike.xuebei.web.service.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 建群
 *
 * @author pjmike
 * @create 2018-02-25 21:27
 */
@RestController
@RequestMapping("/api/userGroups")
public class UserGroupController {
    @Autowired
    private UserGroupService userGroupService;
    /**
     * 面對面建群
     *
     * @param groupInfo
     * @return
     */
    @PostMapping
    public ResponseResult<Object> creatGroup(@RequestBody UserGroupInfo groupInfo) {
        ResponseResult<Object> result = userGroupService.joinAroundGroup(groupInfo);
        result.setCode(0);
        result.setMsg("建群成功");
        return result;
    }
}
