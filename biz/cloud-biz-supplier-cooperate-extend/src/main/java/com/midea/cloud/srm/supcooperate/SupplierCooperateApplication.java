package com.midea.cloud.srm.supcooperate;

import com.cloud.srm.cxf.config.EnableCxf;
import com.midea.cloud.log.bind.EnableOpLog;
import com.midea.cloud.quartz.bind.EnableJobFeignSupport;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.beans.Introspector;
import java.util.HashSet;
import java.util.Set;

/**
 * <pre>
 *  供应商协同模块
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
@SpringBootApplication
@ComponentScan(
        value = {"com.midea.cloud.srm.supcooperate",
                "com.midea.cloud.srm.pm",
                "com.midea.cloud.srm.po",
                "com.midea.cloud.srm.pr",
                "com.midea.cloud.srm.ps",
                "com.midea.cloud.component",
                "com.midea.cloud.common",
                "com.midea.cloud.log"
        }, nameGenerator = SupplierCooperateApplication.RepAnnotationBeanNameGenerator.class)
@MapperScan(basePackages = {"com.midea.cloud.srm.supcooperate.**.mapper", "com.midea.cloud.srm.po.**.mapper", "com.midea.cloud.srm.pr.**.mapper", "com.midea.cloud.srm.ps.**.mapper"})
@EnableOpLog
@EnableJobFeignSupport
@EnableCxf
@Slf4j
public class SupplierCooperateApplication {

    public static void main(String[] args) {
        SpringApplication.run(SupplierCooperateApplication.class, args);
    }

    static class RepAnnotationBeanNameGenerator extends AnnotationBeanNameGenerator {

        Set<String> beanNames = new HashSet<String>();

        @Override
        protected String buildDefaultBeanName(BeanDefinition definition) {
            String beanClassName = definition.getBeanClassName();
            Assert.state(beanClassName != null, "No bean class name set");
            String shortClassName = ClassUtils.getShortName(beanClassName);
            String decapitalizeName = Introspector.decapitalize(shortClassName);
            if (beanNames.contains(decapitalizeName)) {
                decapitalizeName = "eq" + shortClassName;
            }
            beanNames.add(decapitalizeName);
            return decapitalizeName;
        }

    }

}


