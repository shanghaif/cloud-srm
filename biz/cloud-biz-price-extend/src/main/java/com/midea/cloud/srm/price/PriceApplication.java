package com.midea.cloud.srm.price;

import com.midea.cloud.log.bind.EnableOpLog;
import com.midea.cloud.quartz.bind.EnableJobFeignSupport;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * <pre>
 *  核价模块
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-2-11 17:19
 *  修改内容:
 * </pre>
 */
@EnableFeignClients("com.midea.cloud.srm.feign")
@EnableEurekaClient
@SpringBootApplication(scanBasePackages = {"com.midea.cloud.srm.price", "com.midea.cloud.component",
        "com.midea.cloud.common","com.midea.cloud.log"})
@MapperScan(basePackages = {"com.midea.cloud.srm.price.**.mapper"})
@EnableOpLog
@EnableJobFeignSupport
@Slf4j
public class PriceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PriceApplication.class, args);
    }

}
