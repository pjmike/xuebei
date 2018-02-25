package cn.pjmike.xuebei.web.controller;

import cn.pjmike.xuebei.domain.UserDto;
import cn.pjmike.xuebei.web.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 关于面对面建群
 *
 * @author pjmike
 * @create 2018-02-01 15:39
 **/
@Controller
@RequestMapping("/group")
public class GroupController {
    @Autowired
    GroupService groupService;

    /**
     * 保存包含位置信息的用户信息
     *
     * @param userDto
     * @return
     */
    @PostMapping
    @ResponseBody
    public List<UserDto> saveUser(@RequestBody UserDto userDto) {
        //先保存用户信息，再找出数据库中输入密码相同且在10范围内的用户信息
        return groupService.saveAndReturnUserDto(userDto);
    }

    /**
     * 面对面建群，返回输入密码相同的进入群的群员信息
     *
     * @return
     */
    @GetMapping
    public List<UserDto> createCrowd() {
        List<UserDto> userDtos = groupService.findUserDTOByPwd();
        return userDtos;
    }
}
