package com.midea.cloud.srm.model.suppliercooperate.order.entry;

import com.baomidou.mybatisplus.annotation.*;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseTax;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 *  采购订单明细表 模型
 * </pre>
 *
 * @author chensl26@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-17 13:19:32
 *  修改内容:
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sc_order_detail")
public class OrderDetail extends BaseErrorCell {
    private static final long serialVersionUID = -2263420417641653067L;

    /**
     * 主键ID
     */
    @TableId("ORDER_DETAIL_ID")
    private Long orderDetailId;

    /**
     * 订单ID
     */
    @TableField("ORDER_ID")
    private Long orderId;

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
     * 物料ID
     */
    @TableField("MATERIAL_ID")
    private Long materialId;

    /**
     * 物料编码
     */
    @TableField("MATERIAL_CODE")
    private String materialCode;

    /**
     * 物料名称
     */
    @TableField("MATERIAL_NAME")
    private String materialName;

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
     * 备注
     */
    @TableField("COMMENTS")
    private String comments;

    /**
     * 创建人ID
     */
    @TableField(value = "CREATED_ID", fill = FieldFill.INSERT)
    private Long createdId;

    /**
     * 创建人
     */
    @TableField(value = "CREATED_BY", fill = FieldFill.INSERT)
    private String createdBy;

    /**
     * 创建时间
     */
    @TableField(value = "CREATION_DATE", fill = FieldFill.INSERT)
    private Date creationDate;

    /**
     * 创建人IP
     */
    @TableField(value = "CREATED_BY_IP", fill = FieldFill.INSERT)
    private String createdByIp;

    /**
     * 最后更新人ID
     */
    @TableField(value = "LAST_UPDATED_ID", fill = FieldFill.INSERT_UPDATE)
    private Long lastUpdatedId;

    /**
     * 最后更新人
     */
    @TableField(value = "LAST_UPDATED_BY", fill = FieldFill.INSERT_UPDATE)
    private String lastUpdatedBy;

    /**
     * 最后更新时间
     */
    @TableField(value = "LAST_UPDATE_DATE", fill = FieldFill.INSERT_UPDATE)
    private Date lastUpdateDate;

    /**
     * 最后更新人IP
     */
    @TableField(value = "LAST_UPDATED_BY_IP", fill = FieldFill.INSERT_UPDATE)
    private String lastUpdatedByIp;

    /**
     * 租户ID
     */
    @TableField("TENANT_ID")
    private Long tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;

    /**
     * 订单累计收货量（送货数量）
     */
    @TableField("RECEIVE_SUM")
    private BigDecimal receiveSum;

    /**
     * 订单行号
     */
    @TableField("LINE_NUM")
    private Integer lineNum;

    /*不使用的字段*/

    /**
     * 单价（含税）(不使用)
     */
    @TableField("UNIT_PRICE_CONTAINING_TAX")
    private BigDecimal unitPriceContainingTax;

    /**
     * 单价（不含税）（不使用）
     */
    @TableField("UNIT_PRICE_EXCLUDING_TAX")
    private BigDecimal unitPriceExcludingTax;

    /**
     * 币种(不使用)
     */
    @TableField("CURRENCY")
    private String currency;

    /**
     * 交货日期(不使用)
     */
    @TableField("DELIVERY_DATE")
    private LocalDate deliveryDate;

    /**
     * 供方要求交期(不使用)
     */
    @TableField("VENDOR_DELIVERY_DATE")
    private LocalDate vendorDeliveryDate;

    /**
     * 规格型号(不使用)
     */
    @TableField("SPECIFICATION")
    private String specification;

    /**
     * 订单行状态
     * {{@link com.midea.cloud.common.enums.neworder.OrderDetailStatus}}
     * 字典值：OrderDetailStatus
     */
    @TableField("ORDER_DETAIL_STATUS")
    private String orderDetailStatus;

    /**
     * 收货工厂(不使用)
     */
    @TableField("RECEIVED_FACTORY")
    private String receivedFactory;

    /**
     * 库存地点(不使用)
     */
    @TableField("INVENTORY_PLACE")
    private String inventoryPlace;

    /**
     * 价格单位(不使用)
     */
    @TableField("PRICE_UNIT")
    private String priceUnit;

    /**
     * 寄售标识(不使用)
     */
    @TableField("SALE_LABEL")
    private String saleLabel;

    /**
     * 成本编号(不使用)
     */
    @TableField("COST_NUM")
    private String costNum;

    /**
     * 成本类型(不使用)
     */
    @TableField("COST_TYPE")
    private String costType;

    /**
     * 外部类型 CONTRACT REQUIREMENT(不使用)
     */
    @TableField("EXTERNAL_TYPE")
    private String externalType;

    /**
     * 外部ID(不使用)
     */
    @TableField("EXTERNAL_ID")
    private Long externalId;

    /**
     * 外部编号(不使用)
     */
    @TableField("EXTERNAL_NUM")
    private String externalNum;

    /**
     * 外部行ID(不使用)
     */
    @TableField("EXTERNAL_ROW_ID")
    private Long externalRowId;

    /**
     * 外部行号(不使用)
     */
    @TableField("EXTERNAL_ROW_NUM")
    private Long externalRowNum;

    /*新增加的字段*/

    /**
     * 采购申请物料行主键id
     */
    @TableField("CEEA_REQUIREMENT_LINE_ID")
    private Long ceeaRequirementLineId;

    /**
     * 采购申请编号(longi)
     */
    @TableField("CEEA_REQUIREMENT_HEAD_NUM")
    private String ceeaRequirementHeadNum;

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
     * 第一次审批通过的数量(原订单数量)
     */
    @TableField("CEEA_FIRST_APPROVED_NUM")
    private BigDecimal ceeaFirstApprovedNum;

