package com.midea.cloud.srm.base;

import com.cloud.srm.cxf.config.EnableCxf;
import com.midea.cloud.dynamicds.bind.EnableDynamicDatasource;
import com.midea.cloud.log.bind.EnableOpLog;
import com.midea.cloud.quartz.bind.EnableQuartz;
import com.midea.cloud.ureport.bind.EnableUReport;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * <pre>
 * 基础模块
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
 *          </pre>
 */
@EnableFeignClients({"com.midea.cloud.srm.feign", "com.midea.cloud.srm.base.workflow.provider.callback"})
@EnableEurekaClient
@SpringBootApplication(scanBasePackages = {
		"com.midea.cloud.srm.base", 
		"com.midea.cloud.component", 
		"com.midea.cloud.flow",
		"com.midea.cloud.log",
		"com.midea.cloud.signature"})
@MapperScan(basePackages = {"com.midea.cloud.srm.base.**.mapper","com.midea.cloud.flow.**.mapper"})
@EnableDynamicDatasource
@EnableUReport
@EnableQuartz
@EnableOpLog
@EnableCxf
@Slf4j
public class BaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseApplication.class, args);
    }

}
