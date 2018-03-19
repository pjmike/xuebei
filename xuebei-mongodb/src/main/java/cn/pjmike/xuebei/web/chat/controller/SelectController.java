package cn.pjmike.xuebei.web.chat.controller;

import cn.pjmike.xuebei.domain.User;
import cn.pjmike.xuebei.utils.ResponseResult;
import cn.pjmike.xuebei.web.chat.Model.UserTemp;
import cn.pjmike.xuebei.web.service.SelectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索接口
 *
 * @author pjmike
 * @create 2018-03-19 22:32
 */
@RestController
public class SelectController {
    @Autowired
    private SelectService selectService;
    @GetMapping(value = "/user/{name}")
    public ResponseResult<Object> selectUserByName(@PathVariable("name") String name) {
        List<User> users = selectService.selectUserByLike(name);
        List<UserTemp> userTemps = new ArrayList<UserTemp>();
        for (User user : users) {
            UserTemp userTemp = new UserTemp();
            userTemp.setId(user.getId());
            userTemp.setAlias(user.getUsername());
            userTemp.setAvatar(user.getIcon());
            userTemps.add(userTemp);
        }
        return new ResponseResult<Object>(0, "搜索成功", userTemps);
    }
}