    /**
     * 审批通过的数量
     */
    @TableField("CEEA_APPROVED_NUM")
    private BigDecimal ceeaApprovedNum;

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
     * 含税金额(longi)
     */
    @TableField("CEEA_AMOUNT_INCLUDING_TAX")
    private BigDecimal ceeaAmountIncludingTax;

    /**
     * 不含税金额(longi)
     */
    @TableField("CEEA_AMOUNT_EXCLUDING_TAX")
    private BigDecimal ceeaAmountExcludingTax;

    /**
     * 税额(longi)
     */
    @TableField("CEEA_TAX_AMOUNT")
    private BigDecimal ceeaTaxAmount;

    /**
     * 合同编号(longi-合同长码)
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
    private BigDecimal ceeaAssignedQuantity;

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
     * 验收数量(srm验收管理确认验收的数量)
     */
    @TableField("RECEIVE_NUM")
    private BigDecimal receiveNum;
    /**
     * 已接收数量(数据迁移用)
     */
    @TableField("RECEIVED_QUANTITY")
    private BigDecimal receivedQuantity;

    /**
     * 业务小类
     */
    @TableField("CEEA_BUSINESS_SMALL")
    private String ceeaBusinessSmall;

    /**
     * 业务小类编码
     */
    @TableField("CEEA_BUSINESS_SMALL_CODE")
    private String ceeaBusinessSmallCode;

    /**
     * 未开票数量(结算用)
     */
    @TableField("NOT_INVOICE_QUANTITY")
    private BigDecimal notInvoiceQuantity;

    /**
     * 本次开票数量(结算用)
     */
    @TableField("INVOICE_QUANTITY")
    private BigDecimal invoiceQuantity;

    /**
     * 净价金额(结算用)
     */
    @TableField("NO_TAX_AMOUNT")
    private BigDecimal noTaxAmount;

    /**
     * 变更前数量(与订单数量保持一致)
     */
    @TableField("BEFORE_UPDATE_QUANTITY")
    private BigDecimal beforeUpdateQuantity;

    /**
     * 物料大类编码(校验使用)
     */
    @TableField("BIG_CATEGORY_CODE")
    private String bigCategoryCode;

    /**
     * 可用税率
     */
    @TableField(exist = false)
    private List<PurchaseTax> purchaseTaxList;

    /**
     * erp采购申请编码
     */
    @TableField(exist = false)
    private String esRequirementHeadNum;

    /**
     * 是否海鲜价公式
     */
    @TableField(value = "IS_SEA_FOOD_FORMULA")
    private String isSeaFoodFormula;
    /**
     * 基价列表
     */
    @TableField(value = "PRICE_JSON")
    private String priceJson;
    /**
     * 公式值
     */
    @TableField("CEEA_FORMULA_VALUE")
    private String ceeaFormulaValue;
    /**
     * 公式结果
     */
    @TableField("CEEA_FORMULA_RESULT")
    private String ceeaFormulaResult;
    /**
     * 公式id
     */
    @TableField("CEEA_FORMULA_ID")
    private Long ceeaFormulaId;
    /**
     * 供应商要素报价明细
     */
    @TableField("ESSENTIAL_FACTOR_VALUES")
    private String essentialFactorValues;

    /**
     * 是否变更过 Y / N / null(历史数据)
     */
    @TableField("CEEA_IF_CHANGE")
    private String ceeaIfChange;

    /**
     * 变更前数量
     */
    @TableField("CEEA_BEFORE_UPDATE_NUM")
    private BigDecimal ceeaBeforeUpdateNum;

    /**
     * 价格来源类型
     */
    @TableField("CEEA_PRICE_SOURCE_TYPE")
    private String ceeaPriceSourceType;

    /**
     * 价格来源单据id(contractMaterialId, priceLibraryId)
     */
    @TableField("CEEA_PRICE_SOURCE_ID")
    private Long ceeaPriceSourceId;

    /**
     * 采购申请行需求数量
     */
    @TableField(exist = false)
    private BigDecimal requirementLineTotalQuantity;

    /**
     * 合同序号（短码）
     */
    @TableField(value = "CEEA_CONTRACT_NUM")
    private String contractNum;
    /**
     * 是否有订单结算
     */
    @TableField(value="HAS_SETTLEMENT")
    private String hasSettlement;
    /********冗余字段start，采购申请中已经存在的，pm要求添加，不知道为什么要非要在订单行中添加2020年12月10日 ***/
    /**
     * 项目编号
     */
    @TableField(value = "CEEA_PROJECT_NUM")
    private String ceeaProjectNum;
    /**
     * 项目名称
     */
    @TableField(value = "CEEA_PROJECT_NAME")
    private String ceeaProjectName;
    /**
     * 任务id
     */
    @TableField(value = "TASK_ID")
    private Long taskId;
    /**
     * 任务编号
     */
    @TableField(value = "TASK_NUMBER")
    private String taskNumber;
    /**
     * 任务名称
     */
    @TableField(value = "TASK_NAME")
    private String taskName;
    /**
     * 来源系统，如erp系统
     */
    @TableField(value = "SOURCE_SYSTEM")
    private String sourceSystem;
    /********冗余字段start end*/


    /**
     * 供应商确认时间
     */
    @TableField(value = "CONFIRM_TIME")
    private Date confirmTime;

    /**
     * 拒绝原因
     */
    @TableField(value = "REFUSED_REASON")
    private String refusedReason;

    /**
     * 订单明细id
     */
    @TableField(exist = false)
    private List<Long> ids;

    /**
     * 送货通知引用数量
     */
    @TableField(value = "DELIVERY_NOTICE_QUANTITY")
    private BigDecimal deliveryNoticeQuantity;
}
