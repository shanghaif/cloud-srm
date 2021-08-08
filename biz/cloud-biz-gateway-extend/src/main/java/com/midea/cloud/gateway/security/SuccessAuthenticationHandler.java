package com.midea.cloud.gateway.security;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.midea.cloud.common.result.BaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * <pre>
 *  认证成功处理器
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-2-11 20:27
 *  修改内容:
 * </pre>
 */
@Component
public class SuccessAuthenticationHandler implements AuthenticationSuccessHandler {

    @Autowired
    private LoginLog loginLog;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        loginLog.saveLoginLog(SecurityContextHolder.getContext().getAuthentication().getName(), "", true);
        BaseResult baseResult = BaseResult.buildSuccess("");
        String rst = JSON.toJSONString(baseResult, SerializerFeature.WriteMapNullValue);
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_UTF8.toString());
        PrintWriter writer = response.getWriter();
        writer.write(rst);
        response.flushBuffer();
        writer.close();
    }
}
