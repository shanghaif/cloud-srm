package com.midea.cloud.oauth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 认证中心
 *
 * @author artifact
 */
@EnableFeignClients("com.midea.cloud.srm.feign")
@EnableEurekaClient
@SpringBootApplication(scanBasePackages = {"com.midea.cloud.oauth", "com.midea.cloud.component","com.midea.cloud.log"})
@Slf4j
public class OAuthCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(OAuthCenterApplication.class, args);
    }

}