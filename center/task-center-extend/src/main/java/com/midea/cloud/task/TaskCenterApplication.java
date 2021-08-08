package com.midea.cloud.task;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * <pre>
 *  Pass任务调度模块
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/24
 *  修改内容:
 * </pre>
 */
@EnableFeignClients("com.midea.cloud.srm.feign")
@EnableEurekaClient
@SpringBootApplication(scanBasePackages = {"com.midea.cloud.task", "com.midea.cloud.component"})
@MapperScan(basePackages = {"com.midea.cloud.task.**.mapper"})
@Slf4j
public class TaskCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskCenterApplication.class, args);
    }

}
