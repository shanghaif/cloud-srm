package com.midea.cloud.gateway.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <pre>
 *  认证失败处理器
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-2-11 20:34
 *  修改内容:
 * </pre>
 */
@Component
@Slf4j
public class FailureAuthenticationHandler implements AuthenticationFailureHandler {

    @Autowired
    private LoginLog loginLog;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // 获取原始异常
        Throwable throwable = exception.getCause();
        if (null == throwable) {
            throwable = exception;
        }
        String saveExceptionMsg = null;
        if(throwable instanceof BadCredentialsException) {
            saveExceptionMsg = "账号或密码错误，请重试";
        } else if (throwable instanceof DisabledException) {
            saveExceptionMsg = "账号已被禁用";
        }else {
            saveExceptionMsg = "账号异常，请稍后再试";
        }
//        loginLog.saveLoginLog(SecurityContextHolder.getContext().getAuthentication().getName(), saveExceptionMsg, false);
    }


}
