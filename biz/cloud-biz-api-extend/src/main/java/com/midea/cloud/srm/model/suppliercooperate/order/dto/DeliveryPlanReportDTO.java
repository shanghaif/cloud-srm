package com.midea.cloud.srm.model.suppliercooperate.order.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <pre>
 *  到货计划报表实体类
 * </pre>
 *
 * @author wuwl18@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/12/13 23:15
 *  修改内容:
 * </pre>
 */
@Data
public class DeliveryPlanReportDTO {
    /** 接收组织**/
    private String ceeaReceiveAddress;
    /**供应商 **/
    private String vendorName;
    /** 采购员**/
    private String ceeaEmpUsername;
    /** 到货地点**/
    private String ceeaDeliveryAddress;
    /** 合同号码**/
    private String ceeaContractNo;
    /** 采购订单**/
    private String orderNumber;
    /** 行号**/
    private Long lineNum ;
    /** 物料编码**/
    private String materialCode;
    /** 物料名称**/
    private String materialName;
    /** 单位**/
    private String unit;
    /** 订单数量**/
    private BigDecimal ceeaApprovedNum;
    /** 承诺日期**/
    private Date ceeaPromiseReceiveDate;
    /** 未到货数量**/
    private BigDecimal notDeliveryNum;
    /** 实际收货数量 **/
    private BigDecimal receivedQuantity;
    /** 采购申请单号**/
    private String ceeaRequirementHeadNum;

    /** 公司名称**/
    private String ceeaCompanyName;
    /** 业务类型**/
    private String orderType;
    /** 采购申请人**/
    private String createdFullName;
    /** 采购申请备注**/
    private String ceeaDepartmentName;
    /** 申请明细备注**/
    private String comments;

    /**公司ID*/
    private Long ceeaOrgId;
    /**采购订单行ID*/
    private Long ceeaRequirementLineId;

}
