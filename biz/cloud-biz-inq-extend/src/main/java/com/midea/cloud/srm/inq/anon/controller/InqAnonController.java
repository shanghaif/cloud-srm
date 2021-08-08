package com.midea.cloud.srm.inq.anon.controller;

import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.inq.price.mapper.ApprovalBiddingItemMapper;
import com.midea.cloud.srm.inq.price.service.IApprovalHeaderService;
import com.midea.cloud.srm.model.inq.price.entity.ApprovalHeader;
import com.midea.cloud.srm.model.inq.price.vo.ApprovalAllVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/inq-anon")
public class InqAnonController {

    @Resource
    private IApprovalHeaderService iApprovalHeaderService;

    /**
     * 审批通过
     */
    @PostMapping("/price/approval/auditPass")
    public void auditPass(@RequestParam("approvalHeaderId") Long approvalHeaderId) {
        Assert.notNull(approvalHeaderId, LocaleHandler.getLocaleMsg("审批单id不能为空"));
        ApprovalHeader header = iApprovalHeaderService.getById(approvalHeaderId);
        Assert.notNull(header, LocaleHandler.getLocaleMsg("价格审批单不存在"));
        iApprovalHeaderService.auditPass(header);
    }

    /**
     * 审批驳回
     */
    @RequestMapping("/price/approval/reject")
    public void reject(Long approvalHeaderId, String ceeaDrafterOpinion) {
        Assert.notNull(approvalHeaderId, LocaleHandler.getLocaleMsg("审批单id不能为空"));
        iApprovalHeaderService.reject(approvalHeaderId,ceeaDrafterOpinion);
    }

    /**
     * 审批撤回
     */
    @RequestMapping("/price/approval/withdraw")
    public void withdraw(Long approvalHeaderId) {
        Assert.notNull(approvalHeaderId, LocaleHandler.getLocaleMsg("审批单id不能为空"));
        iApprovalHeaderService.withdraw(approvalHeaderId);
    }

    /**
     * 获取价格审批详情
     */
    @GetMapping("/price/approval/approvalDetail")
    public ApprovalAllVo ceeaGetApprovalDetail(Long approvalHeaderId) {
        return iApprovalHeaderService.ceeaGetApprovalDetail(approvalHeaderId);
    }
}
