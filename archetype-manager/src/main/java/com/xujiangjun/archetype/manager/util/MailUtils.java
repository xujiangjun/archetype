package com.xujiangjun.archetype.manager.util;

import com.sun.mail.util.MailSSLSocketFactory;
import com.xujiangjun.archetype.exception.BusinessException;
import com.xujiangjun.archetype.enums.ResponseEnum;
import lombok.extern.slf4j.Slf4j;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Properties;

/**
 * 邮件发送工具类
 * http://www.imooc.com/video/14266
 * http://www.runoob.com/java/java-sending-email.html
 *
 * @author xujiangjun
 * @since 2018.05.20
 */
@Slf4j
public class MailUtils {

    /** 邮件发送者 **/
    private static final String from = "269875504@qq.com";

    /** 邮件发送者密码 **/
    private static final String password = "";

    /** smtp邮件服务器 **/
    private static final String host = "smtp.qq.com";

    /**
     * 发送邮件
     *
     * @param toUser   目标用户
     * @param subject  邮件主题
     * @param content  邮件正文
     */
    public static void sendMail(String toUser, String subject, String content){
        // 1.创建连接对象，连接到邮箱服务器
        Properties props = new Properties();
        props.setProperty("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");
        setMailSSL(props);
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // 使用时请替换邮箱账号和密码
                return new PasswordAuthentication(from, password);
            }
        });

        // 2.创建默认的MimeMessage对象
        try {
            MimeMessage message = new MimeMessage(session);
            // 2.1设置发件人
            message.setFrom(new InternetAddress(from));
            // 2.2设置收件人
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(toUser));
            // 2.3设置邮件主题
            message.setSubject(subject);
            // 2.4设置邮件正文
            message.setContent(content, "text/html;charset=UTF-8");
            // 2.5发送邮件
            Transport.send(message);
        } catch (MessagingException e) {
            String msg = new StringBuilder("邮件发送失败，toUser:").append(toUser).append(", subject:").append(subject)
                    .append(", content:").append(content).toString();
            log.error(msg, e);
            throw new BusinessException(ResponseEnum.MAIL_SEND_FAIL);
        }
    }

    /**
     * 163邮箱可以不用设置SSL，但是QQ邮箱必须设置SSL加密
     *
     * @param props props
     */
    private static void setMailSSL(Properties props){
        try {
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtp.ssl.socketFactory", sf);
        } catch (GeneralSecurityException e) {
            throw new BusinessException(ResponseEnum.SYSTEM_ERROR, "初始化MailSSLSocketFactory异常");
        }
    }

    /**
     * 发送注册激活邮件
     *
     * @param toUser 注册用户
     * @param code 激活码 - 通过UUID产生，去除连字符
     */
    public static void sendActivateMail(String toUser, String code){
        String subject = "来自Archetype网站的激活邮件";
        String content = "<h1>来自Archetype网站的激活邮件, 激活请点击以下链接：</h1>" +
                "<h3><a href='http://localhost:8080/archetype/active?code=" + code + "'>" +
                "http://localhost:8080/archetype/active?code=" + code + "</a></h3>";
        sendMail(toUser, subject, content);
    }
}
