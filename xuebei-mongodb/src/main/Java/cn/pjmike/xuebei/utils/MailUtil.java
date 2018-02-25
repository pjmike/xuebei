package cn.pjmike.xuebei.utils;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * JavaMail工具类
 *
 * @author pjmike
 * @create 2018-01-29 22:37
 **/
public class MailUtil {
    private static final String SENDER = "1757752215@qq.com";
    private static final String CONTENT = "<h3>来自学呗的激活邮件:</h3>" +
            "<h3>请在5分钟之内点击以下邮件进行激活:</h3>" +
            "http://localhost:8080/ActiveUser?token=";
    /**
     * 接收者
     */
    private String token;
    private String recevier;

    public MailUtil(String recevier, String token) {
        this.recevier = recevier;
        this.token = token;
    }

    public boolean SendMail() {
        String authword = "glqksbhygdstcegd";
        //新建配置文件
        Properties prop = new Properties();
        //设置发送协议为SMTP
        prop.setProperty("mail.transport.protocol", "smtp");
        //设置发送服务器地址
        prop.setProperty("mail.smtp.host", "smtp.qq.com");
        //设置需要验证，即需提供密码（QQ邮箱需要 授权码 而非 密码， 网易邮箱可以设置用密码还是授权码）
        prop.setProperty("mail.smtp.auth", "true");
        //设置SSL安全连接相关的类，QQ邮箱经测试必须开启
        prop.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        //设置服务器端口（官方设置页有提供，QQ邮箱截为465或者587）
        prop.setProperty("mail.smtp.port", "465");
        prop.setProperty("mail.smtp.socketFactory.port", "465");
        //获取符合session实例
        Session session = Session.getDefaultInstance(prop);
        //开启Debug模式
        session.setDebug(true);
        try {
            MimeMessage mimeMessage = createMessage(session);
            Transport transport = session.getTransport();
            transport.connect(SENDER, authword);
            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
            transport.close();
            return true;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }

    }

    private MimeMessage createMessage(Session session) throws UnsupportedEncodingException, MessagingException {
        MimeMessage mimeMessage = new MimeMessage(session);
        //设置发件人，internetAddress参数:Email地址，昵称，编码
        mimeMessage.setFrom(new InternetAddress(SENDER, "XueBei", "utf-8"));
        //设置收件人,参数
        mimeMessage.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(recevier, "用户", "utf-8"));
        //设置邮件标题
        mimeMessage.setSubject("来自学呗的邮箱验证", "utf-8");
        //设置邮件内容
        mimeMessage.setContent(CONTENT + token, "text/html;charset=Utf-8");
        return mimeMessage;
    }
}
