package com.midea.cloud.common.enums.rbac;

/**
 * <pre>
 *  菜单模块使用说明：
 *  指定菜单列表数据需要根据权限筛选数据时，
 *  1.根据菜单表把菜单编码添加到该枚举中
 *  2.在调用mapper的service上标上@AuthData注解
 *  3.在数据权限配置页面配置上对应的菜单和角色以及显示级别，即可
 * </pre>
 *
 * @author chenjj120@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/10/25 14:52
 *  修改内容:
 * </pre>
 */
public enum MenuEnum {
    PURCHASE_APPLICATION("purchaseApplication" , "采购需求-采购申请-列表"),
    BUYER_PURCHASE_ORDER("buyerPurchaseOrder" , "采购需求-采购订单-列表(采购方)"),
    DEMAND_POOL_MANAGEMENT("demandPoolManagement" , "采购需求-需求池-列表"),

    VENDOR_PURCHASE_ORDER("vendorPurchaseOrder" , "订单协同-采购订单-列表（供应商）"),
    MATERIAL_PLAN("materialPlan" , "订单协同-物料计划维护-列表"),
    VENDOR_DELIVER_PLAN("vendorDeliverPlan" , "订单协同-到货计划表-列表"),
    VENDOR_DELIVERY_ORDER("vendorDeliveryOrder" , "订单协同-送货单-列表"),


    PURCHASE_ORDER_CHANGE("purchaseOrderChange" , "订单管理-采购订单变更-列表（采购商）"),
    BUYER_DELIVERY_ORDER("buyerDeliveryOrder" , "订单管理-送货单-列表（采购商）"),

    SUPPLIER_PURCHASE_ORDER_CHANGE("supplierPurchaseOrderChange" , "订单协同-采购订单变更-列表（供应商）"),


    ONLINE_INVOICE("onlineInvoice" , "采购结算-网上开票-列表"),
    AGENT_ONLINE_INVOICE("agentOnlineInvoice" , "采购结算-代理网上开票-列表"),
    PUR_INVOICE("purInvoice" , "采购结算-开票通知-列表"),
    PUR_PAYMENT_APPLY("purPaymentApply","采购结算-付款申请-列表"),
    ADVANCE_PAYMENT("advancePayment","采购结算-预付款申请-列表"),
    PUR_INVOICE_SUPPLIER("purInvoiceSupplier" , "采购结算-开票通知-列表(供应商)"),
    SUP_ONLINE_INVOICE("supOnlineInvoice" , "采购结算-供方网上开票-列表（供应商）"),



    INSPECTION_BILL_LIST("inspectionBillList" , "采购合同-验收单-列表"),
    INSPECTION_APPLY_BILL("inspectionApplyBill" , "采购合同-验收申请单-列表"),
    CONTRACT_MAINTAIN_LIST("contractMaintainList", "采购合同-合同管理-列表"),

    BIDDING_PROJECT_NEW("biddingProject_new" , "询比价-项目管理"),
    BIDDING_PROJECT("biddingProject" , "招标管理-项目管理"),
    SOURCING_TEMPLATE("sourcingTemplate" , "招标管理-寻源模板"),

    PRICE_CATALOG("priceCatalog" , "价格管理-价格目录-列表"),
    INQUIRY_APPROVAL_FLOW("inquiryApprovalFlow" , "价格管理-价格审批单-列表"),

    QUA_OF_REVIEW("quaOfReview" , "供应商管理-资质审查-列表"),
    SITE_ASSESSMENT("siteAssessment" , "供应商管理-供应商认证-列表"),
    CROSS_ORG_IMPORT("crossOrgImport" , "供应商管理-跨组织引入-列表"),
    VENDOR_INFO_CHANGE("vendorInfoChange" , "供应商管理-供应商信息变更-列表"),

    QUOTA_MODULATION("quotaModulation" , "配额管理-配额调整-列表"),
    QUOTA_CONFIG("quotaConfig" , "配额管理-配额配置-列表"),

    //目前供应商只查询自身供应商数据
    SUPPLIER_SIGN("supplierSign","供应商标志")
    ;

    private String functionCode;    //菜单编码；对应表【scc_rbac_function】中的菜单编码

    private String functionName;    //菜单名称，开发方便看

    public String getFunctionCode() {
        return functionCode;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }
    MenuEnum(String functionCode , String functionName){
        this.functionCode = functionCode;
        this.functionName = functionName;
    }
}
