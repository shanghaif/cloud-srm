package com.midea.cloud.srm.inq.price.service.impl;

import com.midea.cloud.common.enums.ApproveStatusType;
import com.midea.cloud.common.enums.flow.CbpmOperationTypeEnum;
import com.midea.cloud.common.workflow.WorkFlowFunctionHandler;
import com.midea.cloud.srm.inq.price.service.IApprovalHeaderService;
import com.midea.cloud.srm.model.inq.price.entity.ApprovalHeader;
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
 * @author linxc6@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-4-14 9:36
 *  修改内容:
 * </pre>
 */
@Component
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class ApprovalWorkFlowHandler extends WorkFlowFunctionHandler {

    @Autowired
    private IApprovalHeaderService iApprovalHeaderService;

    @Override
    public Map<String, Object> getFormData(Long businessId, Map<String, Object> mapEventData) throws Exception {
        log.info("进入价格审批单formData");
        Map<String, Object> formDataMap = new HashMap<>();
        if(null != businessId) {
            ApprovalHeader approvalHeader = iApprovalHeaderService.getById(businessId);
            formDataMap.put("number", approvalHeader.getApprovalNo());
            formDataMap.put("createdBy", approvalHeader.getCreatedBy());
        }
        return formDataMap;
    }

    @Override
    public Map<String, Object> draftEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        log.info("价格审批单审批流程开始draftEvent");
        if(null == businessId) {
            return null;
        }

        ApprovalHeader approvalHeader = iApprovalHeaderService.getById(businessId);
        if (approvalHeader != null) {
//            Map<String, Object> bodyMap = (null != mapEventData.get("body") ? (Map<String, Object>) mapEventData.get("body") : null);
            String processId = String.valueOf(mapEventData.get("processId"));
//            if(bodyMap!= null && null != bodyMap.get("processParam")) {
//                Map<String, Object> processParamMap = (Map<String, Object>) bodyMap.get("processParam");
                approvalHeader.setCbpmInstaceId(String.valueOf(mapEventData.get("processId")));
                approvalHeader.setLastUpdatedBy(String.valueOf(mapEventData.get("handlerId")));
                approvalHeader.setLastUpdatedByIp(String.valueOf(mapEventData.get("handlerIp")));
                approvalHeader.setLastUpdateDate(new Date());
                iApprovalHeaderService.updateById(approvalHeader);
//            }
        }
        log.info("项目管理流程结束draftEvent");
        return null;
    }

    /**
     * 起草人提交
     */
    @Override
    public String draftSubmitEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        log.info("价格审批单起草人提交开始draftSubmitEvent");
        String result = "fail";
        if (businessId == null) {
            return result;
        }
        ApprovalHeader approvalHeader = iApprovalHeaderService.getById(businessId);
        if (approvalHeader == null) {
            return result;
        }
        approvalHeader.setStatus(ApproveStatusType.SUBMITTED.getValue());

        Map<String, Object> bodyMap = (null != mapEventData.get("body") ? (Map<String, Object>) mapEventData.get("body") : null);
        if (bodyMap!= null && null != bodyMap.get("processParam")) {
            Map<String, Object> processParamMap = (Map<String, Object>) bodyMap.get("processParam");
            approvalHeader.setCbpmInstaceId(String.valueOf(processParamMap.get("processId")));
            approvalHeader.setLastUpdatedBy(String.valueOf(processParamMap.get("handlerId")));
            approvalHeader.setLastUpdateDate(new Date());
            approvalHeader.setLastUpdatedByIp(String.valueOf(processParamMap.get("handlerIp")));
            iApprovalHeaderService.updateById(approvalHeader);
            result = "success";
        }
        log.info("价格审批单起草人提交结束draftSubmitEvent");
        return result;
    }

    /**
     * 流程结束（审批通过）
     */
    @Override
    public String processEndEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        log.info("价格审批单流程结束（审批通过）开始processEndEvent");
        String result = "fail";
        if (businessId == null) {
            return result;
        }
        ApprovalHeader approvalHeader = iApprovalHeaderService.getById(businessId);
        if (approvalHeader == null) {
            return result;
        }

        Map<String, Object> bodyMap = (null != mapEventData.get("body") ? (Map<String, Object>) mapEventData.get("body") : null);
        if(null != bodyMap && null != bodyMap.get("processParam")) {
            Map<String, Object> processParamMap = (Map<String, Object>) bodyMap.get("processParam");
            approvalHeader.setCbpmInstaceId(String.valueOf(processParamMap.get("processId")));
            approvalHeader.setLastUpdatedBy(String.valueOf(processParamMap.get("handlerId")));
            approvalHeader.setLastUpdatedByIp(String.valueOf(processParamMap.get("handlerIp")));
            approvalHeader.setLastUpdateDate(new Date());
            iApprovalHeaderService.auditPass(approvalHeader);
            result = "success";
        }
        log.info("价格审批单流程结束（审批通过）结束processEndEvent");
        return result;
    }

    /**
     * 起草人废弃
     */
    @Override
    public String draftAbandonEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        log.info("价格审批单起草人废弃开始draftAbandonEvent");
        String result = "fail";
        if (businessId == null) {
            return result;
        }
        ApprovalHeader approvalHeader = iApprovalHeaderService.getById(businessId);
        if (approvalHeader == null) {
            return result;
        }
        approvalHeader.setStatus(ApproveStatusType.ABANDONED.getValue());

        Map<String, Object> bodyMap = (null != mapEventData.get("body") ? (Map<String, Object>) mapEventData.get("body") : null);
        if(null != bodyMap && null != bodyMap.get("processParam")) {
            Map<String, Object> processParamMap = (Map<String, Object>) bodyMap.get("processParam");
            approvalHeader.setCbpmInstaceId(String.valueOf(processParamMap.get("processId")));
            approvalHeader.setLastUpdatedBy(String.valueOf(processParamMap.get("handlerId")));
            approvalHeader.setLastUpdatedByIp(String.valueOf(processParamMap.get("handlerIp")));
            approvalHeader.setLastUpdateDate(new Date());
            iApprovalHeaderService.updateById(approvalHeader);
            result = "success";
        }
        log.info("价格审批单起草人废弃结束draftAbandonEvent");
        return result;
    }

    /**
     * 节点进入
     */
    @Override
    public String activityStartEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        log.info("价格审批单节点进入开始activityStartEvent");
        String result = "fail";
        String operationtype = String.valueOf(mapEventData.get("operationtype"));
        boolean isUpdateApproveStatus = false;
        if(CbpmOperationTypeEnum.DRAFT_RETURN.getKey().equals(operationtype)){
            /*起草人撤回*/
            isUpdateApproveStatus = true;
        }else if(CbpmOperationTypeEnum.HANDLER_REFUSE.getKey().equals(operationtype)){
            /*处理人驳回*/
            isUpdateApproveStatus = true;
        }

        if(isUpdateApproveStatus && null != businessId) {
            ApprovalHeader approvalHeader = iApprovalHeaderService.getById(businessId);
            if (approvalHeader != null) {
                approvalHeader.setStatus(ApproveStatusType.REJECTED.getValue());
                approvalHeader.setLastUpdateDate(new Date());
                Map<String, Object> bodyMap = (null != mapEventData.get("body") ? (Map<String, Object>) mapEventData.get("body") : null);
                if(null != bodyMap && null != bodyMap.get("processParam")) {
                    Map<String, Object> processParamMap = (Map<String, Object>) bodyMap.get("processParam");
                    approvalHeader.setLastUpdatedBy(String.valueOf(processParamMap.get("handlerId")));
                    approvalHeader.setLastUpdatedByIp(String.valueOf(processParamMap.get("handlerIp")));
                }
                iApprovalHeaderService.updateById(approvalHeader);
                result = "success";
            }
        }
        log.info("价格审批单节点进入结束activityStartEvent");
        return result;
    }
}