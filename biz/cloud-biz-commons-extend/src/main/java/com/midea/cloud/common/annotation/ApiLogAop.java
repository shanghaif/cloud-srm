package com.midea.cloud.common.annotation;

import com.alibaba.fastjson.JSON;
import com.midea.cloud.common.enums.api.ResultStatus;
import com.midea.cloud.common.utils.ExceptionUtil;
import com.midea.cloud.common.utils.LogWriteBack;
import com.midea.cloud.component.context.container.SpringContextHolder;
import com.midea.cloud.srm.feign.api.ApiClient;
import com.midea.cloud.srm.model.api.interfacelog.dto.InterfaceLogDTO;
import com.midea.cloud.srm.model.api.interfacelog.dto.LogWriteBackDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.ObjectUtils;

import java.util.Date;

/**
 * <pre>
 * 日志Aop
 * </pre>
 *
 * @author wangpr@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-27 09:24:20
 *  修改内容:
 * </pre>
 */
@Aspect
@Slf4j
public class ApiLogAop {

    private static ApiClient apiClient;


    @Around(value = "@annotation(com.midea.cloud.common.annotation.ApiLog)")
    public Object apiLog(ProceedingJoinPoint pjp) throws Throwable{
        Object obj ;

        InterfaceLogDTO interfaceLogDTO = new InterfaceLogDTO();
        // 设置请求参数
        setServiceInfo(pjp, interfaceLogDTO);
        //创建开始时间
        interfaceLogDTO.setCreationDateBegin(new Date());
        interfaceLogDTO.setDealTime(1L);

        // 回调方法类
        LogWriteBack logWriteBack = null;

        // 获取注解信息
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        ApiLog annotation = signature.getMethod().getAnnotation(ApiLog.class);
        if(null != annotation){
            // 设置日志基础信息
            interfaceLogDTO.setServiceName(annotation.serviceName());
            interfaceLogDTO.setServiceType(annotation.serviceType());
            interfaceLogDTO.setType(annotation.type());
            interfaceLogDTO.setBillType(annotation.billType());
            interfaceLogDTO.setTargetSys(annotation.targetSys());
            interfaceLogDTO.setUrl(annotation.url());
            logWriteBack = annotation.aClass().newInstance();
        }

        try {
            // 具体业务方法
            obj = pjp.proceed();
            // 记录返回参数
            setReturnInfo(obj, interfaceLogDTO);
            // 执行回调方法
            executeCallback(obj, interfaceLogDTO, logWriteBack);
        } catch (Exception e) {
            String errorMsg = ExceptionUtil.getErrorMsg(e);
            interfaceLogDTO.setErrorInfo(errorMsg);
            interfaceLogDTO.setStatus(ResultStatus.FAIL.name());
            throw e;
        }finally {
            try {
                interfaceLogDTO.setCreationDateEnd(new Date());
                getApiClient().createInterfaceLog(interfaceLogDTO);
            } catch (Exception e) {
                String errorMsg = ExceptionUtil.getErrorMsg(e);
                log.error(interfaceLogDTO.getServiceName()+"保存日志报错{}" + errorMsg);
            }
        }
        return obj;
    }

    public void setReturnInfo(Object obj, InterfaceLogDTO interfaceLogDTO) {
        if (!ObjectUtils.isEmpty(obj)) {
            try {
                String paramJson = JSON.toJSONString(obj);
                interfaceLogDTO.setReturnInfo(paramJson);
            } catch (Exception e) {
                String errorMsg = ExceptionUtil.getErrorMsg(e);
                interfaceLogDTO.setServiceInfo("返回参数解析报错{}" + errorMsg);
            }
        }
    }

    /**
     * 执行回调方法
     * @param obj
     * @param interfaceLogDTO
     * @param logWriteBack
     */
    public void executeCallback(Object obj, InterfaceLogDTO interfaceLogDTO, LogWriteBack logWriteBack) {
        try {
            LogWriteBackDto returnStatusInfo;
            if(null != logWriteBack){
                returnStatusInfo = logWriteBack.getReturnStatusInfo(obj);
                interfaceLogDTO.setStatus(returnStatusInfo.getStatus());
                interfaceLogDTO.setErrorInfo(returnStatusInfo.getErrorInfo());
                if (ResultStatus.SUCCESS.name().equals(returnStatusInfo.getStatus())) {
                    interfaceLogDTO.setFinishDate(new Date());
                }
            }
        } catch (Exception e) {
            String errorMsg = ExceptionUtil.getErrorMsg(e);
            interfaceLogDTO.setErrorInfo("执行回调方法报错=>"+errorMsg);
            interfaceLogDTO.setStatus(ResultStatus.FAIL.name());
        }
    }

    public void setServiceInfo(ProceedingJoinPoint pjp, InterfaceLogDTO interfaceLogDTO) {
        try {
            // 获取请求参数
            Object[] args = pjp.getArgs();
            if(!ObjectUtils.isEmpty(args)){
                String paramJson = JSON.toJSONString(args);
                interfaceLogDTO.setServiceInfo(paramJson);
            }
        } catch (Exception e) {
            String errorMsg = ExceptionUtil.getErrorMsg(e);
            interfaceLogDTO.setServiceInfo("请求参数解析报错{}" + errorMsg);
        }
    }

    private static synchronized ApiClient getApiClient(){
        if(null == apiClient){
            apiClient = SpringContextHolder.getBean(ApiClient.class);
        }
        return apiClient;
    }
}
