package com.midea.cloud.srm.model.base.mail.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * <pre>
 *  邮箱服务认证配置
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
@AllArgsConstructor
public class MailAuthenticator extends Authenticator {

    /**
     * 用户名（登录邮箱）
     */
    private String username;
    /**
     * 密码
     */
    private String password;

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
    }

}
