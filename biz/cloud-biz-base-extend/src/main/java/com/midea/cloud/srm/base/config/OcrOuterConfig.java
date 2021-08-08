package com.midea.cloud.srm.base.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author tanjl11@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/18 11:04
 *  修改内容:
 * </pre>
 */
@ConfigurationProperties(prefix = "cloud.scc.ocr.outer")
@Data
@ConditionalOnExpression("${cloud.scc.ocr.outer.enable:true}")
@Configuration
public class OcrOuterConfig {
    private boolean enable;
    private String appId;
    private String appKey;
    private String url;
}
