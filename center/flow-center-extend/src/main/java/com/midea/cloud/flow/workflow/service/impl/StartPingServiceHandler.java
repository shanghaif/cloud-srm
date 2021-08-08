package com.midea.cloud.flow.workflow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.midea.cloud.flow.common.constant.FlowCommonConst;
import com.midea.cloud.flow.workflow.service.IFlowInterfaceSetService;
import com.midea.cloud.srm.model.flow.process.entity.FlowInterfaceSet;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <pre>
 *  启动flowCenter服务之后，调用
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/27 11:40
 *  修改内容:
 * </pre>
 */
@Component
@Slf4j
public class StartPingServiceHandler implements CommandLineRunner {
    /**租户ID**/
    @Value("${work-flow.tenanId}")
    private String tenanId;

    /**工作流接口访问配置表 Service接口*/
    @Autowired
    private IFlowInterfaceSetService iFlowInterfaceSetService;

    /**cbpm工作流引擎的appId*/
    public static String APP_ID;
    /**cbpm工作流引擎的sysId*/
    public static String SYS_ID;
    /**cbpm工作流引擎的对应租户的盐值*/
    public static String SALT;

    /**门户平台相关配置*/
    /**门户的accessKeyId*/
    public static String CBPM_ACCESS_KEY_ID;
    /**门户的accessKeySecret*/
    public static String CBPM_ACCESS_KEY_SECERT;

    @Override
    public void run(String... args) throws Exception {
        log.info("flowCenter任务已经启动...");

        boolean initProcessSalt = false;
        /**新增cbpm系统接口*/
        try {
            if(StringUtils.isBlank(APP_ID) || StringUtils.isEmpty(APP_ID) || StringUtils.isBlank(SYS_ID) || StringUtils.isEmpty(SYS_ID)
                    || StringUtils.isBlank(SALT) || StringUtils.isEmpty(SALT)
                    || StringUtils.isBlank(CBPM_ACCESS_KEY_ID) || StringUtils.isEmpty(CBPM_ACCESS_KEY_ID)
                    || StringUtils.isBlank(CBPM_ACCESS_KEY_SECERT) || StringUtils.isEmpty(CBPM_ACCESS_KEY_SECERT)) {
                initProcessSalt = true;
            }
            if(initProcessSalt && null != iFlowInterfaceSetService){
                iFlowInterfaceSetService.getProcessSalt();
            }
        } catch (Exception e) {
            log.error("flowCenter任务启动,调用新增cbpm系统接口时报错：",e);
            throw new Exception("flowCenter任务启动,调用新增cbpm系统接口时报错");
        }

        try {
            if(initProcessSalt && null != iFlowInterfaceSetService) {
                /**保存之后，根据租户ID获取cbpm的salt相关信息**/
                FlowInterfaceSet queryFlowInterfaceSet = new FlowInterfaceSet();
                queryFlowInterfaceSet.setDeleteFlag(FlowCommonConst.DELETE_FLAG_NO);
                queryFlowInterfaceSet.setTenantId(tenanId);
                List<FlowInterfaceSet> flowInterfaceSetList = iFlowInterfaceSetService.list(new QueryWrapper<>(queryFlowInterfaceSet));
                if (!CollectionUtils.isEmpty(flowInterfaceSetList) && null != flowInterfaceSetList.get(0)) {
                    FlowInterfaceSet flowInterfaceSet = flowInterfaceSetList.get(0);
                    APP_ID = flowInterfaceSet.getAppId();
                    SYS_ID = flowInterfaceSet.getSysId();
                    SALT = flowInterfaceSet.getSalt();
                    CBPM_ACCESS_KEY_ID = flowInterfaceSet.getCbpmAccessKeyId();
                    CBPM_ACCESS_KEY_SECERT = flowInterfaceSet.getCbpmAccessKeySecret();
                }else{
                    throw new Exception("flowCenter任务启动,获取工作流接口访问配置表信息为空");
                }
            }
        } catch (Exception e) {
            log.info("flowCenter任务已经启动报错：",e);
            throw new Exception("flowCenter任务启动,获取工作流接口访问配置表信息时报错");
        }
        log.info("flowCenter任务已经启动结束...");
    }
}
