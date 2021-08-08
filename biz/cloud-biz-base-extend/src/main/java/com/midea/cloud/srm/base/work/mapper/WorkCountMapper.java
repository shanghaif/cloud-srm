package com.midea.cloud.srm.base.work.mapper;

import com.midea.cloud.srm.model.base.work.dto.WorkCountQueryDTO;

public interface WorkCountMapper {
    /*** 统计供应商改善数量 ***/
    Integer countVendorImprovement(WorkCountQueryDTO countQueryDTO);
    /*** 统计绩效考核单数量 ***/
    Integer countPerformanceAssessment(WorkCountQueryDTO countQueryDTO);
    /*** 统计询价单数量 ***/
    Integer coutInquiryOrders(WorkCountQueryDTO countQueryDTO);
    /*** 统计采购订单数量 ***/
    Integer coutPurchaseOrder(WorkCountQueryDTO countQueryDTO);
    /*** 统计送货单数量 ***/
    Integer coutDeliveryOrder(WorkCountQueryDTO countQueryDTO);
    /*** 统计供应商验收协同数量 ***/
    Integer coutInspectionBill(WorkCountQueryDTO countQueryDTO);
    /*** 统计供应商合同管理数量 ***/
    Integer coutContract(WorkCountQueryDTO countQueryDTO);
    /*** 统计到货计划数量 ***/
    Integer coutDeliverPlan(WorkCountQueryDTO countQueryDTO);
    /*** 统计开票通知数量 ***/
    Integer coutInvoice(WorkCountQueryDTO countQueryDTO);
    /*** 统计供应商网上开票数量 ***/
    Integer coutOnlineInvoice(WorkCountQueryDTO countQueryDTO);
    /*** 统计物料维护数量 ***/
    Integer coutMaterialMaintenance(WorkCountQueryDTO countQueryDTO);
    /*** 统计招投标项目管理列表数量 ***/
    Integer coutVendorBidding(WorkCountQueryDTO countQueryDTO);
}
