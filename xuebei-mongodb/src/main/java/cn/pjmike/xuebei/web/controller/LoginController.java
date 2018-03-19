package cn.pjmike.xuebei.web.controller;

import cn.pjmike.xuebei.domain.dto.UserPostCondition;
import cn.pjmike.xuebei.jwt.JwtToken;
import cn.pjmike.xuebei.domain.dto.UserCondition;
import cn.pjmike.xuebei.utils.MD5Util;
import cn.pjmike.xuebei.web.exception.NullException;
import cn.pjmike.xuebei.web.exception.UserException;
import cn.pjmike.xuebei.web.service.UserService;
import cn.pjmike.xuebei.utils.ResponseResult;
import io.rong.RongCloud;
import io.rong.methods.user.User;
import io.rong.models.Result;
import io.rong.models.response.TokenResult;
import io.rong.models.user.UserModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
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
    /**
     *设置过期时间3天
     */
    private long TTLMills = 1000 * 60 * 60 * 24 * 3;
    private Logger logger = LoggerFactory.getLogger(LoginController.class);
    private ResponseResult<Object> responseResult;
    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;
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
            throw new UserException("验证码已过期");
        }
        if ((Integer)code != user.getCode()) {
            throw new UserException("验证码不匹配");
        }
        //注册用户操作
        if (!userService.register(user,request)) {
            throw new UserException("该邮箱已存在");
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
    public ResponseResult<Object> signin(@Valid @RequestBody cn.pjmike.xuebei.domain.User user) throws Exception {
        responseResult = new ResponseResult<Object>();
        //进行验证登录操作
        cn.pjmike.xuebei.domain.User result = userService.findUser(user);
        //验证密码是否正确
        String md5pwd = MD5Util.getMD5(user.getPassword());
        if (!StringUtils.equals(md5pwd, result.getPassword())) {
            throw new UserException("密码错误");
        }
        //获取用户token
        String token = JwtToken.createTokenWithTime(user.getEmail(), TTLMills);
        //进行判断，成功返回true,失败返回false
        if (result == null) {
            throw new NullException("该邮箱未注册");
        }
        System.out.println(result.getId());
        //注册融云
        //TODO
        RongCloud rongCloud = RongCloud.getInstance();
        User userRongCloud = rongCloud.user;

        //注册用户，生成用户在融云的唯一身份标识 Token
        UserModel userModel = new UserModel()
                .setId("sfsdfsdfsd")
                .setName("pjmike")
                .setPortrait("http://osvtz719h.bkt.clouddn.com/lADPBbCc1SjLjH_NAwDNAwA_768_768.jpg");
        TokenResult tokenResult = userRongCloud.register(userModel);
        System.out.println("getToken:  " + tokenResult.toString());


        //刷新用户信息方法
        Result refreshResult = userRongCloud.update(userModel);
        System.out.println("refresh:  " + refreshResult.toString());
        //将融云返回的唯一token放在redis数据库中
        ValueOperations valueOperations = redisTemplate.opsForValue();
        logger.info("user.id " + result.getId());
        logger.info("tokenResult.token :" + tokenResult.getToken());
        valueOperations.set(result.getId(), tokenResult.getToken());


        Map<String, Object> map = new HashMap<String,Object>(16);
        map.put("token", token);
        map.put("cloud_token", tokenResult.getToken());
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
            throw new UserException("邮箱不能为空");
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
            throw new UserException("验证码已过期");
        }
        if ((Integer)code != user.getCode()) {
            throw new UserException("注册码不匹配");
        }
        userService.ChangeUserPassword(user);
        return new ResponseResult<Object>(0,"修改成功");
    }

    /**
     * 验证邮箱是否被注册
     *
     * @param user
     * @return
     */
    @PostMapping(value = "/registion")
    public ResponseResult<Object> getUserByEmail(@RequestBody UserPostCondition user) {
        if (StringUtils.isBlank(user.getEmail())) {
            throw new UserException("邮箱不能为空");
        }
        if (userService.findUserByEmail(user.getEmail()) != null) {
            return new ResponseResult<Object>(1, "该邮箱已存在", null);
        } else {
            return new ResponseResult<Object>(0, "该邮箱可注册", null);
        }
    }

}
