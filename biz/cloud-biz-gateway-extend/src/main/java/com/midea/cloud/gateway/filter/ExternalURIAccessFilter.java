package com.midea.cloud.gateway.filter;

import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.SignUtil;
import com.midea.cloud.component.handler.ErrorWriterHandler;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 过滤uri<br>
 * 该类uri需要使用sign签名的方式对外开放使用，<br>
 * 为了方便拦截此场景的uri，我们自己约定一个规范，及uri中含有-anon/external<br>
 * 如给门户提供的订单同步接口/base-anon/external/externalOrder不能做登录拦截，但是要验证sign<br>
 * 如果有此类场景的uri，请用这种命名格式，
 *
 * @author artifact
 */
@Component
public class ExternalURIAccessFilter extends ZuulFilter implements InitializingBean {

    @Autowired
    ErrorWriterHandler errorWriterHandler;

    @Value("${cloud.scc.sign.appId}")
    private String[] appIds;

    @Value("${cloud.scc.sign.appSec}")
    private String[] appSecs;

    private Map<String, String> apps = new HashMap<String, String>();

    @SneakyThrows
    @Override
    public Object run() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        HttpServletResponse response = requestContext.getResponse();

        String sign = request.getHeader("sign"); // 获取签名
        String appId = request.getHeader("appId"); // 获取应用标识
        String timestamp = request.getHeader("timestamp"); // 获取时间戳
        if (StringUtils.isBlank(sign) || StringUtils.isBlank(appId) || StringUtils.isBlank(timestamp)) {
            errorWriterHandler.writerError(response, ResultCode.MISSING_SERVLET_REQUEST_PARAMETER, null);
            return null;
        }
        String signBase = SignUtil.getMD5sign(appId, apps.get(appId), timestamp);
        if (!sign.equals(signBase)) {
            errorWriterHandler.writerError(response, ResultCode.AUTH_ERROR, null);
        }
        return null;
    }

    @SneakyThrows
    @Override
    public boolean shouldFilter() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        return PatternMatchUtils.simpleMatch("*-anon/external*", request.getRequestURI());
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        int index = 0;
        for (String appId : appIds) {
            apps.put(appIds[index], appSecs[index++]);
        }
    }

}
