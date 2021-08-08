package com.midea.cloud.srm.rbac;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.midea.cloud.log.bind.EnableOpLog;
import com.midea.cloud.quartz.bind.EnableJobFeignSupport;

import lombok.extern.slf4j.Slf4j;

/**
 * 用户中心
 *
 * @author artifact
 */
@EnableFeignClients("com.midea.cloud.srm.feign")
@EnableEurekaClient
@SpringBootApplication(scanBasePackages = {"com.midea.cloud.srm.rbac", "com.midea.cloud.component","com.midea.cloud.common","com.midea.cloud.log"})
@MapperScan(basePackages = {"com.midea.cloud.srm.rbac.**.mapper"})
@EnableOpLog
@EnableJobFeignSupport
@Slf4j
public class RbacCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(RbacCenterApplication.class, args);
    }

}