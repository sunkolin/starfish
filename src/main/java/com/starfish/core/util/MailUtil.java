package com.starfish.core.util;

import com.starfish.core.constant.MailConstant;
import lombok.extern.slf4j.Slf4j;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * MailUtil
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2012-09-11
 */
@Slf4j
public class MailUtil {

    private MailUtil(){

    }

    public static void send(List<String> to, List<String> carbonCopy, List<String> blindCarbonCopy, String subject, String text) throws MessagingException {
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
            properties.put("mail.smtp.ssl.checkserveridentity", "true");
        }
        //auth
        Authenticator authenticator = null;
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
        InternetAddress[] tos = new InternetAddress[to.size()];
        for (int i = 0; i < to.size(); i++) {
            tos[i] = new InternetAddress(to.get(i));
        }
        message.setRecipients(Message.RecipientType.TO, tos);
        //carbonCopy
        if (carbonCopy != null && !carbonCopy.isEmpty()) {
            InternetAddress[] cc = new InternetAddress[carbonCopy.size()];
            for (int i = 0; i < carbonCopy.size(); i++) {
                cc[i] = new InternetAddress(carbonCopy.get(i));
            }
            message.setRecipients(Message.RecipientType.CC, cc);
        }
        //blindCarbonCopy
        if (blindCarbonCopy != null && !blindCarbonCopy.isEmpty()) {
            InternetAddress[] bcc = new InternetAddress[blindCarbonCopy.size()];
            for (int i = 0; i < blindCarbonCopy.size(); i++) {
                bcc[i] = new InternetAddress(blindCarbonCopy.get(i));
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
        log.info("send mail to {} success.", JsonUtil.toJson(to));
    }

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
        List<String> toList = Arrays.stream(to).collect(Collectors.toList());
        List<String> carbonCopyList = Arrays.stream(carbonCopy).collect(Collectors.toList());
        List<String> blindCarbonCopyList = Arrays.stream(blindCarbonCopy).collect(Collectors.toList());
        send(toList, carbonCopyList, blindCarbonCopyList, subject, text);
    }

}