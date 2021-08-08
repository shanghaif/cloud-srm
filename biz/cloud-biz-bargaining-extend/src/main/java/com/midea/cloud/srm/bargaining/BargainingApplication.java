package com.midea.cloud.srm.bargaining;

import java.util.ArrayList;
import java.util.List;

import com.midea.cloud.log.bind.EnableOpLog;
import com.midea.cloud.quartz.bind.EnableJobFeignSupport;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 *  招投标模块
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
@SpringBootApplication(scanBasePackages = {"com.midea.cloud.srm.bargaining", "com.midea.cloud.component","com.midea.cloud.common","com.midea.cloud.log"})
@MapperScan(basePackages = { "com.midea.cloud.srm.bargaining.**.mapper" })
@EnableOpLog
@EnableJobFeignSupport
@Slf4j
public class BargainingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BargainingApplication.class, args);
    }

        /**
     * 引入公用配置属性文件
     * @return
     */
	@Bean
	public PropertyPlaceholderConfigurer properties() {
		final PropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertyPlaceholderConfigurer();
		propertyPlaceholderConfigurer.setIgnoreResourceNotFound(true);
		final List<Resource> resourceLst = new ArrayList<Resource>();
		//@Value("${spring.profiles.active}")用这个无效
		String currentProfilesActive =System.getProperty("spring.profiles.active");
		if( currentProfilesActive==null || "".equals(currentProfilesActive) ) {
			currentProfilesActive ="local";
		}
		resourceLst.add(new ClassPathResource("common-"+currentProfilesActive+".properties"));
		log.info("add resource "+"common-"+currentProfilesActive+".properties");
		propertyPlaceholderConfigurer.setLocations(resourceLst.toArray(new Resource[] {}));
		propertyPlaceholderConfigurer.setIgnoreUnresolvablePlaceholders(true);
		propertyPlaceholderConfigurer.setOrder(100);
		return propertyPlaceholderConfigurer;
	}
}
