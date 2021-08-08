package com.midea.cloud.srm.base.email.util;


import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.srm.base.config.MailConfig;
import com.midea.cloud.srm.model.base.mail.dto.MailAuthenticator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Date;
import java.util.Properties;

/**
 * <pre>
 *  邮箱服务工具类
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-21 11:27:10
 *  修改内容:
 * </pre>
 */
@Component
@EnableConfigurationProperties(MailConfig.class)
@Slf4j
public class MailUtil {

    @Autowired
    private MailConfig mailConfig;

    private static Session sessionMail = null;

    private Session getSession() {
        if (sessionMail == null) {
            // 初始化props
            Properties properties = new Properties();
            properties.put("mail.smtp.host", mailConfig.getServerHost());
            properties.put("mail.smtp.port", mailConfig.getServerPort());
            properties.put("mail.smtp.auth", "true");
            //用于SSL
//			properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//			properties.put("mail.smtp.socketFactory.fallback", "false");
//			properties.put("mail.smtp.socketFactory.port", "25");
            // 验证
            MailAuthenticator authenticator = new MailAuthenticator(mailConfig.getUserName(), mailConfig.getUserPassword());
            // 创建session
            sessionMail = Session.getInstance(properties, authenticator);
        }
        return sessionMail;
    }

    /**
     * 以HTML格式发送邮件
     *
     * @param toEmail 接收人地址
     * @param title   标题
     * @param content 待发送的邮件信息
     * @throws Exception
     */
    public boolean sendMail(String toEmail, String title, String content) {
        /**是否开放发送邮件(开放-true，不开放-false)*/
        if(mailConfig.isOpenSendEmail()) {
            return sendMail(toEmail, title, content, null);
        }else {
            return true;
        }

    }

    /**
     * 以HTML格式发送邮件
     *
     * @param toEmail     接收人地址
     * @param title       标题
     * @param content     待发送的邮件信息
     * @param attachments 附件列表
     * @throws Exception
     */
    public boolean sendMail(
            String toEmail, String title, String content,
            String[] attachments) {
        try {
            // 根据session创建一个邮件消息
            Message mailMessage = new MimeMessage(getSession());
            // 创建邮件发送者地址
            Address from = new InternetAddress(mailConfig.getFromAddress());
//			Address from = new InternetAddress("goms_monitor_test@midea.com");

            // 设置邮件消息的发送者
            mailMessage.setFrom(from);
            // 创建邮件的接收者地址，并设置到邮件消息中
            Address to = new InternetAddress(toEmail);
            // Message.RecipientType.TO属性表示接收者的类型为TO
            mailMessage.setRecipient(Message.RecipientType.TO, to);
            // 设置邮件消息的主题
            mailMessage.setSubject(MimeUtility.encodeWord(title, "UTF-8", "Q"));
            // 设置邮件消息发送的时间
            mailMessage.setSentDate(new Date());
            // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
            Multipart mainPart = new MimeMultipart();
            // 创建一个包含HTML内容的MimeBodyPart
            MimeBodyPart html = new MimeBodyPart();
            // 设置HTML内容
            html.setContent(content, "text/html; charset=utf-8");
            mainPart.addBodyPart(html);

            FileDataSource fds;

            if (attachments != null) {
                for (int j = 0; j < attachments.length; j++) {
                    html = new MimeBodyPart();
                    fds = new FileDataSource(attachments[j]); // 得到数据源
                    html.setDataHandler(new DataHandler(fds)); // 得到附件本身并至入BodyPart
                    html.setFileName(MimeUtility.encodeText(fds.getName())); // 得到文件名同样至入BodyPart
                    mainPart.addBodyPart(html);
                }
            }

            // 将MiniMultipart对象设置为邮件内容
            mailMessage.setContent(mainPart);

            // 发送邮件
            Transport.send(mailMessage);

            return true;
        } catch (Exception ex) {
            throw new BaseException(ResultCode.UNKNOWN_ERROR, "发送邮件失败", ex);
        } finally {
            //日志记录
            log.info("发送邮件：{}\r\ntitle: " + title + "\r\ncontent: {}", toEmail, content);
        }
    }

}

