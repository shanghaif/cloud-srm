package com.midea.cloud.gateway.filter;

import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.pre.ServletDetectionFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * <pre>
 *  文件上传请求标识过滤器
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-3-5 15:03
 *  修改内容:
 * </pre>
 */
//@Component
public class UploadFileHandlerFilter extends ServletDetectionFilter {

    @Value("${zuul.servlet-path:/zuul}")
    private String zuulServletPath;

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        if (!(request instanceof HttpServletRequestWrapper)
                && this.isDispatcherServletRequest(request)
                && !this.isMultipartContent(request)) {
            ctx.set("isDispatcherServletRequest", true);
        } else {
            request.setAttribute("javax.servlet.include.request_uri", zuulServletPath + request.getServletPath());
            ctx.set("isDispatcherServletRequest", false);
        }

        return null;
    }

    private boolean isDispatcherServletRequest(HttpServletRequest request) {
        return request.getAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE) != null;
    }

    /**
     * 判断是否是multipart/form-data请求
     * @param request 请求
     * @return 是否是
     */
    private boolean isMultipartContent(HttpServletRequest request) {
        String requesType = "post";
        if(!requesType.equals(request.getMethod().toLowerCase())) {
            return false;
        }
        //获取Content-Type
        String contentType = request.getContentType();
        return (contentType != null) && (contentType.toLowerCase().startsWith("multipart/"));
    }

}
