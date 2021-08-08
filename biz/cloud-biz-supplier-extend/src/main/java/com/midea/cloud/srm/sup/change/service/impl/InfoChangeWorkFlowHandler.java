package com.midea.cloud.srm.sup.change.service.impl;

import com.midea.cloud.common.enums.ApproveStatusType;
import com.midea.cloud.common.enums.flow.CbpmOperationTypeEnum;
import com.midea.cloud.common.workflow.WorkFlowFunctionHandler;
import com.midea.cloud.srm.model.supplier.change.dto.ChangeInfoDTO;
import com.midea.cloud.srm.model.supplier.change.entity.InfoChange;
import com.midea.cloud.srm.sup.change.service.IInfoChangeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

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
 *  修改日期: 2020/8/10 17:09
 *  修改内容:
 * </pre>
 */
@Component
@Slf4j
public class InfoChangeWorkFlowHandler extends WorkFlowFunctionHandler {
    @Autowired
    private IInfoChangeService infoChangeService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private InfoChangeServiceImpl service;

    @Override
    public Map<String, Object> getFormData(Long businessId, Map<String, Object> mapEventData) throws Exception {
        Map<String, Object> formDataMap = new HashMap<>();
        log.info(this.getClass()+"获取表单事件");
        if (null != businessId) {
            InfoChange infoChange = infoChangeService.getById(businessId);
            formDataMap.put("number", infoChange.getChangeApplyNo());
            formDataMap.put("createdFullName", infoChange.getCreatedBy());
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
            InfoChange infoChange = infoChangeService.getById(businessId);
            result = getParamAndUpdate(mapEventData, ApproveStatusType.SUBMITTED, infoChange);
        }
        return result ? "success" : "fail";
    }

    @Override
    public String processEndEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        log.info(this.getClass()+"流程结束事件");
        Boolean result = false;
        if (null != businessId) {
            InfoChange infoChange = infoChangeService.getById(businessId);
            String key = "sup-info" + infoChange.getChangeId();
            ChangeInfoDTO dto = (ChangeInfoDTO) redisTemplate.opsForValue().get(key);
            InfoChange source = dto.getInfoChange() == null ? new InfoChange() : dto.getInfoChange();
            dto.setInfoChange(source);
            Map<String, Object> bodyMap = (null != mapEventData.get("body") ? (Map<String, Object>) mapEventData.get("body") : null);
            if (bodyMap != null && null != bodyMap.get("processParam")) {
                Map<String, Object> processParamMap = (Map<String, Object>) bodyMap.get("processParam");
                source.setCbpmInstaceId(String.valueOf(processParamMap.get("processId")))
                        .setLastUpdateDate(new Date())
                        .setLastUpdatedBy(String.valueOf(processParamMap.get("handlerId")))
                        .setLastUpdatedIp(String.valueOf(processParamMap.get("handlerIp")));
                service.updateChangeWithFlow(dto, ApproveStatusType.APPROVED.getValue());
            }
            //确保update成功再删除
            redisTemplate.delete(key);
            result = true;
        }
        return result ? "success" : "fail";
    }

    @Override
    public String draftAbandonEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        if (businessId == null) {
            return "fail";
        }
        InfoChange source = infoChangeService.getById(businessId);
        boolean result = getParamAndUpdate(mapEventData, ApproveStatusType.ABANDONED, source);
        String key = "sup-info" + businessId;
        redisTemplate.delete(key);
        return result ? "success" : "fail";
    }

    @Override
    public String handleRefuseEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        if (businessId == null) {
            return "fail";
        }
        InfoChange source = infoChangeService.getById(businessId);
        return getParamAndUpdate(mapEventData, ApproveStatusType.SUBMITTED, source) ?
                "success" : "fail";
    }

    @Override
    public String activityStartEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        String operationType = String.valueOf(mapEventData.get("operationtype"));
        //驳回到起草人节点
        if (CbpmOperationTypeEnum.HANDLER_REFUSE.getKey().equals(operationType)) {
            InfoChange source = infoChangeService.getById(businessId);
            return getParamAndUpdate(mapEventData, ApproveStatusType.REJECTED, source) ? "success" : "fail";
        }
        return "success";
    }


    private Boolean getParamAndUpdate(Map<String, Object> mapEventData, ApproveStatusType type, InfoChange source) {
        if (source != null) {
            source.setApproveStatus(type.getValue());
            Map<String, Object> bodyMap = (null != mapEventData.get("body") ? (Map<String, Object>) mapEventData.get("body") : null);
            if (bodyMap != null && null != bodyMap.get("processParam")) {
                Map<String, Object> processParamMap = (Map<String, Object>) bodyMap.get("processParam");
                source.setCbpmInstaceId(String.valueOf(processParamMap.get("processId")))
                        .setLastUpdateDate(new Date())
                        .setLastUpdatedBy(String.valueOf(processParamMap.get("handlerId")))
                        .setLastUpdatedIp(String.valueOf(processParamMap.get("handlerIp")));
                infoChangeService.updateById(source);
            }
            return true;
        }
        return false;
    }
}
