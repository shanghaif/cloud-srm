package com.midea.cloud.gateway.filter;

import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.component.handler.ErrorWriterHandler;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 过滤uri<br>
 * 该类uri不需要登陆，但又不允许外网通过网关调用，只允许微服务间在内网调用，<br>
 * 为了方便拦截此场景的uri，我们自己约定一个规范，及uri中含有-anon/internal<br>
 * 如在oauth登陆的时候用到根据username查询用户，<br>
 * 用户系统提供的查询接口/rbac-anon/internal/findByUsername不能做登录拦截，该接口也不能对外网暴露<br>
 * 如果有此类场景的uri，请用这种命名格式，
 *
 * @author artifact
 */
@Component
public class InternalURIAccessFilter extends ZuulFilter {

    @Autowired
    ErrorWriterHandler errorWriterHandler;

    @SneakyThrows
    @Override
    public Object run() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletResponse response = RequestContext.getCurrentContext().getResponse();
        errorWriterHandler.writerError(response, ResultCode.RESOURCE_NOT_FOUND, null);
        return null;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        return PatternMatchUtils.simpleMatch("*-anon/internal*", request.getRequestURI());
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

}
