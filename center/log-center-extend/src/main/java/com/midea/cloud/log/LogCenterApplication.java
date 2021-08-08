package com.midea.cloud.log;

import java.util.ArrayList;
import java.util.List;

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
 * 日志中心
 * 
 * @author artifact
 *
 */
@EnableFeignClients("com.midea.cloud.srm.feign")
@EnableEurekaClient
@SpringBootApplication(scanBasePackages = {"com.midea.cloud.log", "com.midea.cloud.component"})
@MapperScan(basePackages = { "com.midea.cloud.log.**.mapper" })
@EnableJobFeignSupport
@Slf4j
public class LogCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogCenterApplication.class, args);
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

//	/**
//	 * jvm监控
//	 * @return
//	 */
//	@Bean(initMethod = "init", destroyMethod = "destroy")
//	public com.midea.monitor.tomcat.JvmMonitorService jvmMonitorService() {
//		log.info("init JvmMonitorService");
//		return new com.midea.monitor.tomcat.JvmMonitorService();
//	}
	
}