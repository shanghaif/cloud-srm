package com.midea.cloud.srm.bid.purchaser.projectmanagement.bidingresult.service.impl;

import com.midea.cloud.common.enums.bid.projectmanagement.projectpublish.BiddingApprovalStatus;
import com.midea.cloud.common.enums.bid.projectmanagement.projectpublish.BiddingProjectStatus;
import com.midea.cloud.common.workflow.WorkFlowFunctionHandler;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IBidingService;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.Biding;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Description 招标-结项审批 回调实现类
 *
 * @Param
 * @return
 * @Author fengdc3@meicloud.com
 * @Date 2020.04.15
 * @throws
 **/
@Component
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class EndProjectApprovalWorkFlowHandler extends WorkFlowFunctionHandler {

    @Autowired
    private IBidingService iBidingService;

    /**
     * Description cbpm回调事件 ---- 获取表单数据
     **/
    @Override
    public Map<String, Object> getFormData(Long businessId, Map<String, Object> mapEventData) throws Exception {
        log.info("进入formData");
        Map<String, Object> formDataMap = new HashMap<>();
        if (null != businessId) {
            Biding biding = iBidingService.getById(businessId);
            formDataMap.put("createdBy", biding.getCreatedBy());
        }
        log.info("结束formData");
        return formDataMap;
    }

    /**
     * Description cbpm回调事件 ---- 起草人保存草稿事件
     **/
    @Override
    public Map<String, Object> draftEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        log.info("项目管理流程开始draftEvent");
        if(null != businessId) {
            Biding biding = iBidingService.getById(businessId);
            if (biding != null && null != mapEventData) {
                biding.setEndCbpmInstanceId(String.valueOf(mapEventData.get("processId")));
                biding.setLastUpdateDate(new Date());
                biding.setLastUpdatedBy(String.valueOf(mapEventData.get("handlerId")));
                biding.setLastUpdatedByIp(String.valueOf(mapEventData.get("handlerIp")));
                iBidingService.updateById(biding);
            }
        }
        log.info("项目管理流程结束draftEvent");
        return null;
    }

    /**
     * Description cbpm回调事件 ---- 起草人提交
     **/
    @Override
    public String draftSubmitEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        log.info("开始draftSubmitEvent");
        String result = "fail";
        if (null != businessId) {
            Biding biding = iBidingService.getById(businessId);
            if (biding != null) {
                Map<String, Object> bodyMap = (null != mapEventData.get("body") ? (Map<String, Object>) mapEventData.get("body") : null);
                if (bodyMap != null && null != bodyMap.get("processParam")) {
                    Map<String, Object> processParamMap = (Map<String, Object>) bodyMap.get("processParam");
                    biding.setEndCbpmInstanceId(String.valueOf(processParamMap.get("processId")));
                    biding.setEndAuditStatus(BiddingApprovalStatus.SUBMITTED.getValue());
                    biding.setLastUpdateDate(new Date());
                    biding.setLastUpdatedBy(String.valueOf(processParamMap.get("handlerId")));
                    biding.setLastUpdatedByIp(String.valueOf(processParamMap.get("handlerIp")));
                    iBidingService.updateById(biding);
                    result = "success";
                }
            }
        }
        log.info("结束draftSubmitEvent");
        return result;
    }

    /**
     * Description cbpm回调事件 ---- 流程结束(审批通过事件)
     **/
    @Override
    public String processEndEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        log.info("开始processEndEvent");
        String result = "fail";
        if (null != businessId) {
            Biding biding = iBidingService.getById(businessId);
            Map<String, Object> bodyMap = (null != mapEventData.get("body") ? (Map<String, Object>) mapEventData.get("body") : null);
            if (null != bodyMap && null != bodyMap.get("processParam")) {
                Map<String, Object> processParamMap = (Map<String, Object>) bodyMap.get("processParam");
                biding.setLastUpdatedBy(String.valueOf(processParamMap.get("handlerName")));
                biding.setLastUpdatedByIp(String.valueOf(processParamMap.get("handlerIp")));
                biding.setEndAuditStatus(BiddingApprovalStatus.APPROVED.getValue());
                biding.setBidingStatus(BiddingProjectStatus.PROJECT_END.getValue());
                iBidingService.updateById(biding);
                result = "success";
            }
        }
        log.info("结束processEndEvent");
        return result;
    }

    /**
     * Description cbpm回调事件 ---- 处理人驳回
     **/
    @Override
    public String handleRefuseEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        log.info("开始handleRefuseEvent");
        String result = "fail";
        if (businessId != null) {
            Biding biding = iBidingService.getById(businessId);
            if (null != biding) {
                Map<String, Object> bodyMap = (null != mapEventData.get("body") ? (Map<String, Object>) mapEventData.get("body") : null);
                biding.setLastUpdateDate(new Date());
                if (bodyMap != null && null != bodyMap.get("processParam")) {
                    Map<String, Object> processParamMap = (Map<String, Object>) bodyMap.get("processParam");
                    biding.setEndAuditStatus(BiddingApprovalStatus.REJECTED.getValue());
                    biding.setLastUpdatedBy(String.valueOf(processParamMap.get("handlerId")));
                    biding.setLastUpdatedByIp(String.valueOf(processParamMap.get("handlerIp")));
                    biding.setLastUpdateDate(new Date());
                }
                iBidingService.updateById(biding);
                result = "success";
            }
        }
        log.info("结束handleRefuseEvent");
        return result;
    }

    /**
     * Description cbpm回调事件 ---- 流程废弃
     **/
    @Override
    public String processAbandonEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        String result = "fail";
        if (businessId != null) {
            Biding biding = iBidingService.getById(businessId);
            if (null != biding) {
                Map<String, Object> bodyMap = (null != mapEventData.get("body") ? (Map<String, Object>) mapEventData.get("body") : null);
                biding.setLastUpdateDate(new Date());
                if (bodyMap != null && null != bodyMap.get("processParam")) {
                    Map<String, Object> processParamMap = (Map<String, Object>) bodyMap.get("processParam");
                    biding.setEndAuditStatus(BiddingApprovalStatus.DRAFT.getValue());
                    biding.setLastUpdatedBy(String.valueOf(processParamMap.get("handlerId")));
                    biding.setLastUpdatedByIp(String.valueOf(processParamMap.get("handlerIp")));
                    biding.setLastUpdateDate(new Date());
                }
                iBidingService.updateById(biding);
                result = "success";
            }
        }
        return result;
    }

    /**
     * Description cbpm回调事件 ---- 起草人废弃
     **/
    public String draftAbandonEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        String result = "fail";
        if (businessId != null) {
            Biding biding = iBidingService.getById(businessId);
            if (null != biding) {
                Map<String, Object> bodyMap = (null != mapEventData.get("body") ? (Map<String, Object>) mapEventData.get("body") : null);
                biding.setLastUpdateDate(new Date());
                if (bodyMap != null && null != bodyMap.get("processParam")) {
                    Map<String, Object> processParamMap = (Map<String, Object>) bodyMap.get("processParam");
                    biding.setEndAuditStatus(BiddingApprovalStatus.DRAFT.getValue());
                    biding.setLastUpdatedBy(String.valueOf(processParamMap.get("handlerId")));
                    biding.setLastUpdatedByIp(String.valueOf(processParamMap.get("handlerIp")));
                    biding.setLastUpdateDate(new Date());
                }
                iBidingService.updateById(biding);
                result = "success";
            }
        }
        return result;
    }

    /**
     * Description cbpm回调事件 ---- 起草人撤回
     **/
    public String draftReturnEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        String result = "fail";
        if (businessId != null) {
            Biding biding = iBidingService.getById(businessId);
            if (null != biding) {
                Map<String, Object> bodyMap = (null != mapEventData.get("body") ? (Map<String, Object>) mapEventData.get("body") : null);
                biding.setLastUpdateDate(new Date());
                if (bodyMap != null && null != bodyMap.get("processParam")) {
                    Map<String, Object> processParamMap = (Map<String, Object>) bodyMap.get("processParam");
                    biding.setEndAuditStatus(BiddingApprovalStatus.DRAFT.getValue());
                    biding.setLastUpdatedBy(String.valueOf(processParamMap.get("handlerId")));
                    biding.setLastUpdatedByIp(String.valueOf(processParamMap.get("handlerIp")));
                    biding.setLastUpdateDate(new Date());
                }
                iBidingService.updateById(biding);
                result = "success";
            }
        }
        return result;
    }

    /**
     * Description cbpm回调事件 ---- 审批人废弃
     **/
    public String handleAbandonEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        String result = "fail";
        if (businessId != null) {
            Biding biding = iBidingService.getById(businessId);
            if (null != biding) {
                Map<String, Object> bodyMap = (null != mapEventData.get("body") ? (Map<String, Object>) mapEventData.get("body") : null);
                biding.setLastUpdateDate(new Date());
                if (bodyMap != null && null != bodyMap.get("processParam")) {
                    Map<String, Object> processParamMap = (Map<String, Object>) bodyMap.get("processParam");
                    biding.setEndAuditStatus(BiddingApprovalStatus.DRAFT.getValue());
                    biding.setLastUpdatedBy(String.valueOf(processParamMap.get("handlerId")));
                    biding.setLastUpdatedByIp(String.valueOf(processParamMap.get("handlerIp")));
                    biding.setLastUpdateDate(new Date());
                }
                iBidingService.updateById(biding);
                result = "success";
            }
        }
        return result;
    }

    /**
     * Description 节点废弃
     **/
    public String activityAbandonEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        String result = "fail";
        if (businessId != null) {
            Biding biding = iBidingService.getById(businessId);
            if (null != biding) {
                Map<String, Object> bodyMap = (null != mapEventData.get("body") ? (Map<String, Object>) mapEventData.get("body") : null);
                biding.setLastUpdateDate(new Date());
                if (bodyMap != null && null != bodyMap.get("processParam")) {
                    Map<String, Object> processParamMap = (Map<String, Object>) bodyMap.get("processParam");
                    biding.setEndAuditStatus(BiddingApprovalStatus.DRAFT.getValue());
                    biding.setLastUpdatedBy(String.valueOf(processParamMap.get("handlerId")));
                    biding.setLastUpdatedByIp(String.valueOf(processParamMap.get("handlerIp")));
                    biding.setLastUpdateDate(new Date());
                }
                iBidingService.updateById(biding);
                result = "success";
            }
        }
        return result;
    }
}
