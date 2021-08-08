package com.midea.cloud.srm.pr.requirement.service;

import com.midea.cloud.common.enums.pm.pr.requirement.RequirementApproveStatus;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementHead;
import com.midea.cloud.srm.model.workflow.service.IFlowBusinessCallbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RequirementFlowServiceImpl implements IFlowBusinessCallbackService {

    @Autowired
    private IRequirementHeadService iRequirementHeadService;

    @Override
    public void submitFlow(Long businessId, String param) throws Exception {
        log.info("submitFlow: {}, {}", businessId, param);
        RequirementHead requirementHead = new RequirementHead().setRequirementHeadId(businessId).setAuditStatus(RequirementApproveStatus.APPROVING.getValue());
        iRequirementHeadService.updateById(requirementHead);
    }

    @Override
    public void passFlow(Long businessId, String param) throws Exception {
        log.info("passFlow: {}, {}", businessId, param);

        RequirementHead requirementHead = new RequirementHead().setRequirementHeadId(businessId).setAuditStatus(RequirementApproveStatus.APPROVED.getValue());
        iRequirementHeadService.updateById(requirementHead);
    }

    @Override
    public void rejectFlow(Long businessId, String param) throws Exception {
        log.info("rejectFlow: {}, {}", businessId, param);
        RequirementHead requirementHead = new RequirementHead().setRequirementHeadId(businessId).setAuditStatus(RequirementApproveStatus.REJECTED.getValue());
        iRequirementHeadService.updateById(requirementHead);
    }

    @Override
    public void withdrawFlow(Long businessId, String param) throws Exception {
        log.info("withdrawFlow: {}, {}", businessId, param);
        RequirementHead requirementHead = new RequirementHead().setRequirementHeadId(businessId).setAuditStatus(RequirementApproveStatus.WITHDRAW.getValue());
        iRequirementHeadService.updateById(requirementHead);
    }

    @Override
    public void destoryFlow(Long businessId, String param) throws Exception {
        log.info("destoryFlow: {}, {}", businessId, param);
        RequirementHead requirementHead = new RequirementHead().setRequirementHeadId(businessId).setAuditStatus(RequirementApproveStatus.ABANDONED.getValue());
        iRequirementHeadService.updateById(requirementHead);
    }

    @Override
    public String getVariableFlow(Long businessId, String param) throws Exception {
        log.info("getVariableFlow: {}, {}", businessId, param);
        return null;
    }

    @Override
    public String getDataPushFlow(Long businessId, String param) throws Exception {
        log.info("getDataPushFlow: {}, {}", businessId, param);
        return null;
    }
}
