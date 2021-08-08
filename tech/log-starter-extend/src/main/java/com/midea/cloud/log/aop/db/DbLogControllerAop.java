package com.midea.cloud.log.aop.db;

import com.alibaba.fastjson.JSON;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.ExceptionUtil;
import com.midea.cloud.common.utils.IPUtil;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.srm.feign.log.LogClient;
import com.midea.cloud.srm.model.log.biz.entity.BizOperateLogInfo;
import com.midea.cloud.srm.model.log.biz.holder.BizDocumentInfoHolder;
import com.midea.cloud.srm.model.log.useroperation.entity.UserOperation;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

/**
 * <pre>
 *
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/6/11
 *  修改内容:
 * </pre>
 */
//@Aspect
@Slf4j
public class DbLogControllerAop {
    @Value("${spring.application.name}")
    private String model;
    @Resource
    private LogClient logClient;

    // 成功状态
    private static final String success = "success";
    // 失败状态
    private static final String error = "error";
    // 是否只记录错误日志
    public static final boolean IS_ONLY_RECORD_ERROR = true;

    @Around("@within(org.springframework.web.bind.annotation.RestController)")
    public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        Object proceed = null;
        UserOperation userOperation = new UserOperation();
        long startTime = 0;
        try {
            // 当前时间
            LocalDateTime now = LocalDateTime.now();
            // 获取Request对象
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            // 获取当前用户信息
            setLoginUserInfo(userOperation);
            // 设置请求参数
            setServiceInfo(joinPoint, userOperation);
            // 设置接口基础信息
            startTime = getApiBaseInfo(joinPoint, now, userOperation, startTime, request);
        } catch (Exception e) {
            String errorMsg = ExceptionUtil.getErrorMsg(e);
            log.error("设置日子基础信息异常:{}" + errorMsg);
        }

        try {
            // 执行具体业务
            proceed = joinPoint.proceed();
            userOperation.setResultStatus(success);
            // 设置返回参数
            setReturnInfo(proceed, userOperation);
        } catch (Exception e) {
            String errorMsg = ExceptionUtil.getErrorMsg(e);
            userOperation.setErrorInfo(errorMsg);
            userOperation.setResultStatus(error);
            throw e;
        }

        try {
            // 保存日志
            if (IS_ONLY_RECORD_ERROR) {
                if (error.equals(userOperation.getResultStatus())) {
                    saveLog(userOperation, startTime);
                }
            }else {
                saveLog(userOperation, startTime);
            }
        } catch (Exception e) {
            String errorMsg = ExceptionUtil.getErrorMsg(e);
            log.error("保存日志异常:{}" + errorMsg);
        }

        return proceed;
    }

    public void saveLog(UserOperation userOperation, long startTime) {
        // 请求结束时间
        Date endDate = new Date();
        userOperation.setRequestEndTime(endDate);
        userOperation.setResponseDate(endDate.getTime() - startTime);
        BizDocumentInfoHolder.clear();
        // 异步将操作日志保存
        CompletableFuture.runAsync(() -> {
            try {
//                        logClient.save(userOperation);
            } catch (Exception e2) {
                log.error("用户操作日记记录异常:{}" + ExceptionUtil.getErrorMsg(e2));
            }
        });
    }

    public long getApiBaseInfo(ProceedingJoinPoint joinPoint, LocalDateTime now, UserOperation userOperation, long startTime, HttpServletRequest request) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 操作名称
        userOperation.setMethodName(signature.getName());
        // 操作时间
        Date date = new Date();
        startTime = date.getTime();
        userOperation.setOperationTime(date);
        // 请求开始时间
        userOperation.setRequestStartTime(date);
        // 请求IP
        userOperation.setRequestIp(IPUtil.getRemoteIpAddr(request));
        // URL
        userOperation.setRequestUrl(request.getRequestURI());
        // 模块
        userOperation.setModel(model);
        //业务操作日志复用用户信息
        businessInfoLog(userOperation, now);
        return startTime;
    }

    public void setLoginUserInfo(UserOperation userOperation) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if (null != loginAppUser) {
            // 操作人
            userOperation.setUsername(StringUtil.getValue(loginAppUser.getUsername(), "内部feign调用"));
            // 操作人账号
            userOperation.setNickname(StringUtil.getValue(loginAppUser.getNickname(), "内部feign调用"));
            // 用户类型
            userOperation.setUserType(StringUtil.getValue(loginAppUser.getUserType(), "system"));
        } else {
            // 操作人
            userOperation.setUsername("内部feign调用");
            // 操作人账号
            userOperation.setNickname("内部feign调用");
            // 用户类型
            userOperation.setUserType("system");
        }
    }

    private void businessInfoLog(UserOperation userOperation, LocalDateTime now) {
        //业务日志属性记录
        BizOperateLogInfo info = BizDocumentInfoHolder.get();
        info.setModel(model);
        info.setOperateTime(now);
        info.setUsername(userOperation.getUsername());
        info.setNickname(userOperation.getNickname());
        info.setUserType(userOperation.getUserType());
        info.setOperateIp(userOperation.getRequestIp());

    }

    public void setServiceInfo(ProceedingJoinPoint pjp, UserOperation userOperation) {
        try {
            // 获取请求参数
            Object[] args = pjp.getArgs();
            if(!ObjectUtils.isEmpty(args)){
                String paramJson = JSON.toJSONString(args);
                userOperation.setRequestParam(paramJson);
            }
        } catch (Exception e) {
            String errorMsg = ExceptionUtil.getErrorMsg(e);
            userOperation.setRequestParam("请求参数解析报错{}" + errorMsg);
        }
    }

    public void setReturnInfo(Object obj, UserOperation userOperation) {
        if (!ObjectUtils.isEmpty(obj)) {
            try {
                String paramJson = JSON.toJSONString(obj);
                userOperation.setResponseResult(paramJson);
            } catch (Exception e) {
                String errorMsg = ExceptionUtil.getErrorMsg(e);
                userOperation.setResponseResult("返回参数解析报错{}" + errorMsg);
            }
        }
    }
}
