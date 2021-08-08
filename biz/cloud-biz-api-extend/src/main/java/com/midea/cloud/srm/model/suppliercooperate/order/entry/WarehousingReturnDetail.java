package com.midea.cloud.srm.model.suppliercooperate.order.entry;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.time.LocalDate;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
*  <pre>
 *  入库退货明细 模型
 * </pre>
*
* @author chenwj92@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-24 13:09:49
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_storage_return")
public class WarehousingReturnDetail extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 入库/退货明细id
     */
    @TableId("WAREHOUSING_RETURN_DETAIL_ID")
    private Long warehousingReturnDetailId;

    /**
     * 入库单明细id
     */
    @TableField("WAREHOUSE_RECEIPT_DETAIL_ID")
    private Long warehouseReceiptDetailId;

    /**
     * 事务处理类型
     * {{@link com.midea.cloud.common.enums.neworder.WarehousingReturnDetailStatus}}
     */
    @TableField("TYPE")
    private String type;

    /**
     * 接收单号
     */
    @TableField("RECEIVE_ORDER_NO")
    private String receiveOrderNo;

    /**
     * 接收行号
     */
    @TableField("RECEIVE_ORDER_LINE_NO")
    private Integer receiveOrderLineNo;

    /**
     * 事务处理日期 todo delete
     */
    @TableField("DEAL_DATE")
    private LocalDate dealDate;

    /**
     * 业务实体ID
     */
    @TableField("ORG_ID")
    private Long orgId;

    /**
     * 业务实体编码
     */
    @TableField("ORG_CODE")
    private String orgCode;

    /**
     * 业务实体名称
     */
    @TableField("ORG_NAME")
    private String orgName;

    /**
     * 库存组织ID
     */
    @TableField("ORGANIZATION_ID")
    private Long organizationId;

    /**
     * 库存组织编码
     */
    @TableField("ORGANIZATION_CODE")
    private String organizationCode;

    /**
     * 库存组织名称
     */
    @TableField("ORGANIZATION_NAME")
    private String organizationName;

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
     * 物料小类ID
     */
    @TableField("CATEGORY_ID")
    private Long categoryId;

    /**
     * 物料小类名称
     */
    @TableField("CATEGORY_NAME")
    private String categoryName;

    /**
     * 物料小类编码
     */
    @TableField("CATEGORY_CODE")
    private String categoryCode;

    /**
     * 物料ID
     */
    @TableField("ITEM_ID")
    private Long itemId;

    /**
     * 物料编码
     */
    @TableField("ITEM_CODE")
    private String itemCode;

    /**
     * 物料名称
     */
    @TableField("ITEM_NAME")
    private String itemName;

    /**
     * 单位名称
     */
    @TableField("UNIT")
    private String unit;

    /**
     * 单位编码
     */
    @TableField("UNIT_CODE")
    private String unitCode;

    /**
     * 接收数量
     */
    @TableField("RECEIVE_NUM")
    private BigDecimal receiveNum;

    /**
     * 采购申请单号
     */
    @TableField("REQUIREMENT_HEAD_NUM")
    private String requirementHeadNum;

    /**
     * 申请行号
     */
    @TableField("ROW_NUM")
    private Integer rowNum;

    /**
     * 采购订单号(优先erp订单编号，如果空存srm编号)
     */
    @TableField("ORDER_NUMBER")
    private String orderNumber;

    /**
     * srm采购订单号
     */
    @TableField("SRM_ORDER_NUMBER")
    private String srmOrderNumber;

    /**
     * 采购结算使用订单号
     */
    @TableField("SETTLEMENT_ORDER_NUMBER")
    private String settlementOrderNumber;

    /**
     * 订单行号
     */
    @TableField("LINE_NUM")
    private Integer lineNum;

    /**
     * 合同编号
     */
    @TableField("CONTRACT_NO")
    private String contractNo;

    /**
     * 合同编码
     */
    @TableField("CONTRACT_CODE")
    private String contractCode;

    /**
     * 合同表头id
     */
    @TableField("CONTRACT_HEAD_ID")
    private Long contractHeadId;

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
     * 创建日期
     */
    @TableField(value = "CREATION_DATE", fill = FieldFill.INSERT)
    private Date creationDate;

    /**
     * 创建人IP
     */
    @TableField(value = "CREATED_BY_IP", fill = FieldFill.INSERT)
    private String createdByIp;

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
     * 税率编码
     */
    @TableField("TAX_KEY")
    private String taxKey;

    /**
     * 税率计算值
     */
    @TableField("TAX_RATE")
    private BigDecimal taxRate;

    /**
     * 项目编码
     */
    @TableField("PROJECT_NUM")
    private String projectNum;

    /**
     * 项目名称
     */
    @TableField("PROJECT_NAME")
    private String projectName;

    /**
     * 任务编号
     */
    @TableField("TASK_NUM")
    private String taskNum;

    /**
     * 任务名称
     */
    @TableField("TASK_NAME")
    private String taskName;

    /**
     * 未开票数量
     */
    @TableField("NOT_INVOICE_QUANTITY")
    private BigDecimal notInvoiceQuantity;

    /**
     * 入库数量
     */
    @TableField("STORAGE_QUANTITY")
    private BigDecimal storageQuantity;

    /**
     * 单价（含税）
     */
    @TableField("UNIT_PRICE_CONTAINING_TAX")
    private BigDecimal unitPriceContainingTax;

    /**
     * 单价（不含税）
     */
    @TableField("UNIT_PRICE_EXCLUDING_TAX")
    private BigDecimal unitPriceExcludingTax;

    /**
     * 币种
     */
    @TableField("CURRENCY_ID")
    private Long currencyId;

    /**
     * 币种名称
     */
    @TableField("CURRENCY_NAME")
    private String currencyName;

    /**
     * 币种编码
     */
    @TableField("CURRENCY_CODE")
    private String currencyCode;

    /**
     * 处理次数
     */
    @TableField("DEAL_NUM")
    private Integer dealNum;

    /**
     * 接收日期
     */
    @TableField("RECEIVE_DATE")
    private Date receiveDate;

    /**
     * 入库日期
     */
    @TableField("WAREHOUSING_DATE")
    private Date warehousingDate;

    /**
     * 退货至接收日期
     */
    @TableField("RETURN_TO_RECEIVING_DATE")
    private Date returnToReceivingDate;

    /**
     * 退货至供应商日期
     */
    @TableField("RETURN_TO_SUPPLIER_DATE")
    private Date returnToSupplierDate;

    /**
     * 采购订单行ID
     */
    @TableField("PO_LINE_ID")
    private Long poLineId;

    /**
     * 退货至接收 数量
      */
    @TableField("RETURN_TO_RECEIVING_NUM")
    private BigDecimal returnToReceivingNum;

    /**
     * 退货至供应商 数量
     */
    @TableField("RETURN_TO_SUPPLIER_NUM")
    private BigDecimal returnToSupplierNum;

    /**
     * 是否已计算返利
     */
    @TableField("IF_CALCULATED")
    private String ifCalculated;

    /**
     * 净价金额
     */
    @TableField(exist = false)
    private BigDecimal noTaxAmount;

    /**
     * 税额
     */
    @TableField(exist = false)
    private BigDecimal tax;

    /**
     * 含税金额
     */
    @TableField(exist = false)
    private BigDecimal taxAmount;

    /**
     * erp业务实体id
     */
    @TableField("ERP_ORG_ID")
    private Long erpOrgId;

    /**
     * erp库存组织id
     */
    @TableField("ERP_ORGANIZATION_ID")
    private Long erpOrganizationId;

    /**
     * erp库存组织编码
     */
    @TableField("ERP_ORGANIZATION_CODE")
    private String erpOrganizationCode;

    /**
     * erp供应商id
     */
    @TableField("ERP_VENDOR_ID")
    private Long erpVendorId;

    /**
     * erp供应商编码
     */
    @TableField("ERP_VENDOR_CODE")
    private String erpVendorCode;

    /**
     * 事务处理类型id
     */
    @TableField("TXN_ID")
    private Long txnId;

    /**
     * 发运行Id
     */
    @TableField("SHIP_LINE_ID")
    private Long shipLineId;

    /**
     * 采购发运行号
     */
    @TableField("SHIP_LINE_NUM")
    private Long shipLineNum;

    /**
     * 事务处理类型id(父事务处理类型id)  Y
     */
    @TableField("PARENT_TXN_ID")
    private Long parentTxnId;

    /**
     * 是否启用
     */
    @TableField("ENABLE")
    private String enable;

    /**
     * ASN编号
     */
    @TableField("ASN_NUMBER")
    private String asnNumber;

    /**
     * ASN行编号
     */
    @TableField("ASN_LINE_NUM")
    private String asnLineNum;

    /**
     * 采购员名称
     */
    @TableField(exist = false)
    private String ceeaEmpUsername;

    /**
     * 送货单行号
     */
    @TableField("DELIVERY_LINE_NUM")
    private Integer deliveryLineNum;

    /**
     * 送货单号
     */
    @TableField("DELIVERY_NUMBER")
    private String deliveryNumber;
}
