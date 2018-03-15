package cn.pjmike.xuebei.web.service;

import cn.pjmike.xuebei.domain.User;
import cn.pjmike.xuebei.domain.dto.UserPostCondition;
import org.springframework.web.bind.annotation.RequestBody;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * @author pjmike
 * @create 2018-01-29 23:20
 **/
public interface UserService {
    /**
     * 注册账号
     *
     * @param user
     * @throws UnsupportedEncodingException
     */
    boolean register(UserPostCondition user, HttpServletRequest request) throws UnsupportedEncodingException, MessagingException;

    /**
     * 激活邮箱
     *
     * @param token
     * @return
     * @throws UnsupportedEncodingException
     */
    boolean activeUser(String token) throws UnsupportedEncodingException;

    /**
     * 更新用户注册状态，state为1表示激活成功，0表示未激活或者激活失败
     *
     * @param query
     * @param update
     */
    void updateUserByState(String query, Integer update);

    /**
     * 验证用户的邮箱和密码
     *
     * @param user
     * @return
     */
    User findUser(User user);

    /**
     * 发送邮件
     *
     * @param email
     * @param request
     * @throws MessagingException
     */
    void sendMail(String email, HttpServletRequest request) throws MessagingException;

    /**
     * 修改密码
     *
     * @param user
     */
    void ChangeUserPassword(UserPostCondition user);

    /**
     * 查看用户是否存在
     *
     * @param email
     * @return
     */
    User findUserByEmail(String email);
}
