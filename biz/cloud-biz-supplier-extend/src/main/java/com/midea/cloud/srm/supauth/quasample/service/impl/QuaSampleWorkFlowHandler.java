package com.midea.cloud.srm.supauth.quasample.service.impl;

import com.midea.cloud.common.enums.ApproveStatusType;
import com.midea.cloud.common.enums.flow.CbpmOperationTypeEnum;
import com.midea.cloud.common.workflow.WorkFlowFunctionHandler;
import com.midea.cloud.srm.model.supplierauth.quasample.entity.QuaSample;
import com.midea.cloud.srm.supauth.quasample.service.IQuaSampleService;
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
 *  修改日期: 2020/8/8 9:54
 *  修改内容:
 * </pre>
 */
@Component
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class QuaSampleWorkFlowHandler extends WorkFlowFunctionHandler {
    @Autowired
    private IQuaSampleService quaSampleService;

    /**
     * 获取表单数据
     * 返回创建人和业务单据
     *
     * @param businessId
     * @param mapEventData
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> getFormData(Long businessId, Map<String, Object> mapEventData) throws Exception {
        log.info(this.getClass()+"获取表单事件");
        Map<String, Object> formDataMap = new HashMap();
        if (!Objects.isNull(businessId)) {
            QuaSample sample = quaSampleService.getById(businessId);
            if (!Objects.isNull(sample)) {
                formDataMap.put("number", sample.getSampleNumber());
                formDataMap.put("createdFullName", sample.getCreatedBy());
            }

        }
        return formDataMap;
    }


    @Override
    public Map<String, Object> draftEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        Map<String, Object> result = new HashMap<>();
        return result;

    }


    /**
     * 起草人提交事件
     *
     * @param businessId
     * @param mapEventData
     * @return
     * @throws Exception
     */
    @Override
    public String draftSubmitEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        log.info(this.getClass()+"起草提交事件");
        Boolean result = false;
        if (null != businessId) {
            QuaSample sample = quaSampleService.getById(businessId);
            result = getParamAndUpdateStatus(mapEventData, ApproveStatusType.SUBMITTED, sample);
        }
        return result ? "success" : "fail";
    }


    /**
     * 节点结束事件
     * @param businessId
     * @param mapEventData
     * @return
     * @throws Exception
     */
    @Override
    public String processEndEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        log.info(this.getClass()+"流程结束事件");
        Boolean result = false;
        QuaSample quaSample = quaSampleService.getById(businessId);
        if (quaSample != null) {
            result = getParamAndUpdateStatus(mapEventData, ApproveStatusType.APPROVED, quaSample);
            quaSampleService.saveOrUpdateCataLogAfterFlow(quaSample);
        }
        return result ? "success" : "fail";
    }

    @Override
    public String handleRefuseEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        if(businessId==null){
            return "fail";
        }
        QuaSample quaSample = quaSampleService.getById(businessId);
        return getParamAndUpdateStatus(mapEventData,ApproveStatusType.SUBMITTED,quaSample)?
        "success":"fail";
    }

    /**
     * 起草人废弃
     * @param businessId
     * @param mapEventData
     * @return
     * @throws Exception
     */
    @Override
    public String draftAbandonEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        if(businessId==null){
            return "fail";
        }
        QuaSample quaSample = quaSampleService.getById(businessId);
        return getParamAndUpdateStatus(mapEventData,ApproveStatusType.ABANDONED,quaSample)?
                "success":"fail";
    }

    /**
     * Description cbpm节点进入事件,用于判断当前状态
     **/
    @Override
    public String activityStartEvent(Long businessId, Map<String, Object> mapEventData) throws Exception {
        String operationtype = String.valueOf(mapEventData.get("operationtype"));
        QuaSample source = quaSampleService.getById(businessId);
        //驳回到起草人节点
        if (CbpmOperationTypeEnum.HANDLER_REFUSE.getKey().equals(operationtype)) {
            return getParamAndUpdateStatus(mapEventData, ApproveStatusType.REJECTED, source)?"success":"fail";
        }
        return "fail";

    }


    private Boolean getParamAndUpdateStatus(Map<String, Object> mapEventData, ApproveStatusType type, QuaSample sample) {
        if (sample != null) {
            sample.setApproveStatus(type.getValue());
            Map<String, Object> bodyMap = (null != mapEventData.get("body") ? (Map<String, Object>) mapEventData.get("body") : null);
            if (bodyMap != null && null != bodyMap.get("processParam")) {
                Map<String, Object> processParamMap = (Map<String, Object>) bodyMap.get("processParam");
                sample.setCbpmInstaceId(String.valueOf(processParamMap.get("processId")))
                        .setLastUpdateDate(new Date())
                        .setLastUpdatedBy(String.valueOf(processParamMap.get("handlerId")))
                        .setLastUpdatedByIp(String.valueOf(processParamMap.get("handlerIp")));
                quaSampleService.updateById(sample);
            }
            return true;
        }
        return false;
    }

}
