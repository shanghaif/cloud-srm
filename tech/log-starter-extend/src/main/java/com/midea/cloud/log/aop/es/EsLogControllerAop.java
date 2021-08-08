package com.midea.cloud.log.aop.es;

import com.alibaba.fastjson.JSON;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.IPUtil;
import com.midea.cloud.common.utils.NamedThreadFactory;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.common.utils.support.EsLogAppender;
import com.midea.cloud.component.context.container.SpringContextHolder;
import com.midea.cloud.log.autoconfigure.LogAutoRegistar;
import com.midea.cloud.srm.feign.log.LogClient;
import com.midea.cloud.srm.model.log.useroperation.entity.LogDocument;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.MimeHeaders;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author tanjl11
 * @date 2020/12/18 16:29
 */
@Slf4j
@Aspect
public class EsLogControllerAop {
    @Value("${spring.application.name}")
    private String model;

    @Resource
    private EsLogHandler esLogHandler;
    
    private final ThreadPoolExecutor ioThreadPool;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public EsLogControllerAop() {
        int cpuCount = Runtime.getRuntime().availableProcessors();
        ioThreadPool = new ThreadPoolExecutor(cpuCount * 2 + 1, cpuCount * 2 + 1,
                0, TimeUnit.SECONDS, new LinkedBlockingQueue(),
                new NamedThreadFactory("日志中心-send-log", true), new ThreadPoolExecutor.CallerRunsPolicy());
    }


    //@Around("@within(org.springframework.web.bind.annotation.RestController)||@within(javax.jws.WebService)")
    @Around("@within(org.springframework.web.bind.annotation.RestController)")
    public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        LogDocument document = new LogDocument();
        Long startTime = System.currentTimeMillis();
        Object proceed = null;
        try {
            // 获取Request对象
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
             if (!LogAutoRegistar.enableJobRecord) {
                String jobPath = "job-anon";
                //定时任务及相关链路不记录
                if (request.getRequestURI().contains(jobPath) || Objects.equals(request.getHeader(FilterLogInterceptor.FILTER_SIGN), "Y")) {
                    String name = Thread.currentThread().getName();
                    log.info("thread-name"+name+"url:"+request.getRequestURI()+"不走记录");
                    request.setAttribute(FilterLogInterceptor.FILTER_SIGN, true);
                    proceed = joinPoint.proceed();
                    //用完需要去掉
                    request.removeAttribute(FilterLogInterceptor.FILTER_SIGN);
                    return proceed;
                }
            }
//            LoginAppUser loginAppUser = Optional.ofNullable(AppUserUtil.getLoginAppUser())
//                    .orElse(
//                            (LoginAppUser) new LoginAppUser()
//                                    .setUsername("内部feign调用")
//                                    .setNickname("内部feign调用")
//                                    .setUserType("system")
//                    );
             
             LoginAppUser loginAppUser =AppUserUtil.getLoginAppUser();
             if( loginAppUser==null ) {
            	 loginAppUser =new LoginAppUser();
            	 loginAppUser.setUsername("内部feign调用");
            	 loginAppUser.setNickname("内部feign调用");
            	 loginAppUser.setUserType("system");
             }

            // 操作人
            document.setUsername(StringUtil.getValue(loginAppUser.getUsername(), "内部feign调用"));
            // 操作人账号
            document.setNickname(StringUtil.getValue(loginAppUser.getNickname(), "内部feign调用"));
            // 用户类型
            document.setUserType(StringUtil.getValue(loginAppUser.getUserType(), "system"));


            MethodSignature signature = (MethodSignature) joinPoint.getSignature();

            // 操作名称
            document.setMethodName(signature.getName());

            String now = dateTimeFormatter.format(LocalDateTime.now());
            document.setOperationTime(now);
            // 请求开始时间
            document.setRequestStartTime(now);
            // 请求IP
            document.setRequestIp(IPUtil.getRemoteIpAddr(request));
            // URL
            document.setRequestUrl(request.getRequestURI());
            // 模块
            document.setModel(model);
            Object[] args;
            // 请求参数
            args = joinPoint.getArgs();
            args = Arrays.stream(args).filter(e -> !(e instanceof ServletRequest || e instanceof ServletResponse)).toArray();
            String param = JSON.toJSONString(args);
            document.setRequestParam(param);

        } catch (Exception e) {
            log.error("截取参数异常:" + e);
        }

        try {
            proceed = joinPoint.proceed();
            document.setResultStatus("success");
            return proceed;
        } catch (Exception e) {
            // 堆栈错误信息
            String stackTrace = Arrays.toString(e.getStackTrace());
            // 错误信息
            String message = e.getMessage();
            Map<String, String> errorMsg = new HashMap<>();
            errorMsg.put("message", e.getClass().getName() + ": " + message);
            errorMsg.put("stackTrace", stackTrace);
            document.setErrorInfo(JSON.toJSONString(errorMsg));
            document.setResultStatus("error");
            throw e;
        } finally {
        	//保证不会影响正常业务
        	try {
        		 if (StringUtil.notEmpty(proceed)) {
                     // 返回结果 result too large to recorded
                     String responseResult = JSON.toJSONString(proceed);
                     //长度大于10w不记录
//                     if(responseResult.length()>100000){
//                         responseResult="The response result is too large to record,length:"+responseResult.length();
//                     }
                     //大于4000进行截取
                     if(responseResult.length()>4000){
                         responseResult=responseResult.substring(0,4000);
                     }
                     document.setResponseResult(responseResult);
                 }
                 document.setRequestEndTime(dateTimeFormatter.format(LocalDateTime.now()));
                 document.setResponseDate(System.currentTimeMillis() - startTime);
                 // 异步将操作日志保存
                 document.setLogInfo(EsLogAppender.get().toString());
                 
                 esLogHandler.saveLog(document);
                 
//                 EsLogAppender.clear();
//                 try {
//                     // 异步将操作日志保存
//                     CompletableFuture.runAsync(() -> {
//                         try {
//                         	//本工程记录
//                         	//log.info(document.toString());
//                         	esLogHandler.saveLog(document);
//                         } catch (Exception e2) {
//                             log.error("用户操作日记记录异常:{}" + e2, e2);
//                         }
//                     }, ioThreadPool);
//                 } catch (Exception e) {
//                     log.error("保存日志异常{}" + e);
//                 }
			} catch (Exception e2) {
				log.error(e2.getMessage());
			}
           
        }
    }
}
