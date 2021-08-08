package com.midea.cloud.srm.supauth.orgcategory.service.impl;

import com.midea.cloud.common.enums.ApproveStatusType;
import com.midea.cloud.common.enums.flow.CbpmOperationTypeEnum;
import com.midea.cloud.common.workflow.WorkFlowFunctionHandler;
import com.midea.cloud.srm.model.supplierauth.orgcategory.dto.OrgCatFormDTO;
import com.midea.cloud.srm.model.supplierauth.orgcategory.entity.OrgCatForm;
import com.midea.cloud.srm.supauth.orgcategory.service.IOrgCatFormService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
 *  修改日期: 2020/8/12 9:03
 *  修改内容:
 * </pre>
 */
@Component
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class OrgCatFormWorkFlowHandler extends WorkFlowFunctionHandler {
    @Autowired
    private IOrgCatFormService orgCatFormService;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Map<String, Object> getFormData(Long businessId, Map<String, Object> mapEventData) throws Exception {
        log.info(this.getClass()+"获取表单事件");

        Map<String, Object> formDataMap = new HashMap<>();
        if (null != businessId) {
            OrgCatForm orgCatForm = orgCatFormService.getById(businessId);
            formDataMap.put("number", orgCatForm.getOrgCatFormNumber());
            formDataMap.put("createdFullName", orgCatForm.getCreatedBy());
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
            OrgCatForm orgCatForm = orgCatFormService.getById(businessId);
            result = getParamAndUpdate(mapEventData, ApproveStatusType.SUBMITTED, orgCatForm);
        }
        return result ? "success" : "fail";
    }

    @Override
    public String processEndEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        log.info(this.getClass()+"流程结束事件");
        Boolean success = false;
        if (businessId != null) {
            String key = "orgCat" + businessId;
            OrgCatFormDTO orgCatFormDTO = (OrgCatFormDTO) redisTemplate.opsForValue().get(key);
            OrgCatForm form = orgCatFormDTO.getOrgCatForm();
            form.setOrgCatFormId(businessId);
            orgCatFormService.updateVendorMainDataAfterFLow(orgCatFormDTO.getOrgCateJournals(), form);
            success = true;
            redisTemplate.delete(key);
        }
        return success ? "success" : "false";
    }

    @Override
    public String handleRefuseEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        if (businessId == null) {
            return "fail";
        }
        OrgCatForm source = orgCatFormService.getById(businessId);
        return getParamAndUpdate(mapEventData, ApproveStatusType.SUBMITTED, source) ? "success" : "fail";
    }

    @Override
    public String draftAbandonEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        if (businessId == null) {
            return "fail";
        }
        String key = "orgCat" + businessId;
        OrgCatForm source = orgCatFormService.getById(businessId);
        Boolean result = getParamAndUpdate(mapEventData, ApproveStatusType.SUBMITTED, source);
        redisTemplate.delete(key);
        return result?"success":"fail";
    }

    @Override
    public String activityStartEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        String operationType = String.valueOf(mapEventData.get("operationtype"));
        if (CbpmOperationTypeEnum.HANDLER_REFUSE.getKey().equals(operationType)) {
            OrgCatForm orgCatForm = orgCatFormService.getById(businessId);
            return getParamAndUpdate(mapEventData, ApproveStatusType.REJECTED, orgCatForm) ? "success" : "fail";
        }
        return "success";

    }

    private Boolean getParamAndUpdate(Map<String, Object> mapEventData, ApproveStatusType type, OrgCatForm source) {
        if (source != null) {
            source.setApproveStatus(type.getValue());
            Map<String, Object> bodyMap = (null != mapEventData.get("body") ? (Map<String, Object>) mapEventData.get("body") : null);
            if (bodyMap != null && null != bodyMap.get("processParam")) {
                Map<String, Object> processParamMap = (Map<String, Object>) bodyMap.get("processParam");
                source.setCbpmInstaceId(String.valueOf(processParamMap.get("processId")))
                        .setLastUpdateDate(new Date())
                        .setLastUpdatedBy(String.valueOf(processParamMap.get("handlerId")))
                        .setLastUpdatedByIp(String.valueOf(processParamMap.get("handlerIp")));
                orgCatFormService.updateById(source);
            }
            return true;
        }
        return false;
    }
}
