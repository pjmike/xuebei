package cn.pjmike.xuebei.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * @author pjmike
 * @create 2018-02-02 11:19
 **/
@Configuration
public class JavaMailSend {
    @Bean
    public JavaMailSenderImpl getJavaMailSender() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost("smtp.qq.com");
        sender.setUsername("1757752215@qq.com");
        //QQ邮箱需要授权码而不是密码
        sender.setPassword("glqksbhygdstcegd");
        Properties props = sender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.debug", "true");
        return sender;
    }
}
