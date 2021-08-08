package com.midea.cloud.component.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * 资源监控接口拦截器
 * @author lizl7
 *
 */
@Slf4j
public class DruidFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    	HttpServletRequest httpServletRequest =(HttpServletRequest)servletRequest;
    	HttpServletResponse httpServletResponse =(HttpServletResponse) servletResponse;
    	//在特殊的url加入header，用于HttpMessageConverter判断，原文返回
    	httpServletResponse.addHeader("IS-ORIGINAL-RESPONSE", "true");

    	//判断授权信息，自行实现
//    	String druidAuth =httpServletRequest.getHeader("DRUID-AUTH");
//    	String key ="1234567890QWERTYUIOP";
//    	if( !key.equals(druidAuth) ) {
//    		httpServletResponse.getWriter().write("NO DRUID-AUTH");
//    		return;
//    	}
        //执行
        HttpServletHolder.setRequest(httpServletRequest);
        HttpServletHolder.setResponse(httpServletResponse);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }

}
