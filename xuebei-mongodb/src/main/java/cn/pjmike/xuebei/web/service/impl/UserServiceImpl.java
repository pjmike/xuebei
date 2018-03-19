package cn.pjmike.xuebei.web.service.impl;

import cn.pjmike.xuebei.domain.dto.UserPostCondition;
import cn.pjmike.xuebei.jwt.JwtToken;
import cn.pjmike.xuebei.web.dao.UserDao;
import cn.pjmike.xuebei.domain.User;
import cn.pjmike.xuebei.web.exception.UserException;
import cn.pjmike.xuebei.web.service.UserService;
import cn.pjmike.xuebei.utils.MD5Util;

import com.auth0.jwt.interfaces.Claim;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @author pjmike
 * @create 2018-01-29 23:22
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private JavaMailSenderImpl javaMailSender;

    @Override
    public boolean register(UserPostCondition user, HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
        //查找用户之前是否注册过
        if (userDao.findUserByEmail(user.getEmail()) != null) {
            //返回false，在控制层抛出异常，该邮箱已经注册过了
            return false;
        }
        //对用户的密码进行MD5加密
        String Md5Pwd = MD5Util.getMD5(user.getPassword());
        user.setPassword(Md5Pwd);


        //将邮箱存入数据库中
        userDao.insertUser(new User(user.getEmail(),user.getPassword()));
        return true;
    }

    @Override
    public boolean activeUser(String token) throws UnsupportedEncodingException {
        //token验证
        //验证token是否过期
        if (JwtToken.verifyTokenTime(token)) {
            return false;
        }
        Map<String, Claim> map = JwtToken.verifyToken(token);
        //通过token拿到的email去数据库中查看是否有用户存在
        User user = userDao.findUserByEmail(map.get("email").asString());
        if (user != null) {
            //将用户state的状态改为0,state为1表示未激活，0表示激活成功
            updateUserByState(user.getEmail(), 0);
            return true;
        }
        return false;
    }

    @Override
    public void updateUserByState(String query, Integer update) {
        //查询条件
        Query query1 = new Query(Criteria.where("email").is(query));
        //更新内容
        Update update1 = new Update().set("state", update);
        userDao.updateUser(query1, update1);
    }

    @Override
    public User findUser(User user) {
        User result = userDao.findUserByEmail(user.getEmail());
        return result;
    }

    /**
     * 创建邮件内容，发送邮件
     *
     * @param email
     */
    @Override
    public void sendMail(String email,HttpServletRequest request) throws MessagingException {
        int randCode = (int) ((Math.random() * 9 + 1) * 100000);
        String context = "【学呗】您的邮箱验证码为: " + randCode + ",该验证码10分钟内有效";
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        //使用一个辅助工具类，这样可以直接用set方法传入字符串类型的邮箱。
        //如果直接用mimeMessage,则需要用过InternetAddress
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
        messageHelper.setFrom(javaMailSender.getUsername());
        messageHelper.setTo(email);
        messageHelper.setSubject("【学呗】"+randCode+"是您的注册验证码");
        HttpSession session = request.getSession();
        session.setAttribute(email,randCode);
        //设置session过期时间为15分钟
        session.setMaxInactiveInterval(10*60);
        //发送html格式，设置为true
        messageHelper.setText(context);
        // 发送邮件
        javaMailSender.send(mimeMessage);
    }
    @Override
    public void ChangeUserPassword(UserPostCondition user) {
        //查询条件
        Query query = Query.query(Criteria.where("email").is(user.getEmail()));
        //更新条件
        Update update = new Update();
        update.set("password", MD5Util.getMD5(user.getPassword()));
        userDao.updateUser(query, update);
    }

    @Override
    public User findUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }
}
