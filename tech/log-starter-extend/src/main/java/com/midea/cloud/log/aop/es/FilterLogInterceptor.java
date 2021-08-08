package com.midea.cloud.log.aop.es;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Objects;

/**
 * @author tanjl11
 * @date 2021/01/21 10:33
 */
@Configuration
public class FilterLogInterceptor implements RequestInterceptor {
    protected static String FILTER_SIGN = "filter-log";
    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes();
        if (Objects.nonNull(attributes)) {
            HttpServletRequest request = attributes.getRequest();
            if (Objects.nonNull(request.getAttribute(FILTER_SIGN))&&(Boolean)request.getAttribute(FILTER_SIGN) ) {
                requestTemplate.header(FILTER_SIGN, "Y");
            }
        }
    }
}
