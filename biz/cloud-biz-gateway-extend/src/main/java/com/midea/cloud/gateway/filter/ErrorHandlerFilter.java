package com.midea.cloud.gateway.filter;

import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.component.handler.ErrorWriterHandler;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

/**
 * <pre>
 *  zuul异常处理
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-2-12 21:03
 *  修改内容:
 * </pre>
 */
@Component
public class ErrorHandlerFilter extends ZuulFilter {

    @Autowired
    ErrorWriterHandler errorWriterHandler;

    @Override
    public String filterType() {
        return FilterConstants.ERROR_TYPE;
    }

    @Override
    public int filterOrder() {
        return Integer.MIN_VALUE;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @SneakyThrows
    @Override
    public Object run() {
        HttpServletResponse response = RequestContext.getCurrentContext().getResponse();
        errorWriterHandler.writerError(response, ResultCode.RPC_ERROR, null);
        return null;
    }
}
