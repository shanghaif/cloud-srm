package com.midea.cloud.srm.sup;

import com.cloud.srm.cxf.config.EnableCxf;
import com.midea.cloud.log.bind.EnableOpLog;
import com.midea.cloud.quartz.bind.EnableJobFeignSupport;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.beans.Introspector;
import java.util.HashSet;
import java.util.Set;

/**
 * <pre>
 *  供应商模块
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
@ComponentScan(
        value = {"com.midea.cloud.srm.sup",
                "com.midea.cloud.component",
                "com.midea.cloud.common",
                "com.midea.cloud.srm.supauth",
                "com.midea.cloud.log"
        }, nameGenerator = SupplierApplication.RepAnnotationBeanNameGenerator.class)
@SpringBootApplication
@MapperScan(basePackages = {"com.midea.cloud.srm.sup.**.mapper", "com.midea.cloud.srm.supauth.**.mapper"},
        nameGenerator = SupplierApplication.RepAnnotationBeanNameGenerator.class)
@EnableOpLog
@EnableJobFeignSupport
@EnableCxf
@Slf4j
public class SupplierApplication {

    public static void main(String[] args) {
        SpringApplication.run(SupplierApplication.class, args);
    }

    static class RepAnnotationBeanNameGenerator extends AnnotationBeanNameGenerator {

        Set<String> beanNames = new HashSet<String>();

        public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
            if (definition instanceof AnnotatedBeanDefinition) {
                String beanName = this.determineBeanNameFromAnnotation((AnnotatedBeanDefinition)definition);
                if (StringUtils.hasText(beanName) && !beanNames.contains(beanName)) {
                    beanNames.add(beanName);
                    return beanName;
                }
            }

            return this.buildDefaultBeanName(definition, registry);
        }

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
