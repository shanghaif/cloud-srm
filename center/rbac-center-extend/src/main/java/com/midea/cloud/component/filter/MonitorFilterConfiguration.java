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
public class MonitorFilterConfiguration {


    @Bean
    public FilterRegistrationBean<MonitorFilter> initMonitorFilter() {
        FilterRegistrationBean<MonitorFilter> registration = new FilterRegistrationBean<MonitorFilter>();
        MonitorFilter monitorFilter = new MonitorFilter();
        registration.addUrlPatterns("/monitor");
        registration.addUrlPatterns("/monitor/*");
        registration.setFilter(monitorFilter);
        registration.setOrder(SessionRepositoryFilter.DEFAULT_ORDER + 10); // 设置低于springsession的级别
        return registration;
    }

}
