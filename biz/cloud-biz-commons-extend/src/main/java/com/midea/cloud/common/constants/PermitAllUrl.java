package com.midea.cloud.common.constants;

import java.util.ArrayList;
import java.util.List;

/**
 * 需要放开权限的url
 *
 * @author artifact
 */
public final class PermitAllUrl {

    /**
     * 监控中心和swagger需要访问的url
     */
    private static final String[] ENDPOINTS = {"/health", "/env", "/metrics", "/trace", "/dump", "/jolokia", "/info",
            "/logfile", "/refresh", "/flyway", "/liquibase", "/heapdump", "/loggers", "/auditevents", "/v2/api-docs/**", "/swagger-ui.html", "/swagger-resources/**", "/webjars/**", "/actuator/**"};

    /**
     * 需要放开权限的url
     *
     * @param urls 自定义的url
     * @return 自定义的url和监控中心需要访问的url集合
     */
    public static String[] permitAllUrl(String... urls) {
        if (urls == null || urls.length == 0) {
            return ENDPOINTS;
        }

        List<String> list = new ArrayList<>();
        for (String url : ENDPOINTS) {
            list.add(url);
        }
        for (String url : urls) {
            list.add(url);
        }

        return list.toArray(new String[list.size()]);
    }

}
