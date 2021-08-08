 package com.midea.cloud.api.interfacelog.controller;
 
 import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.alibaba.fastjson.JSON;
import com.midea.cloud.api.interfacelog.service.IInterfaceLogService;
import com.midea.cloud.srm.model.api.interfacelog.dto.InterfaceLogDTO;
 
 /**
  * Create by liping on 2018/8/20
  */
 @Aspect
 @Configuration//定义一个切面
 public class InterfaceLogAspect {
 
     private static final Logger logger = LoggerFactory.getLogger(InterfaceLogAspect.class);
     
     @Autowired
     private IInterfaceLogService iInterfaceLogService;
 
     // 定义切点Pointcut
     @Pointcut("execution(@com.midea.cloud.api.interfacelog.controller.InterfaceLog * com.midea.cloud..*(..))")
     public void excudeService() {
     }
 
     @Around("excudeService()")
     public Object around(ProceedingJoinPoint pjp) throws Throwable {
    	InterfaceLog interfaceLog = ((MethodSignature)pjp.getSignature()).getMethod().getAnnotation(InterfaceLog.class);
    	Object obj = pjp.proceed();
    	InterfaceLogDTO log = new InterfaceLogDTO();
//    	log.setBillId("单据ID");
//    	log.setBillType("接口日志查询");
    	Object[] args = pjp.getArgs();//2.传参 
    	if (null!=args && args.length > 0 ) {
         	log.setServiceInfo(JSON.toJSONString(args[0]));
     	}
    	log.setServiceName(interfaceLog.serviceName());
    	log.setType(interfaceLog.type().name());
    	log.setServiceType(interfaceLog.serviceType().name());
    	log.setTargetSys("SRM");
    	log.setStatus("SUCCESS");
    	log.setFinishDate(new Date());
    	log.setReturnInfo(JSON.toJSONString(obj));
    	log = iInterfaceLogService.createInterfaceLog(log);
    	return obj;
     }
     
     @AfterThrowing(pointcut = "excudeService()", throwing = "e")
     public void afterThrowing(JoinPoint joinPoint, Throwable e) {
    	InterfaceLog interfaceLog = ((MethodSignature)joinPoint.getSignature()).getMethod().getAnnotation(InterfaceLog.class);
     	InterfaceLogDTO log = new InterfaceLogDTO();
     	Object[] args = joinPoint.getArgs();//2.传参 
     	if (null!=args && args.length > 0 ) {
         	log.setServiceInfo(JSON.toJSONString(args[0]));
     	}
     	log.setServiceName(interfaceLog.serviceName());
    	log.setType(interfaceLog.type().name());
    	log.setServiceType(interfaceLog.serviceType().name());
     	log.setTargetSys("SRM");
     	log.setStatus("FAIL");
     	log.setErrorInfo(e.getMessage());
     	log = iInterfaceLogService.createInterfaceLog(log);
     }
 
 }