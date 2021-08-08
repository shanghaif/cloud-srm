package com.midea.cloud.gernator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * <pre>
 * 代码生成器启动类
 * </pre>
 *
 * @author linsb@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-1-11 17:19
 *  修改内容:
 *  </pre>
 */
@Slf4j
@EnableEurekaClient
@SpringBootApplication(scanBasePackages = {"com.midea.cloud.gernator"})
public class GernatorApplication{

    public static void main(String[] args) {
        SpringApplication.run(GernatorApplication.class, args);
    }

}
