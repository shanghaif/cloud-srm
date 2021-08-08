package com.midea.cloud.file;

import com.midea.cloud.log.bind.EnableOpLog;
import com.midea.cloud.quartz.bind.EnableJobFeignSupport;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 文件中心
 *
 * @author artifact
 */
@EnableFeignClients("com.midea.cloud.srm.feign")
@EnableEurekaClient
@SpringBootApplication(scanBasePackages = {"com.midea.cloud.file", "com.midea.cloud.component"})
@MapperScan(basePackages = {"com.midea.cloud.file.**.mapper"})
@EnableOpLog
@EnableJobFeignSupport
@Slf4j
public class FileCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileCenterApplication.class, args);
    }

}