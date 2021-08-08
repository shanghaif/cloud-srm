package com.midea.cloud.log.autoconfigure;

import com.midea.cloud.common.utils.support.EsLogAppender;
import com.midea.cloud.log.bind.EnableOpLog;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * tanjl11
 */
public class LogAutoRegistar implements ImportBeanDefinitionRegistrar {
    public static Boolean enableJobRecord = false;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        Map<String, Object> attributes = annotationMetadata
                .getAnnotationAttributes(EnableOpLog.class.getCanonicalName());
        ClassPathBeanDefinitionScanner scanConfigure =
                new ClassPathBeanDefinitionScanner(beanDefinitionRegistry, false);
        scanConfigure.addIncludeFilter(new AnnotationTypeFilter(Aspect.class));
        if ((Boolean) attributes.get("enableBizLog")) {
            scanConfigure.addIncludeFilter(new AnnotationTypeFilter(Component.class));
        }
        enableJobRecord = (boolean) attributes.get("enableJobRecord");
        if ((Boolean) attributes.get("enableEs")) {
            if (!enableJobRecord) {
                scanConfigure.addIncludeFilter(new AnnotationTypeFilter(Configuration.class));
            }
            scanConfigure.scan("com.midea.cloud.log.aop.es");
            EsLogAppender.init();
        }
        if ((Boolean) attributes.get("enableMysql")) {
            scanConfigure.scan("com.midea.cloud.log.aop.mysql");
        }

    }
}
