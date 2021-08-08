package com.midea.cloud.gateway.security;

import com.midea.cloud.common.utils.IPUtil;
import com.midea.cloud.srm.feign.log.LogClient;
import com.midea.cloud.srm.feign.oauth.Oauth2Client;
import com.midea.cloud.srm.feign.rbac.RbacClient;
import com.midea.cloud.srm.model.log.trace.dto.UserTraceInfoDto;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.CompletableFuture;

/**
 * <pre>
 *  登录日志
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-2-11 20:37
 *  修改内容:
 * </pre>
 */
@Component
@Slf4j
public class LoginLog {

    @Autowired
    private Oauth2Client oauth2Client;

    @Resource
    private RbacClient rbacClient;

    /**
     * 保存用户登录痕迹
     */
    public void saveUserTrace(String username) {
        // 获取Request对象
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 获取用户的真是IP
        String ipAddr = IPUtil.getRemoteIpAddr(request);
        // 异步
        CompletableFuture.runAsync(() -> {
            try {
                LoginAppUser byUsername = rbacClient.findByUsername(username);
                UserTraceInfoDto userTraceInfoDto = new UserTraceInfoDto();
                userTraceInfoDto.setUsername(username);
                userTraceInfoDto.setLogIp(ipAddr);
                userTraceInfoDto.setUserType(byUsername.getUserType());
                rbacClient.saveUserTrace(userTraceInfoDto);
            } catch (Exception e) {
                log.error("保存用户登录痕迹异常{}用户:" + username + ",报错信息:" + e);
            }
        });
    }

    /**
     * 保存用户登录痕迹
     */
    public void updateUserTrace(String accessToken) {
        // 获取Request对象
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 获取用户的真是IP
        String ipAddr = IPUtil.getRemoteIpAddr(request);
        // 异步
        CompletableFuture.runAsync(() -> {
            String userName = null;
            try {
                userName = oauth2Client.getUserInfoByToken(accessToken).get("name").toString();
                UserTraceInfoDto userTraceInfoDto = new UserTraceInfoDto();
                userTraceInfoDto.setUsername(userName);
                userTraceInfoDto.setLogIp(ipAddr);
                rbacClient.updateUserTrace(userTraceInfoDto);
            } catch (Exception e) {
                log.error("保存用户登录痕迹异常{}用户:" + userName + ",报错信息:" + e);
            }
        });
    }

}
