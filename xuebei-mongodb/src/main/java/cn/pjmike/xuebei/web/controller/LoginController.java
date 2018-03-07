package cn.pjmike.xuebei.web.controller;

import cn.pjmike.xuebei.domain.dto.UserPostCondition;
import cn.pjmike.xuebei.jwt.JwtToken;
import cn.pjmike.xuebei.domain.User;
import cn.pjmike.xuebei.domain.dto.UserCondition;
import cn.pjmike.xuebei.web.exception.NullException;
import cn.pjmike.xuebei.web.exception.UserException;
import cn.pjmike.xuebei.web.service.UserService;
import cn.pjmike.xuebei.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * 注册登录逻辑
 *
 * @author pjmike
 * @cr@eate 2018-01-29 22:37
 **/
@Controller
@Api(value = "LoginController")
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(LoginController.class);
    private ResponseResult<Object> responseResult;
    @Autowired
    private UserService userService;
    /**
     * 用户注册
     *
     * @param user
     * @return
     * @throws UnsupportedEncodingException
     */
    @PostMapping(value = "/sign_up")
    @ResponseBody
    @ApiOperation(value = "用户注册", notes = "用户注册接口", httpMethod = "POST")
    public ResponseResult<Object> signup(@Valid @RequestBody UserPostCondition user, HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
        HttpSession session = request.getSession();
        logger.info("session的过期时间："+session.getMaxInactiveInterval());
        Object code = session.getAttribute(user.getEmail());
        if (code == null) {
            throw new NullException("验证码已过期");
        }
        if ((Integer)code != user.getCode()) {
            throw new UserException("注册码不匹配");
        }
        //注册用户操作
        if (!userService.register(user,request)) {
            throw new UserException("该邮箱已经被注册了");
        }
        return new ResponseResult<Object>(0,"注册成功");
    }

    /**
     * 用户登录
     *
     * @param user
     * @return
     * @throws UnsupportedEncodingException
     */
    @PostMapping(value = "/sign_in")
    @ResponseBody
    @ApiOperation(value = "用户登录", notes = "用户登录接口", httpMethod = "POST")
    public ResponseResult<Object> signin(@Valid @RequestBody User user) throws UnsupportedEncodingException {
        responseResult = new ResponseResult<Object>();
        //进行验证登录操作
        User result = userService.findUser(user);
        //获取用户token
        //设置过期时间3天
        long TTLMills = 1000 * 60 * 60 * 24 * 3;
        String token = JwtToken.createTokenWithTime(user.getEmail(), TTLMills);
        //进行判断，成功返回true,失败返回false
        if (result == null) {
            responseResult.setCode(1);
            responseResult.setMsg("该用户未注册");
            return responseResult;
        }
        if (result.getState() == 1) {
            responseResult.setCode(1);
            responseResult.setMsg("该用户未激活，请激活");
            return responseResult;
        }
        Map<String, Object> map = new HashMap<String,Object>(16);
        map.put("token", token);
        map.put("user", new UserCondition(result.getId(), result.getUsername(), result.getIcon()));
        responseResult.setCode(0);
        responseResult.setMsg("登录成功");
        responseResult.setData(map);
        return responseResult;
    }

    /**
     * 发送验证码
     *
     * @param userPostCondition
     * @param request
     * @return
     */
    @PostMapping(value = "/code")
    @ResponseBody
    public ResponseResult<Object> sendCodeByEmail(@RequestBody UserPostCondition userPostCondition,HttpServletRequest request) throws MessagingException {
        if (StringUtils.isBlank(userPostCondition.getEmail())) {
            throw new NullException("邮箱不能为空");
        }
        userService.sendMail(userPostCondition.getEmail(),request);
        return new ResponseResult<Object>(0,"发送成功");
    }

    /**
     * 激活用户操作
     *
     * @param token
     * @return
     * @throws UnsupportedEncodingException
     */
    @GetMapping(value = "/activation")
    @ApiOperation(value = "激活用户操作", notes = "用户激活接口", httpMethod = "GET")
    public String ActiveUser(@RequestParam("token") String token) throws UnsupportedEncodingException {
        if (token == null) {
            return "error";
        }
        //如果验证token成功，则返回成功页面，失败返回失败页面
        if (userService.activeUser(token)) {
            return "successActive";
        } else {
            return "failActive";
        }
    }

    /**
     * 修改密码
     *
     * @param user
     * @param request
     * @return
     */
    @PostMapping(value = "/reset")
    @ResponseBody
    public ResponseResult<Object> resetPwd(@RequestBody UserPostCondition user, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object code = session.getAttribute(user.getEmail());
        if (code == null) {
            throw new NullException("验证码已过期");
        }
        if ((Integer)code != user.getCode()) {
            throw new UserException("注册码不匹配");
        }
        userService.ChangeUserPassword(user);
        return new ResponseResult<Object>(0,"修改成功");
    }
}
