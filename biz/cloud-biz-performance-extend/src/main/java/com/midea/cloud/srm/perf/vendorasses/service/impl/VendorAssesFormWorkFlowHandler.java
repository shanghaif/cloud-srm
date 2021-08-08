package com.midea.cloud.srm.perf.vendorasses.service.impl;

import com.midea.cloud.common.enums.ApproveStatusType;
import com.midea.cloud.common.enums.flow.CbpmOperationTypeEnum;
import com.midea.cloud.common.workflow.WorkFlowFunctionHandler;
import com.midea.cloud.srm.model.perf.vendorasses.VendorAssesForm;
import com.midea.cloud.srm.perf.vendorasses.service.IVendorAssesFormService;
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
 *  修改日期: 2020/8/12 11:38
 *  修改内容:
 * </pre>
 */
@Component
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class VendorAssesFormWorkFlowHandler extends WorkFlowFunctionHandler {

    @Autowired
    private IVendorAssesFormService service;

    @Override
    public Map<String, Object> getFormData(Long businessId, Map<String, Object> mapEventData) throws Exception {
        log.info(this.getClass()+"获取表单事件");
        Map<String, Object> formDataMap = new HashMap();
        if (!Objects.isNull(businessId)) {
            VendorAssesForm assesForm = service.getById(businessId);
            if (!Objects.isNull(assesForm)) {
                formDataMap.put("number", assesForm.getAssessmentNo());
                formDataMap.put("createdFullName", assesForm.getCreatedBy());
            }

        }
        return formDataMap;
    }

    @Override
    public Map<String, Object> draftEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        return new HashMap<>();
    }

    @Override
    public String draftSubmitEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        log.info(this.getClass()+"起草提交事件");

        Boolean result = false;
        if (null != businessId) {
            VendorAssesForm sample = service.getById(businessId);
            result = getParamAndUpdateStatus(mapEventData, ApproveStatusType.SUBMITTED, sample);
        }
        return result ? "success" : "fail";
    }

    @Override
    public String processEndEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        log.info(this.getClass()+"流程结束事件");

        Boolean result = false;
        VendorAssesForm form = service.getById(businessId);
        if (form != null) {
            result = getParamAndUpdateStatus(mapEventData, ApproveStatusType.APPROVED, form);
        }
        return result ? "success" : "fail";
    }

    @Override
    public String handleRefuseEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        if(businessId==null){
            return "fail";
        }
        VendorAssesForm form = service.getById(businessId);
        return getParamAndUpdateStatus(mapEventData,ApproveStatusType.SUBMITTED,form)?
                "success":"fail";
    }

    @Override
    public String draftAbandonEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        if(businessId==null){
            return "fail";
        }
        VendorAssesForm form = service.getById(businessId);
        return getParamAndUpdateStatus(mapEventData,ApproveStatusType.ABANDONED,form)?
                "success":"fail";
    }

    @Override
    public String activityStartEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        String operationtype = String.valueOf(mapEventData.get("operationtype"));
        VendorAssesForm source = service.getById(businessId);
        //驳回到起草人节点
        if (CbpmOperationTypeEnum.HANDLER_REFUSE.getKey().equals(operationtype)) {
            return getParamAndUpdateStatus(mapEventData, ApproveStatusType.REJECTED, source)?"success":"fail";
        }
        return "fail";
    }

    private Boolean getParamAndUpdateStatus(Map<String, Object> mapEventData, ApproveStatusType type, VendorAssesForm form) {
        if (form != null) {
            form.setApproveStatus(type.getValue());
            Map<String, Object> bodyMap = (null != mapEventData.get("body") ? (Map<String, Object>) mapEventData.get("body") : null);
            if (bodyMap != null && null != bodyMap.get("processParam")) {
                Map<String, Object> processParamMap = (Map<String, Object>) bodyMap.get("processParam");
                form.setCbpmInstaceId(String.valueOf(processParamMap.get("processId")))
                        .setLastUpdateDate(new Date())
                        .setLastUpdatedBy(String.valueOf(processParamMap.get("handlerId")))
                        .setLastUpdatedByIp(String.valueOf(processParamMap.get("handlerIp")));
                service.updateById(form);
            }
            return true;
        }
        return false;
    }
}
