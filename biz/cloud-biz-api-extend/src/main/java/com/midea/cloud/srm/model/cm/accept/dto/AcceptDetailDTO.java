package com.midea.cloud.srm.model.cm.accept.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.midea.cloud.srm.model.cm.accept.entity.AcceptDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AcceptDetailDTO extends AcceptDetail {



    /**
     * 采购分类ID(物料小类id)
     */
    @TableField("CATEGORY_ID")
    private Long categoryId;

    /**
     * 采购分类编码(物料小类编码)
     */
    @TableField("CATEGORY_CODE")
    private String categoryCode;

    /**
     * 采购分类全名(物料小类名称)
     */
    @TableField("CATEGORY_NAME")
    private String categoryName;



    /**
     * 收货地点
     */
    @TableField("RECEIPT_PLACE")
    private String receiptPlace;

    /**
     * 单位
     */
    @TableField("UNIT")
    private String unit;

    /**
     * 单位编码
     */
    @TableField("UNIT_CODE")
    private String unitCode;

    /**
     * 订单数量
     */
    @TableField("ORDER_NUM")
    private BigDecimal orderNum;

    /**
     * 需求日期
     */
    @TableField("REQUIREMENT_DATE")
    private LocalDate requirementDate;

    /**
     * 需求数量
     */
    @TableField("REQUIREMENT_QUANTITY")
    private BigDecimal requirementQuantity;

    /**
     * 币种id
     */
    @TableField("CURRENCY_ID")
    private Long currencyId;

    /**
     * 币种编码
     */
    @TableField("CURRENCY_CODE")
    private String currencyCode;

    /**
     * 币种名称
     */
    @TableField("CURRENCY_NAME")
    private String currencyName;


    /**
     * 订单行号
     */
    @TableField("LINE_NUM")
    private Integer lineNum;

    /**
     * 采购申请物料行主键id
     */
    @TableField("CEEA_REQUIREMENT_LINE_ID")
    private Long ceeaRequirementLineId;


    /**
     * 采购申请行号(longi)
     */
    @TableField("CEEA_ROW_NUM")
    private String ceeaRowNum;

    /**
     * 库存组织ID(longi)
     */
    @TableField("CEEA_ORGANIZATION_ID")
    private Long ceeaOrganizationId;

    /**
     * 库存组织名称(longi)
     */
    @TableField("CEEA_ORGANIZATION_NAME")
    private String ceeaOrganizationName;

    /**
     * 库存组织编码(longi)
     */
    @TableField("CEEA_ORGANIZATION_CODE")
    private String ceeaOrganizationCode;


    /**
     * 计划到货日期(longi)
     */
    @TableField("CEEA_PLAN_RECEIVE_DATE")
    private Date ceeaPlanReceiveDate;

    /**
     * 承诺到货日期(longi)
     */
    @TableField("CEEA_PROMISE_RECEIVE_DATE")
    private Date ceeaPromiseReceiveDate;

    /**
     * 含税单价(longi)
     */
    @TableField("CEEA_UNIT_TAX_PRICE")
    private BigDecimal ceeaUnitTaxPrice;

    /**
     * 不含税单价(longi)
     */
    @TableField("CEEA_UNIT_NO_TAX_PRICE")
    private BigDecimal ceeaUnitNoTaxPrice;

    /**
     * 税率编码(longi)
     */
    @TableField("CEEA_TAX_KEY")
    private String ceeaTaxKey;

    /**
     * 税率(longi)
     */
    @TableField("CEEA_TAX_RATE")
    private BigDecimal ceeaTaxRate;


    /**
     * 合同编号(longi)
     */
    @TableField("CEEA_CONTRACT_NO")
    private String ceeaContractNo;

    /**
     * 批次号
     */
    @TableField("CEEA_BATCH_NUM")
    private String ceeaBatchNum;

    /**
     * 托号
     */
    @TableField("CEEA_CONSIGNMENT_NUM")
    private String ceeaConsignmentNum;

    /**
     * 箱号
     */
    @TableField("CEEA_BOX_NUM")
    private String ceeaBoxNum;

    /**
     * 生产日期
     */
    @TableField("CEEA_PRODUCE_DATE")
    private Date ceeaProduceDate;

    /**
     * 有效日期
     */
    @TableField("CEEA_EFFECTIVE_DATE")
    private Date ceeaEffectiveDate;

    /**
     * 判断标准
     */
    @TableField("CEEA_JUDGE_STANDARD")
    private String ceeaJudgeStandard;

    /**
     * 使用状态
     */
    @TableField("CEEA_USING_STATUS")
    private String ceeaUsingStatus;

    /**
     * 物料行来源是否为采购需求
     */
    @TableField("CEEA_IF_REQUIREMENT")
    private String ceeaIfRequirement;

    /**
     * 计划到货号
     */
    @TableField("CEEA_PLAN_RECEIVE_NUM")
    private String ceeaPlanReceiveNum;

    /**
     * 已分配计划数量
     */
    @TableField("CEEA_ASSIGNED_QUANTITY")
    private  BigDecimal ceeaAssignedQuantity;

    /*确认后弃用*/
    /**
     * 物料小类ID(确认后弃用)
     */
    @TableField("CEEA_CATEGORY_ID")
    private Long ceeaCategoryId;

    /**
     * 采购分类名称(物料小类名称)(确认后弃用)
     */
    @TableField("CEEA_CATEGORY_NAME")
    private String ceeaCategoryName;
    /**
     * 主键ID
     */
    @TableId("ORDER_ID")
    private Long orderId;

    /**
     * 订单类型 字典编码：ORDER_TYPE
     */
    @TableField("ORDER_TYPE")
    private String orderType;

    /**
     * 备注
     */
    @TableField("COMMENTS")
    private String comments;

    /**
     * 供应商ID
     */
    @TableField("VENDOR_ID")
    private Long vendorId;

    /**
     * 供应商编码
     */
    @TableField("VENDOR_CODE")
    private String vendorCode;

    /**
     * 供应商名称
     */
    @TableField("VENDOR_NAME")
    private String vendorName;

    /**
     * 订单状态 字典编码：PURCHASE_ORDER
     */
    @TableField("ORDER_STATUS")
    private String orderStatus;

    /**
     * 采购员名称
     */
    @TableField("BUYER_NAME")
    private String buyerName;

    /*不使用的字段*/
    /**
     * 业务实体ID
     */
    @TableField("ORGANIZATION_ID")
    private Long organizationId;

    /**
     * 业务实体编码
     */
    @TableField("ORGANIZATION_CODE")
    private String organizationCode;

    /**
     * 业务实体名称
     */
    @TableField("ORGANIZATION_NAME")
    private String organizationName;

    /**
     * 组织全路径虚拟ID
     */
    @TableField("FULL_PATH_ID")
    private String fullPathId;

    /**
     * 订单金额
     */
    @TableField("ORDER_AMOUNT")
    private BigDecimal orderAmount;

    /**
     * 币种
     */
    @TableField("RFQ_SETTLEMENT_CURRENCY")
    private String rfqSettlementCurrency;



    /**
     * 税率编码
     */
    @TableField("TAX_KEY")
    private String taxKey;

    /**
     * 税率
     */
    @TableField("TAX_RATE")
    private BigDecimal taxRate;

    /**
     * 公司代码
     */
    @TableField("COMPANY_CODE")
    private String companyCode;

    /**
     * 供应商反馈状态 字典编码：RESPONSE_STATUS
     */
    @TableField("RESPONSE_STATUS")
    private String responseStatus;

    /*新增的字段*/
    /**
     * 订单日期
     */
    @TableField("CEEA_PURCHASE_ORDER_DATE")
    private Date ceeaPurchaseOrderDate;

    /**
     * 部门id(logni)
     */
    @TableField("CEEA_DEPARTMENT_ID")
    private Long ceeaDepartmentId;

    /**
     * 部门编码(longi)
     */
    @TableField("CEEA_DEPARTMENT_CODE")
    private String ceeaDepartmentCode;

    /**
     * 部门名称(longi)
     */
    @TableField("CEEA_DEPARTMENT_NAME")
    private String ceeaDepartmentName;



    /**
     * 收货地址(longi)
     */
    @TableField("CEEA_RECEIVE_ADDRESS")
    private String ceeaReceiveAddress;

    /**
     * 收货联系人(longi)
     */
    @TableField("CEEA_RECEIVE_CONTACTS")
    private String ceeaReceiveContacts;

    /**
     * 收货联系电话(longi)
     */
    @TableField("CEEA_RECEIVE_TELEPHONE")
    private String ceeaReceiveTelephone;

    /**
     * 收单联系人
     */
    @TableField("CEEA_RECEIVE_ORDER_CONTACT")
    private String ceeaReceiveOrderContact;

    /**
     * 收单联系电话
     */
    @TableField("CEEA_RECEIVE_ORDER_TELEPHONE")
    private String ceeaReceiveOrderTelephone;

    /**
     * 收单地址
     */
    @TableField("CEEA_RECEIVE_ORDER_ADDRESS")
    private String ceeaReceiveOrderAddress;

    /**
     * 供应商联系人(longi)
     */
    @TableField("CEEA_SUPPLIER_CONTACTS")
    private String ceeaSupplierContacts;

    /**
     * 成本类型(longi)
     */
    @TableField("CEEA_COST_TYPE")
    private String ceeaCostType;

    /**
     * 成本类型编码(对应ERP供应商地点ID)
     */
    @TableField("CEEA_COST_TYPE_CODE")
    private String ceeaCostTypeCode;

    /**
     * 合计数量(longi)
     */
    @TableField("CEEA_TOTAL_NUM")
    private Long ceeaTotalNum;

    /**
     * 合计金额含税(longi)
     */
    @TableField("CEEA_TAX_AMOUNT")
    private BigDecimal ceeaTaxAmount;

    /**
     * 合计金额不含税(longi)
     */
    @TableField("CEEA_NO_TAX_AMOUNT")
    private BigDecimal ceeaNoTaxAmount;

    /**
     * 质量保证协议编号(longi)
     */
    @TableField("CEEA_Q_A_AGREEMENT_NO")
    private String ceeaQAAgreementNo;

    /**
     * 技术协议编号(longi)
     */
    @TableField("CEEA_T_AGREEMENT_NO")
    private String ceeaTAgreementNo;


    /**
     * 账户工号(采购员员工工号)
     */
    @TableField("CEEA_EMP_NO")
    private String ceeaEmpNo;

    /**
     * 采购员id
     */
    @TableField("CEEA_EMP_USER_ID")
    private Long ceeaEmpUseId;

    /**
     * 采购员名称
     */
    @TableField("CEEA_EMP_USERNAME")
    private String ceeaEmpUsername;


    /**
     * 采购订单类型条目标识（对接erp需要使用）
     */
    @TableField("CEEA_ORDER_TYPE_IDENTIFICATION")
    private String ceeaOrderTypeIdentification;


}
