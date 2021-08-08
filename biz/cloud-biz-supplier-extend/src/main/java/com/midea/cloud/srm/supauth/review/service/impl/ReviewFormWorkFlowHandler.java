package com.midea.cloud.srm.supauth.review.service.impl;

import com.midea.cloud.common.enums.ApproveStatusType;
import com.midea.cloud.common.enums.flow.CbpmOperationTypeEnum;
import com.midea.cloud.common.workflow.WorkFlowFunctionHandler;
import com.midea.cloud.srm.model.supplierauth.review.entity.ReviewForm;
import com.midea.cloud.srm.supauth.review.service.IReviewFormService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Description 资质审查回调实现类
 * @Param
 * @return
 * @Author wuwl18@meicloud.com
 * @Date 2020.04.11
 * @throws
 **/
@Component
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class ReviewFormWorkFlowHandler extends WorkFlowFunctionHandler {

    /**资质审查Service*/
    @Autowired
    private IReviewFormService iReviewFormService;

    @Override
    public Map<String, Object> getFormData(Long businessId, Map<String, Object> mapEventData) throws Exception{
        Map<String, Object> formDataMap = new HashMap<>();
        if(null != businessId) {
            ReviewForm reviewForm = iReviewFormService.getById(businessId);
            formDataMap.put("number", reviewForm.getReviewFormNumber());
            formDataMap.put("createdBy", reviewForm.getCreatedBy());
            formDataMap.put("createdFullName", reviewForm.getCreatedFullName());
        }
        return formDataMap;
    }

    @Override
    public Map<String, Object> draftEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        Map<String, Object> result = new HashMap<>();
        log.info("进入流程自定义draftEvent事件,表单ID",businessId);
        /**更新资质审查表流程ID*/
        if(null != businessId) {
            ReviewForm reviewForm = iReviewFormService.getById(businessId);
            if (reviewForm != null && null != mapEventData) {
                reviewForm.setCbpmInstaceId(String.valueOf(mapEventData.get("processId")));
                reviewForm.setLastUpdateDate(new Date());
                reviewForm.setLastUpdatedBy(String.valueOf(mapEventData.get("handlerId")));
                reviewForm.setLastUpdatedFullName(String.valueOf(mapEventData.get("handlerName")));
                reviewForm.setLastUpdatedByIp(String.valueOf(mapEventData.get("handlerIp")));
                iReviewFormService.updateById(reviewForm);
            }
        }
        return result;
    }

    @Override
    public String draftSubmitEvent(Long businessId, Map<String, Object> mapEventData) throws Exception{
        String result = "fail";
        if(null != businessId) {
            ReviewForm reviewForm = iReviewFormService.getById(businessId);
            if (reviewForm != null) {
                reviewForm.setApproveStatus(ApproveStatusType.SUBMITTED.getValue());
                Map<String, Object> bodyMap = (null != mapEventData.get("body") ? (Map<String, Object>) mapEventData.get("body") : null);
                if(bodyMap!= null && null != bodyMap.get("processParam")) {
                    Map<String, Object> processParamMap = (Map<String, Object>) bodyMap.get("processParam");
                    reviewForm.setCbpmInstaceId(String.valueOf(processParamMap.get("processId")));
                    reviewForm.setLastUpdateDate(new Date());
                    reviewForm.setLastUpdatedBy(String.valueOf(processParamMap.get("handlerId")));
                    reviewForm.setLastUpdatedFullName(String.valueOf(mapEventData.get("handlerName")));
                    reviewForm.setLastUpdatedByIp(String.valueOf(processParamMap.get("handlerIp")));
                    iReviewFormService.updateById(reviewForm);
                    result = "success";
                }
            }
        }
        return result;
    }

    @Override
    public String processEndEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        String result = "fail";
        if(null != businessId) {
            ReviewForm reviewForm = iReviewFormService.getById(businessId);
            Map<String, Object> bodyMap = (null != mapEventData.get("body") ? (Map<String, Object>) mapEventData.get("body") : null);
            if(null != bodyMap && null != bodyMap.get("processParam")) {
                Map<String, Object> processParamMap = (Map<String, Object>) bodyMap.get("processParam");
                reviewForm.setLastUpdatedBy(String.valueOf(processParamMap.get("handlerId")));
                reviewForm.setLastUpdatedFullName(String.valueOf(processParamMap.get("handlerName")));
                reviewForm.setLastUpdatedByIp(String.valueOf(processParamMap.get("handlerIp")));
                iReviewFormService.updateReviewFormAfterWorkFlow(reviewForm);
            }
            result = "success";
        }
        return result;
    }

    @Override
    public String draftAbandonEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        log.info("起草人废弃流程",businessId);
        String result = "fail";
        if(businessId != null){
            ReviewForm reviewForm = iReviewFormService.getById(businessId);
            if(null != reviewForm) {
                reviewForm.setApproveStatus(ApproveStatusType.ABANDONED.getValue());    //起草人废弃时，资质审查单状态为 拟定
                Map<String, Object> bodyMap = (null != mapEventData.get("body") ? (Map<String, Object>) mapEventData.get("body") : null);
                reviewForm.setLastUpdateDate(new Date());
                if (bodyMap != null && null != bodyMap.get("processParam")) {
                    Map<String, Object> processParamMap = (Map<String, Object>) bodyMap.get("processParam");
                    reviewForm.setLastUpdatedBy(String.valueOf(processParamMap.get("handlerId")));
                    reviewForm.setLastUpdatedFullName(String.valueOf(processParamMap.get("handlerName")));
                    reviewForm.setLastUpdatedByIp(String.valueOf(processParamMap.get("handlerIp")));
                    reviewForm.setLastUpdateDate(new Date());
                }
                iReviewFormService.updateById(reviewForm);
                result = "success";
            }
        }
        return result;
    }

    @Override
    public String activityAbandonEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        log.info("节点废弃流程",businessId);
        String result = "fail";
        if(businessId != null){
            ReviewForm reviewForm = iReviewFormService.getById(businessId);
            if(null != reviewForm) {
                reviewForm.setApproveStatus(ApproveStatusType.ABANDONED.getValue());    //起草人废弃时，资质审查单状态为 拟定
                Map<String, Object> bodyMap = (null != mapEventData.get("body") ? (Map<String, Object>) mapEventData.get("body") : null);
                reviewForm.setLastUpdateDate(new Date());
                if (bodyMap != null && null != bodyMap.get("processParam")) {
                    Map<String, Object> processParamMap = (Map<String, Object>) bodyMap.get("processParam");
                    reviewForm.setLastUpdatedBy(String.valueOf(processParamMap.get("handlerId")));
                    reviewForm.setLastUpdatedFullName(String.valueOf(processParamMap.get("handlerName")));
                    reviewForm.setLastUpdatedByIp(String.valueOf(processParamMap.get("handlerIp")));
                    reviewForm.setLastUpdateDate(new Date());
                }
                iReviewFormService.updateById(reviewForm);
                result = "success";
            }
        }
        return result;
    }

    /**
     * Description cbpm节点进入事件
     * @Param businessId 业务审批表ID
     * @Param mapEventData 事件map
     * @return String
     * @Author wuwl18@meicloud.com
     * @Date 2020.03.25
     * @throws Exception
     **/
    @Override
    public String activityStartEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        log.info("节点进入事件",businessId);
        String result = "fail";
        String operationtype = String.valueOf(mapEventData.get("operationtype"));
        String aproveStatus = ApproveStatusType.REJECTED.getValue();
        boolean isUpdateReviewForm = false;
        if(CbpmOperationTypeEnum.DRAFT_RETURN.getKey().equals(operationtype)){ //起草人撤回
            aproveStatus = ApproveStatusType.DRAFT.getValue();
            isUpdateReviewForm = true;
            log.info("这是起草人撤回节点");
        }else if(CbpmOperationTypeEnum.HANDLER_REFUSE.getKey().equals(operationtype)){ //驳回到起草人节点
            isUpdateReviewForm = true;
            log.info("这是驳回到起草人节点");
        }

        if(isUpdateReviewForm && null != businessId) {
            ReviewForm reviewForm = iReviewFormService.getById(businessId);
            if (reviewForm != null) {
                reviewForm.setApproveStatus(aproveStatus);
                reviewForm.setLastUpdateDate(new Date());
                Map<String, Object> bodyMap = (null != mapEventData.get("body") ? (Map<String, Object>) mapEventData.get("body") : null);
                if(null != bodyMap && null != bodyMap.get("processParam")) {
                    Map<String, Object> processParamMap = (Map<String, Object>) bodyMap.get("processParam");
                    reviewForm.setLastUpdatedBy(String.valueOf(processParamMap.get("handlerId")));
                    reviewForm.setLastUpdatedFullName(String.valueOf(processParamMap.get("handlerName")));
                    reviewForm.setLastUpdatedByIp(String.valueOf(processParamMap.get("handlerIp")));
                }
                result = "success";
                iReviewFormService.updateById(reviewForm);
            }
        }
        return result;
    }


}
