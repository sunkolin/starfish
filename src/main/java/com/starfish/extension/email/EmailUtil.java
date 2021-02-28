package com.starfish.extension.email;

import com.alibaba.fastjson.JSON;
import com.starfish.constant.MailConstant;
import lombok.extern.slf4j.Slf4j;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;

/**
 * Mails
 *
 * @author sunny
 * @since 2012-9-11
 */
@Slf4j
public class EmailUtil {

    /**
     * 发送邮件
     *
     * @param to              收件人
     * @param carbonCopy      抄送人
     * @param blindCarbonCopy 密送人
     * @param subject         主题
     * @param text            内容
     * @throws MessagingException 发送失败抛出异常
     */
    public static void send(String[] to, String[] carbonCopy, String[] blindCarbonCopy, String subject, String text) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", MailConstant.HOST);
        properties.put("mail.smtp.port", MailConstant.PORT);
        properties.put("mail.smtp.timeout", MailConstant.TIMEOUT);
        //open ssl
        if (MailConstant.SSL) {
            // set mail.smtp.starttls.enable=false when use 126.com
            properties.put("mail.smtp.starttls.enable", "false");
            properties.put("mail.smtp.socketFactory.fallback", "false");
            properties.setProperty("mail.smtp.port", MailConstant.PORT);
            properties.put("mail.smtp.socketFactory.port", MailConstant.PORT);
            properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        }
        //auth
        Authenticator authenticator;
        if (MailConstant.AUTH) {
            properties.put("mail.smtp.auth", true);
            authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(MailConstant.USERNAME, MailConstant.PASSWORD);
                }
            };
        }
        Session session = Session.getInstance(properties, authenticator);
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(MailConstant.FROM));
        //to
        InternetAddress[] tos = new InternetAddress[to.length];
        for (int i = 0; i < to.length; i++) {
            tos[i] = new InternetAddress(to[i]);
        }
        message.setRecipients(Message.RecipientType.TO, tos);
        //carbonCopy
        if (carbonCopy != null && carbonCopy.length > 0) {
            InternetAddress[] cc = new InternetAddress[carbonCopy.length];
            for (int i = 0; i < carbonCopy.length; i++) {
                cc[i] = new InternetAddress(carbonCopy[i]);
            }
            message.setRecipients(Message.RecipientType.CC, cc);
        }
        //blindCarbonCopy
        if (blindCarbonCopy != null && blindCarbonCopy.length > 0) {
            InternetAddress[] bcc = new InternetAddress[blindCarbonCopy.length];
            for (int i = 0; i < blindCarbonCopy.length; i++) {
                bcc[i] = new InternetAddress(blindCarbonCopy[i]);
            }
            message.setRecipients(Message.RecipientType.BCC, bcc);
        }
        message.setSubject(subject);
        message.setSentDate(new Date());
        Multipart multipart = new MimeMultipart();
        BodyPart bodyPart = new MimeBodyPart();
        bodyPart.setContent(text, "text/html; charset=utf-8");
        multipart.addBodyPart(bodyPart);
        message.setContent(multipart);
        Transport.send(message);
        log.info("send mail to {} success.", JSON.toJSONString(to));
    }

}