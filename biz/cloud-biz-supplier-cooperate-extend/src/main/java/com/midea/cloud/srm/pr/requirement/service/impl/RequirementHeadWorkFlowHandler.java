package com.midea.cloud.srm.pr.requirement.service.impl;
import com.midea.cloud.common.enums.flow.CbpmOperationTypeEnum;
import com.midea.cloud.common.enums.pm.pr.requirement.RequirementApplyStatus;
import com.midea.cloud.common.workflow.WorkFlowFunctionHandler;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementHead;
import com.midea.cloud.srm.pr.requirement.service.IRequirementHeadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author tanjl11@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/12 18:54
 *  修改内容:
 * </pre>
 */
@Component
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class RequirementHeadWorkFlowHandler extends WorkFlowFunctionHandler {
    @Autowired
    private IRequirementHeadService service;
    @Override
    public Map<String, Object> getFormData(Long businessId, Map<String, Object> mapEventData) throws Exception {
        log.info(this.getClass()+"获取表单事件");
        Map<String, Object> formDataMap = new HashMap();
        if (!Objects.isNull(businessId)) {
            RequirementHead sample = service.getById(businessId);
            if (!Objects.isNull(sample)) {
                formDataMap.put("number", sample.getRequirementHeadNum());
                formDataMap.put("createdFullName", sample.getCreatedBy());
            }

        }
        return formDataMap;
    }

    @Override
    public Map<String, Object> draftEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        return null;
    }

    @Override
    public String draftSubmitEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        log.info(this.getClass()+"起草提交事件");

        Boolean result = false;
        if (null != businessId) {
        RequirementHead sample = service.getById(businessId);
//        result = getParamAndUpdateStatus(mapEventData, RequirementApplyStatus.SUBMITTED, sample);
    }
        return result ? "success" : "fail";    }

    @Override
    public String processEndEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        log.info(this.getClass()+"流程结束事件");

        Boolean result = false;
        RequirementHead header = service.getById(businessId);
        if (header != null) {
//            result = getParamAndUpdateStatus(mapEventData, RequirementApplyStatus.APPROVED, header);
        }
        return result ? "success" : "fail";
    }

    @Override
    public String handleRefuseEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        if(businessId==null){
            return "fail";
        }
        RequirementHead header = service.getById(businessId);
//        return getParamAndUpdateStatus(mapEventData,RequirementApplyStatus.SUBMITTED,header)?
//                "success":"fail";
        return "fail";
    }

    @Override
    public String draftAbandonEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        if(businessId==null){
            return "fail";
        }
        RequirementHead head = service.getById(businessId);
//        return getParamAndUpdateStatus(mapEventData,RequirementApplyStatus.ABANDONED,head)?
//                "success":"fail";
        return "fail";
    }

    @Override
    public String activityStartEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        String operationtype = String.valueOf(mapEventData.get("operationtype"));
        RequirementHead source = service.getById(businessId);
        //驳回到起草人节点
        if (CbpmOperationTypeEnum.HANDLER_REFUSE.getKey().equals(operationtype)) {
            return getParamAndUpdateStatus(mapEventData, RequirementApplyStatus.REJECTED, source)?"success":"fail";
        }
        return "fail";
    }
    private Boolean getParamAndUpdateStatus(Map<String, Object> mapEventData, RequirementApplyStatus type, RequirementHead sample) {
        if (sample != null) {
            sample.setAuditStatus(type.getValue());
            Map<String, Object> bodyMap = (null != mapEventData.get("body") ? (Map<String, Object>) mapEventData.get("body") : null);
            if (bodyMap != null && null != bodyMap.get("processParam")) {
                Map<String, Object> processParamMap = (Map<String, Object>) bodyMap.get("processParam");
//                sample.setCbpmInstaceId(String.valueOf(processParamMap.get("processId")))
//                        .setLastUpdateDate(new Date())
//                        .setLastUpdatedBy(String.valueOf(processParamMap.get("handlerId")))
//                        .setLastUpdatedByIp(String.valueOf(processParamMap.get("handlerIp")));
                service.updateById(sample);
            }
            return true;
        }
        return false;
    }
}
