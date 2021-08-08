package com.midea.cloud.srm.base.config;

import com.midea.cloud.common.annotation.ApiLogAop;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <pre>
 * 注入接口日志代理
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-27 09:24:20
 *  修改内容:
 * </pre>
 */
@Configuration
public class ApiLogConfig {

    @Bean
    public ApiLogAop getApiLogAop(){
        return new ApiLogAop();
    }
}
