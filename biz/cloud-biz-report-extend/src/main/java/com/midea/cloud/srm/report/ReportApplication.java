package com.midea.cloud.srm.report;

import com.midea.cloud.log.bind.EnableOpLog;
import com.midea.cloud.quartz.bind.EnableJobFeignSupport;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * <pre>
 *  报表模块
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/11/18 18:55
 *  修改内容:
 * </pre>
 */
@EnableFeignClients("com.midea.cloud.srm.feign")
@EnableEurekaClient
@SpringBootApplication(scanBasePackages = {"com.midea.cloud.srm.report", "com.midea.cloud.component","com.midea.cloud.log"})
@MapperScan(basePackages = {"com.midea.cloud.srm.report.**.mapper"})
@EnableOpLog
@EnableJobFeignSupport
public class ReportApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReportApplication.class, args);
    }

}
