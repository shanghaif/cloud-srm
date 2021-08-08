package com.midea.cloud.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 网关，统一入口
 *
 * @author artifact
 */
@EnableFeignClients("com.midea.cloud.srm.feign")
@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication(scanBasePackages = {"com.midea.cloud.gateway", "com.midea.cloud.component"})
@Slf4j
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}