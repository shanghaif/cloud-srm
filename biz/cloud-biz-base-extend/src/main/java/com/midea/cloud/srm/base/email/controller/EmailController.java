package com.midea.cloud.srm.base.email.controller;

import com.midea.cloud.srm.base.email.util.MailUtil;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 *  邮箱服务接口
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
@RestController
public class EmailController extends BaseController {

    @Autowired
    private MailUtil mailUtil;

    /**
     * 发送邮件
     */
    @PostMapping("/base-anon/internal/email/send")
    public void send(String to, String subject, String content) {
        mailUtil.sendMail(to, subject, content);
    }

}
