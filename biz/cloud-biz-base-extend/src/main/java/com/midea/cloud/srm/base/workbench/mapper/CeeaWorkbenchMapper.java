package com.midea.cloud.srm.base.workbench.mapper;

import com.midea.cloud.srm.model.flow.query.dto.WorkbenchMyTaskDTO;
import com.midea.cloud.srm.model.flow.vo.WorkbenchMyTaskVo;

import java.util.Collection;
import java.util.List;

public interface CeeaWorkbenchMapper {

    // 待办
    /*** 查询资质审查待办信息 ***/
    List<WorkbenchMyTaskVo> findQuaReviewProcess(WorkbenchMyTaskDTO workbenchMyTaskDTO);
    /*** 查询供应商认证待办信息 ***/
    Collection<? extends WorkbenchMyTaskVo> findSiteReviewProcess(WorkbenchMyTaskDTO workbenchMyTaskDTO);
    /*** 查询跨组织引入待办信息 ***/
    Collection<? extends WorkbenchMyTaskVo> findVendorImportProcess(WorkbenchMyTaskDTO workbenchMyTaskDTO);
    /*** 供应商绿色通道待办信息 ***/
    Collection<? extends WorkbenchMyTaskVo> findCompanyInfoProcess(WorkbenchMyTaskDTO workbenchMyTaskDTO);
    /*** 查询绩效评分项目待办信息 ***/
    Collection<? extends WorkbenchMyTaskVo> findPerfScoreManProcess(WorkbenchMyTaskDTO workbenchMyTaskDTO);
    /*** 查询招投标项目管理待办信息 ***/
    Collection<? extends WorkbenchMyTaskVo> findBidingProcess(WorkbenchMyTaskDTO workbenchMyTaskDTO);
    /*** 查询询比价待办信息 ***/
    Collection<? extends WorkbenchMyTaskVo> findInqProcess(WorkbenchMyTaskDTO workbenchMyTaskDTO);
    /*** 查询价格审批单待办信息 ***/
    Collection<? extends WorkbenchMyTaskVo> findPriceApproveProcess(WorkbenchMyTaskDTO workbenchMyTaskDTO);
    /*** 查询配额调整待办信息 ***/
    Collection<? extends WorkbenchMyTaskVo> findQuotaAdjustProcess(WorkbenchMyTaskDTO workbenchMyTaskDTO);
    /*** 查询采购申请管理信息 ***/
    Collection<? extends WorkbenchMyTaskVo> findRequirementProcess(WorkbenchMyTaskDTO workbenchMyTaskDTO);
    /*** 查询需求池管理信息 ***/
    Collection<? extends WorkbenchMyTaskVo> findRequirementManageProcess(WorkbenchMyTaskDTO workbenchMyTaskDTO);
    /*** 查询采购订单信息 ***/
    Collection<? extends WorkbenchMyTaskVo> findPurchaseOrderProcess(WorkbenchMyTaskDTO workbenchMyTaskDTO);
    /*** 查询采购订单变更信息 ***/
    Collection<? extends WorkbenchMyTaskVo> findPurchaseOrderChangeProcess(WorkbenchMyTaskDTO workbenchMyTaskDTO);
    /*** 查询合同列表信息 ***/
    Collection<? extends WorkbenchMyTaskVo> findContractProcess(WorkbenchMyTaskDTO workbenchMyTaskDTO);
    /*** 查询验收申请列表信息 ***/
    Collection<? extends WorkbenchMyTaskVo> findInspectionApplyProcess(WorkbenchMyTaskDTO workbenchMyTaskDTO);
    /*** 查询验收单信息 ***/
    Collection<? extends WorkbenchMyTaskVo> findInspectionProcess(WorkbenchMyTaskDTO workbenchMyTaskDTO);
    /*** 查询预付款信息 ***/
    Collection<? extends WorkbenchMyTaskVo> findAdvancePaymentProcess(WorkbenchMyTaskDTO workbenchMyTaskDTO);
    /*** 查询开票通知信息 ***/
    Collection<? extends WorkbenchMyTaskVo> findPurInvoiceProcess(WorkbenchMyTaskDTO workbenchMyTaskDTO);
    /*** 查询网上发票信息 ***/
    Collection<? extends WorkbenchMyTaskVo> findOnlineInvoiceProcess(WorkbenchMyTaskDTO workbenchMyTaskDTO);
    /*** 查询代理网上开票信息 ***/
    Collection<? extends WorkbenchMyTaskVo> findAgentOnlineInvoiceProcess(WorkbenchMyTaskDTO workbenchMyTaskDTO);
    /*** 查询付款申请信息 ***/
    Collection<? extends WorkbenchMyTaskVo> findPurPaymentApplyProcess(WorkbenchMyTaskDTO workbenchMyTaskDTO);
    /*** 查询购物车信息 ***/
    Collection<? extends WorkbenchMyTaskVo> findShoppingCartProcess(WorkbenchMyTaskDTO workbenchMyTaskDTO);
    /*** 供应商信息变更 ***/
    Collection<? extends WorkbenchMyTaskVo> findVendorInfoChangeProcess(WorkbenchMyTaskDTO workbenchMyTaskDTO);

    // 已办
    /*** 获取绩效评分人已办信息 ***/
    Collection<? extends WorkbenchMyTaskVo> findPerfScoreManWorked(WorkbenchMyTaskDTO workbenchMyTaskDTO);
    /*** 需求池已办信息 ***/
    Collection<? extends WorkbenchMyTaskVo> findRequirementManageWorked(WorkbenchMyTaskDTO workbenchMyTaskDTO);

}
