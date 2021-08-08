package com.midea.cloud.srm.base.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <pre>
 *  邮箱服务基础配置
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
@Data
@ConfigurationProperties(prefix = "cloud.scc.mail.sender")
public class MailConfig {
    /**
     * 发送方smtp 地址
     */
    private String serverHost;
    /**
     * 发送方smtp 端口
     */
    private String serverPort;
    /**
     * 发送方smtp 性名
     */
    private String userName;
    /**
     * 发送方smtp 密码
     */
    private String userPassword;
    /**
     * 发送方邮箱 地址
     */
    private String fromAddress;

    /**
     * 是否开放发送邮件(开放-true，不开放-false)
     */
    private boolean openSendEmail;

}
