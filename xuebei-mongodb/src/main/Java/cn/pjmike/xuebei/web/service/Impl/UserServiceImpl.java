package cn.pjmike.xuebei.web.service.Impl;

import cn.pjmike.xuebei.Jwt.JwtToken;
import cn.pjmike.xuebei.web.dao.UserDao;
import cn.pjmike.xuebei.domain.User;
import cn.pjmike.xuebei.web.service.UserService;
import cn.pjmike.xuebei.utils.MD5Util;

import com.auth0.jwt.interfaces.Claim;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
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
    public boolean register(User user) throws UnsupportedEncodingException, MessagingException {
        //查找用户之前是否注册过
        if (userDao.findUserByEmail(user.getEmail()) != null) {
            //返回false，在控制层抛出异常，该邮箱已经注册过了
            return false;
        }
        //对用户的密码进行MD5加密
        String Md5Pwd = MD5Util.getMD5(user.getPassword());
        user.setPassword(Md5Pwd);

        //将邮箱存入数据库中
        userDao.insertUser(user);
        //生成token
        String token = JwtToken.createToken(user.getEmail());

        //发送验证邮箱
        sendMail(user.getEmail(), token);
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
        String md5pwd = MD5Util.getMD5(user.getPassword());
        User result = userDao.findUserByEmailAndPassword(user.getEmail(), md5pwd);
        return result;
    }

    /**
     * 创建邮件内容，发送邮件
     *
     * @param email
     * @param token
     */
    public void sendMail(String email, String token) throws MessagingException {
        String context = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "\t<title>邮箱验证</title>\n" +
                "\t<meta charset=\"utf-8\">\n" +
                "</head>\n" +
                "<body>\n" +
                "<h3>来自学呗的激活邮件:</h3>\n" +
                "<h3>请在5分钟之内点击以下邮件进行激活:</h3>\n" +
                "\n" +
                "http://localhost:8080/activation?token=";

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        //使用一个辅助工具类，这样可以直接用set方法传入字符串类型的邮箱。
        //如果直接用mimeMessage,则需要用过InternetAddress
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
        messageHelper.setFrom(javaMailSender.getUsername());
        messageHelper.setTo(email);
        messageHelper.setSubject("来自学呗的邮箱验证:");
        //发送html格式，设置为true
        messageHelper.setText(context+token+"</body></html>",true);
        // 发送邮件
        javaMailSender.send(mimeMessage);
    }
}
