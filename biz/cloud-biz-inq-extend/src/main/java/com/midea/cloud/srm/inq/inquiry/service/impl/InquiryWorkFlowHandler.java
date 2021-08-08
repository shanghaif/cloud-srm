package com.midea.cloud.srm.inq.inquiry.service.impl;

import com.midea.cloud.common.enums.ApproveStatusType;
import com.midea.cloud.common.enums.flow.CbpmOperationTypeEnum;
import com.midea.cloud.common.workflow.WorkFlowFunctionHandler;
import com.midea.cloud.common.enums.inq.InquiryPublishStatusEnum;
import com.midea.cloud.srm.inq.inquiry.service.IHeaderService;
import com.midea.cloud.srm.model.inq.inquiry.dto.InquiryHeaderDto;
import com.midea.cloud.srm.model.inq.inquiry.entity.Header;
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
public class InquiryWorkFlowHandler extends WorkFlowFunctionHandler {

    @Autowired
    private IHeaderService iHeaderService;

    @Override
    public Map<String, Object> getFormData(Long businessId, Map<String, Object> mapEventData) throws Exception{
        log.info("进入询价单formData");
        Map<String, Object> formDataMap = new HashMap<>();
        if(null != businessId) {
            Header header = iHeaderService.getById(businessId);
            formDataMap.put("number", header.getInquiryNo());
            formDataMap.put("createdBy", header.getCreatedBy());
            formDataMap.put("createdFullName", header.getCreatedBy());
        }
        return formDataMap;
    }

    @Override
    public Map<String, Object> draftEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        log.info("询价单审批流程开始draftEvent");
        if(null == businessId) {
            return null;
        }

        Header header = iHeaderService.getById(businessId);
        if (header != null) {
            header.setCbpmInstaceId(String.valueOf(mapEventData.get("processId")));
            header.setLastUpdatedBy(String.valueOf(mapEventData.get("handlerId")));
            header.setLastUpdatedByIp(String.valueOf(mapEventData.get("handlerIp")));
            header.setLastUpdateDate(new Date());
            iHeaderService.updateById(header);
        }
        log.info("询价单审批流程结束draftEvent");
        return null;
    }

    /**
     * 起草人提交
     */
    @Override
    public String draftSubmitEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        log.info("询价单起草人提交开始draftSubmitEvent");
        String result = "fail";
        if (businessId == null) {
            return result;
        }
        Header header = iHeaderService.getById(businessId);
        if (header == null) {
            return result;
        }
        header.setStatus(InquiryPublishStatusEnum.UNPUBLISH.getKey());
        header.setAuditStatus(ApproveStatusType.SUBMITTED.getValue());

        Map<String, Object> bodyMap = (null != mapEventData.get("body") ? (Map<String, Object>) mapEventData.get("body") : null);
        if (bodyMap!= null && null != bodyMap.get("processParam")) {
            Map<String, Object> processParamMap = (Map<String, Object>) bodyMap.get("processParam");
            header.setCbpmInstaceId(String.valueOf(processParamMap.get("processId")));
            header.setLastUpdatedBy(String.valueOf(processParamMap.get("handlerId")));
            header.setLastUpdateDate(new Date());
            header.setLastUpdatedByIp(String.valueOf(processParamMap.get("handlerIp")));
            iHeaderService.updateById(header);
            result = "success";
        }
        log.info("询价单起草人提交结束draftSubmitEvent");
        return result;
    }

    /**
     * 流程结束（审批通过）
     */
    @Override
    public String processEndEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        log.info("询价单流程结束（审批通过）开始processEndEvent");
        String result = "fail";
        if (businessId == null) {
            return result;
        }
        Header header = iHeaderService.getById(businessId);
        if (header == null) {
            return result;
        }

        Map<String, Object> bodyMap = (null != mapEventData.get("body") ? (Map<String, Object>) mapEventData.get("body") : null);
        if(null != bodyMap && null != bodyMap.get("processParam")) {
            Map<String, Object> processParamMap = (Map<String, Object>) bodyMap.get("processParam");
            header.setCbpmInstaceId(String.valueOf(processParamMap.get("processId")));
            header.setLastUpdatedBy(String.valueOf(processParamMap.get("handlerId")));
            header.setLastUpdatedByIp(String.valueOf(processParamMap.get("handlerIp")));
            header.setLastUpdateDate(new Date());
            InquiryHeaderDto headerDto = new InquiryHeaderDto();
            headerDto.setHeader(new Header().setInquiryId(businessId));
            iHeaderService.commit(headerDto, "PASS");
            result = "success";
        }
        log.info("询价单流程结束（审批通过）结束processEndEvent");
        return result;
    }

    /**
     * 起草人废弃
     */
    @Override
    public String draftAbandonEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        log.info("询价单起草人废弃开始draftAbandonEvent");
        String result = "fail";
        if (businessId == null) {
            return result;
        }
        Header header = iHeaderService.getById(businessId);
        if (header == null) {
            return result;
        }
        header.setStatus(InquiryPublishStatusEnum.CANCEL.getKey());
        header.setAuditStatus(ApproveStatusType.ABANDONED.getValue());

        Map<String, Object> bodyMap = (null != mapEventData.get("body") ? (Map<String, Object>) mapEventData.get("body") : null);
        if(null != bodyMap && null != bodyMap.get("processParam")) {
            Map<String, Object> processParamMap = (Map<String, Object>) bodyMap.get("processParam");
            header.setCbpmInstaceId(String.valueOf(processParamMap.get("processId")));
            header.setLastUpdatedBy(String.valueOf(processParamMap.get("handlerId")));
            header.setLastUpdatedByIp(String.valueOf(processParamMap.get("handlerIp")));
            header.setLastUpdateDate(new Date());
            iHeaderService.updateById(header);
            result = "success";
        }
        log.info("询价单起草人废弃结束draftAbandonEvent");
        return result;
    }

    /**
     * 节点进入
     */
    @Override
    public String activityStartEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        log.info("询价单节点进入开始activityStartEvent");
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
            Header header = iHeaderService.getById(businessId);
            if (header != null) {
                header.setStatus(InquiryPublishStatusEnum.DRAFT.getKey());
                header.setAuditStatus(ApproveStatusType.REJECTED.getValue());
                header.setLastUpdateDate(new Date());
                Map<String, Object> bodyMap = (null != mapEventData.get("body") ? (Map<String, Object>) mapEventData.get("body") : null);
                if(null != bodyMap && null != bodyMap.get("processParam")) {
                    Map<String, Object> processParamMap = (Map<String, Object>) bodyMap.get("processParam");
                    header.setLastUpdatedBy(String.valueOf(processParamMap.get("handlerId")));
                    header.setLastUpdatedByIp(String.valueOf(processParamMap.get("handlerIp")));
                }
                iHeaderService.updateById(header);
                result = "success";
            }
        }
        log.info("询价单节点进入结束activityStartEvent");
        return result;
    }
}