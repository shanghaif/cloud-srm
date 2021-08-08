package com.midea.cloud.srm.base.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <pre>
 *  短信服务基础配置
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-4-20 15:33
 *  修改内容:
 * </pre>
 */
@Data
@ConfigurationProperties(prefix = "cloud.scc.phoneMsg")
public class PhoneMsgConfig {
    /**
     * 发送短信url
     */
    private String url;

}
