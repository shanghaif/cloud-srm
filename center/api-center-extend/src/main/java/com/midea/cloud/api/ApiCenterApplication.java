package com.midea.cloud.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.cloud.srm.cxf.config.EnableCxf;
import com.midea.cloud.dynamicds.bind.EnableDynamicDatasource;
import com.midea.cloud.quartz.bind.EnableJobFeignSupport;

import lombok.extern.slf4j.Slf4j;

/**
 * API接口中心
 *
 * @author artifact
 */
@EnableFeignClients("com.midea.cloud.srm.feign")
@EnableEurekaClient
@SpringBootApplication(scanBasePackages = {"com.midea.cloud.api", "com.midea.cloud.component","com.midea.cloud.common","com.midea.cloud.log"})
@MapperScan(basePackages = {"com.midea.cloud.api.**.mapper"})
@EnableDynamicDatasource
@EnableJobFeignSupport
@EnableCxf
@Slf4j
public class ApiCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiCenterApplication.class, args);
    }

}