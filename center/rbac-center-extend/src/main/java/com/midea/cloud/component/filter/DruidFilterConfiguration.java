package com.midea.cloud.component.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.SessionRepositoryFilter;

/**
 * 资源监控接口拦截器
 * @author lizl7
 *
 */
@Configuration
public class DruidFilterConfiguration {


    @Bean
    public FilterRegistrationBean<DruidFilter> initDruidFilter() {
        FilterRegistrationBean<DruidFilter> registration = new FilterRegistrationBean<DruidFilter>();
        DruidFilter druidFilter = new DruidFilter();
        registration.addUrlPatterns("/druid");
        registration.addUrlPatterns("/druid/*");
        registration.setFilter(druidFilter);
        registration.setOrder(SessionRepositoryFilter.DEFAULT_ORDER + 10); // 设置低于springsession的级别
        return registration;
    }

}
