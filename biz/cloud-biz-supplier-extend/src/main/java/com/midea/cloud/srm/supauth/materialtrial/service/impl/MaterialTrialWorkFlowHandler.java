package com.midea.cloud.srm.supauth.materialtrial.service.impl;

import com.midea.cloud.common.enums.ApproveStatusType;
import com.midea.cloud.common.enums.flow.CbpmOperationTypeEnum;
import com.midea.cloud.common.workflow.WorkFlowFunctionHandler;
import com.midea.cloud.srm.model.supplierauth.materialtrial.entity.MaterialTrial;
import com.midea.cloud.srm.supauth.materialtrial.service.IMaterialTrialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
 *  修改日期: 2020/8/10 10:53
 *  修改内容:
 * </pre>
 */
@Slf4j
@Component
@Transactional(rollbackFor = Exception.class)
public class MaterialTrialWorkFlowHandler extends WorkFlowFunctionHandler {

    @Autowired
    private IMaterialTrialService materialTrialService;


    @Override
    public Map<String, Object> getFormData(Long businessId, Map<String, Object> mapEventData) throws Exception {
        log.info(this.getClass()+"获取表单事件");

        Map<String, Object> formDataMap = new HashMap<>();
        if (null != businessId) {
            MaterialTrial materialTrial = materialTrialService.getById(businessId);
            formDataMap.put("number", materialTrial.getTrialNumber());
            formDataMap.put("createdFullName", materialTrial.getCreatedBy());
        }
        return formDataMap;
    }

    @Override
    public Map<String, Object> draftEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        Map<String, Object> result = new HashMap<>();
        return result;
    }

    @Override
    public String draftSubmitEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        log.info(this.getClass()+"起草提交事件");

        String result = "fail";
        if (null != businessId) {
            log.info("物料试用进入流程提交回调事件");
            MaterialTrial materialTrial = materialTrialService.getById(businessId);
            return getParamAndUpdateStatus(mapEventData, ApproveStatusType.SUBMITTED, materialTrial)?"success" : "fail";
        }
        return result;
    }

    @Override
    public String processEndEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        log.info(this.getClass()+"流程结束事件");
        String result = "fail";
        if (null != businessId) {
            log.info("物料试用进入流程结束回调事件");
            MaterialTrial materialTrial = materialTrialService.getById(businessId);
            materialTrialService.updateCataLogAfterFlow(materialTrial);
            return getParamAndUpdateStatus(mapEventData, ApproveStatusType.APPROVED, materialTrial) ?
                    "success" : "fail";
        }

        return result;
    }

    @Override
    public String draftAbandonEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        if (businessId == null) {
            return "fail";
        }
        MaterialTrial source = materialTrialService.getById(businessId);
        return getParamAndUpdateStatus(mapEventData, ApproveStatusType.ABANDONED, source) ?
                "success" : "fail";
    }

    @Override
    public String handleRefuseEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        if (businessId == null) {
            return "fail";
        }
        MaterialTrial source = materialTrialService.getById(businessId);
        return getParamAndUpdateStatus(mapEventData, ApproveStatusType.SUBMITTED, source) ?
                "success" : "fail";
    }


    @Override
    public String activityStartEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        String operationtype = String.valueOf(mapEventData.get("operationtype"));
        MaterialTrial source = materialTrialService.getById(businessId);
        if (CbpmOperationTypeEnum.HANDLER_REFUSE.getKey().equals(operationtype)) { //驳回到起草人节点
            return getParamAndUpdateStatus(mapEventData, ApproveStatusType.REJECTED, source) ? "success" : "fail";
        }
        return "success";
    }


    private Boolean getParamAndUpdateStatus(Map<String, Object> mapEventData, ApproveStatusType type, MaterialTrial source) {
        if (source != null) {
            source.setApproveStatus(type.getValue());
            Map<String, Object> bodyMap = (null != mapEventData.get("body") ? (Map<String, Object>) mapEventData.get("body") : null);
            if (bodyMap != null && null != bodyMap.get("processParam")) {
                Map<String, Object> processParamMap = (Map<String, Object>) bodyMap.get("processParam");
                source.setCbpmInstaceId(String.valueOf(processParamMap.get("processId")))
                        .setLastUpdateDate(new Date())
                        .setLastUpdatedBy(String.valueOf(processParamMap.get("handlerId")))
                        .setLastUpdatedByIp(String.valueOf(processParamMap.get("handlerIp")));
                materialTrialService.updateById(source);
            }
            return true;
        }
        return false;
    }
}
