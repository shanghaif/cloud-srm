//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.midea.cloud.component.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.ExceptionUtil;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class GlobalHandleFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(GlobalHandleFilter.class);

    public GlobalHandleFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpServletHolder.setRequest((HttpServletRequest)servletRequest);
            HttpServletHolder.setResponse((HttpServletResponse)servletResponse);
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (BaseException var5) {
            this.sendError(var5.getResultCode(), var5.getMessage(), 500, (HttpServletResponse)servletResponse, var5);
        } catch (Throwable var6) {
            this.sendError(ResultCode.UNKNOWN_ERROR.getCode(), ResultCode.UNKNOWN_ERROR.getMessage(), 500, (HttpServletResponse)servletResponse, var6);
        }

    }

    @Override
    public void destroy() {
    }

    protected void sendError(String errorCode, String message, Integer httpStatusCode, HttpServletResponse response, Throwable throwable) throws IOException {
        log.error(String.format("global error info : %s", throwable.getMessage()), throwable);
        if (!response.isCommitted()) {
            String errMsg = LocaleHandler.getLocaleMsg(message);
            log.info(String.format("write global error info : %s", errMsg));
            BaseResult baseResult = BaseResult.build(errorCode, errMsg, httpStatusCode, ExceptionUtil.getErrorMsg(throwable));
            String rst = JSON.toJSONString(baseResult, new SerializerFeature[]{SerializerFeature.WriteMapNullValue});
            response.setCharacterEncoding("UTF-8");
            response.setContentType(MediaType.APPLICATION_JSON_UTF8.toString());
            PrintWriter writer = response.getWriter();
            writer.write(rst);
            response.flushBuffer();
            writer.close();
        } else {
            log.warn("response isCommitted");
        }

    }
}
