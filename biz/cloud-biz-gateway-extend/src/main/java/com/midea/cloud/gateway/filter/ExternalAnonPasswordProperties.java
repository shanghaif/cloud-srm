package com.midea.cloud.gateway.filter;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * <pre>
 *  第三方接口调用认证接口密文
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/11/20 11:27
 *  修改内容:
 * </pre>
 */
@ConditionalOnProperty(prefix = "eureka.instance.metadata-map", name = "externalAnonPassword")
@Component
@Data
public class ExternalAnonPasswordProperties {

    @Value("${eureka.instance.metadata-map.externalAnonPassword}")
    private String externalAnonPassword;

}
