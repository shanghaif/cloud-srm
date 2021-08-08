//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.midea.cloud.component.exception;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.midea.cloud.common.enums.InvokeType;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.ExceptionUtil;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class GlobalHandlerExceptionResolver extends DefaultHandlerExceptionResolver {
    private static final Logger log = LoggerFactory.getLogger(GlobalHandlerExceptionResolver.class);

    public GlobalHandlerExceptionResolver() {
    }

    @Override
    public int getOrder() {
        return -1;
    }

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        this.logger.error(ex);

        try {
            if (ex instanceof HttpRequestMethodNotSupportedException) {
                this.sendError(ex, ResultCode.METHOD_NOT_SUPPORTED, 405, request, response);
            } else if (ex instanceof HttpMediaTypeNotSupportedException) {
                this.sendError(ex, ResultCode.MEDIA_TYPE_NOT_SUPPORTED, 415, request, response);
            } else if (ex instanceof HttpMediaTypeNotAcceptableException) {
                this.sendError(ex, ResultCode.MEDIA_TYPE_NOT_ACCEPTABLE, 406, request, response);
            } else if (ex instanceof MissingPathVariableException) {
                this.sendError(ex, ResultCode.MISSING_PATH_VARIABLE, 500, request, response);
            } else if (ex instanceof MissingServletRequestParameterException) {
                this.sendError(ex, ResultCode.MISSING_SERVLET_REQUEST_PARAMETER, 400, request, response);
            } else if (ex instanceof ServletRequestBindingException) {
                this.sendError(ex, ResultCode.SERVLET_REQUEST_BINDING, 400, request, response);
            } else if (ex instanceof ConversionNotSupportedException) {
                this.sendError(ex, ResultCode.CONVERSION_NOT_SUPPORTED, 500, request, response);
            } else if (ex instanceof TypeMismatchException) {
                this.sendError(ex, ResultCode.TYPE_MISMATCH, 400, request, response);
            } else if (ex instanceof HttpMessageNotReadableException) {
                this.sendError(ex, ResultCode.MESSAGE_NOT_READABLE, 400, request, response);
            } else if (ex instanceof HttpMessageNotWritableException) {
                this.sendError(ex, ResultCode.MESSAGE_NOT_WRITABLE, 500, request, response);
            } else if (ex instanceof MethodArgumentNotValidException) {
                this.sendError(ex, ResultCode.METHOD_ARGUMENT_NOT_VALID, 400, request, response);
            } else if (ex instanceof MissingServletRequestPartException) {
                this.sendError(ex, ResultCode.MISSING_SERVLET_REQUEST_PART, 400, request, response);
            } else if (ex instanceof BindException) {
                this.sendError(ex, ResultCode.BIND_ERROR, 400, request, response);
            } else if (ex instanceof NoHandlerFoundException) {
                this.sendError(ex, ResultCode.NO_HANDLER_FOUND, 404, request, response);
            } else if (ex instanceof AsyncRequestTimeoutException) {
                this.sendError(ex, ResultCode.ASYNC_REQUEST_TIMEOUT, 503, request, response);
            } else if (ex instanceof AccessDeniedException) {
                this.sendError(ex, ResultCode.NEED_PERMISSION, 401, request, response);
            } else if (ex instanceof AuthenticationException) {
                this.sendError(ex, ResultCode.AUTH_ERROR.getCode(), ex.getMessage(), 401, request, response);
            } else if (ex instanceof OAuth2Exception) {
                this.sendError(ex, ResultCode.AUTH_ERROR.getCode(), ex.getMessage(), 401, request, response);
            } else if (ex instanceof BaseException) {
                BaseException baseException = (BaseException)ex;
                this.sendError(ex, baseException.getResultCode(), baseException.getMessage(), 500, request, response);
            } else if (ex instanceof IllegalArgumentException) {
                this.sendError(ex, ResultCode.UNKNOWN_ERROR.getCode(), ex.getMessage(), 500, request, response);
            } else if (ex instanceof Exception) {
                this.sendError(ex, ResultCode.UNKNOWN_ERROR, 500, request, response);
            }
        } catch (Exception var6) {
            log.error("客户端响应异常", var6);
        }

        return null;
    }

    protected void sendError(Throwable throwable, ResultCode resultCode, Integer httpStatusCode, HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.sendError(throwable, resultCode.getCode(), resultCode.getMessage(), httpStatusCode, request, response);
    }

    protected void sendError(Throwable throwable, String resultCode, String message, Integer httpStatusCode, HttpServletRequest request, HttpServletResponse response) throws IOException {
        BaseResult baseResult = BaseResult.build(resultCode, LocaleHandler.getLocaleMsg(message), httpStatusCode, ExceptionUtil.getErrorMsg(throwable));
        String invokeType = request.getHeader("invokeType");
        invokeType = invokeType == null ? request.getParameter("invokeType") : invokeType;
        String rst = JSON.toJSONString(baseResult, new SerializerFeature[]{SerializerFeature.WriteMapNullValue});
        if (invokeType != null && invokeType.equals(InvokeType.INTERAL_INVOKE.getCode())) {
            response.setStatus(httpStatusCode);
        }

        if (!response.isCommitted()) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType(MediaType.APPLICATION_JSON_UTF8.toString());
            PrintWriter writer = response.getWriter();
            writer.write(rst);
            response.flushBuffer();
            writer.close();
        }

    }
}
