package com.midea.cloud.srm.model.suppliercooperate.invoice.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

/**
*  <pre>
 *  开票通知表 模型
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-06-27 11:46:26
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sc_invoice_notice")
public class InvoiceNotice extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键,开票通知ID
     */
    @TableId("INVOICE_NOTICE_ID")
    private Long invoiceNoticeId;

    /**
     * 组织ID(业务实体ID)
     */
    @TableField("ORG_ID")
    private Long orgId;

    /**
     * 组织全路径虚拟ID
     */
    @TableField("FULL_PATH_ID")
    private String fullPathId;

    /**
     * 组织编码(业务实体编码)
     */
    @TableField("ORG_CODE")
    private String orgCode;

    /**
     * 组织名称(业务实体名称)
     */
    @TableField("ORG_NAME")
    private String orgName;

    /**
     * 开票通知单号
     */
    @TableField("INVOICE_NOTICE_NUMBER")
    private String invoiceNoticeNumber;

    /**
     * 开票通知单状态
     */
    @TableField("INVOICE_NOTICE_STATUS")
    private String invoiceNoticeStatus;

    /**
     * 供应商ID
     */
    @TableField("VENDOR_ID")
    private Long vendorId;

    /**
     * 供应商名称
     */
    @TableField("VENDOR_NAME")
    private String vendorName;

    /**
     * 供应商编码
     */
    @TableField("VENDOR_CODE")
    private String vendorCode;

    /**
     * 成本类型
     */
    @TableField("CEEA_COST_TYPE")
    private String ceeaCostType;

    /**
     * 成本类型编码(对应ERP供应商地点ID)
     */
    @TableField("CEEA_COST_TYPE_CODE")
    private String ceeaCostTypeCode;

    /**
     * 开票日期
     */
    @TableField("CEEA_INVOICE_DATE")
    private LocalDate ceeaInvoiceDate;

    /**
     * 接收开始日期
     */
    @TableField("CEEA_RECEIVE_START_DATE")
    private LocalDate ceeaReceiveStartDate;

    /**
     * 接收结束日期
     */
    @TableField("CEEA_RECEIVE_END_DATE")
    private LocalDate ceeaReceiveEndDate;

    /**
     * 净价总金额
     */
    @TableField("CEEA_NO_TAX_TOTAL_AMOUNT")
    private BigDecimal ceeaNoTaxTotalAmount;

    /**
     * 含税总金额
     */
    @TableField("CEEA_TAX_TOTAL_AMOUNT")
    private BigDecimal ceeaTaxTotalAmount;

    /**
     * 总税额
     */
    @TableField("CEEA_TOTAL_TAX")
    private BigDecimal ceeaTotalTax;

    /**
     * 汇率
     */
    @TableField("CEEA_EXCHANGE_RATE")
    private BigDecimal ceeaExchangeRate;

    /**
     * 是否需要供应商确认
     */
    @TableField("CEEA_IF_SUPPLIER_CONFIRM")
    private String ceeaIfSupplierConfirm;

    /**
     * 对账单总额(未税)(隆基不需要)
     */
    @TableField("STATEMENT_TOTAL_AMOUNT")
    private BigDecimal statementTotalAmount;

    /**
     * 税控机发票总额(未税)(隆基不需要)
     */
    @TableField("INVOICE_TOTAL_AMOUNT")
    private BigDecimal invoiceTotalAmount;

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
     * 结算币种
     */
    @TableField("CURRENCY_ID")
    private Long currencyId;

    /**
     * 结算币种
     */
    @TableField("CURRENCY_CODE")
    private String currencyCode;

    /**
     * 结算币种
     */
    @TableField("CURRENCY_NAME")
    private String currencyName;

    /**
     * 来源单号(可多个来源单号  用,分隔)
     */
    @TableField("SOURCE_NUMBER")
    private String sourceNumber;

    /**
     * 备注
     */
    @TableField("REMARK")
    private String remark;

    /**
     * 驳回原因
     */
    @TableField("REJECT_REASON")
    private String rejectReason;

    /**
     * 生效日期(YYYY-MM-DD)
     */
    @TableField("START_DATE")
    private LocalDate startDate;

    /**
     * 失效日期(YYYY-MM-DD)
     */
    @TableField("END_DATE")
    private LocalDate endDate;

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
    @TableField(value = "LAST_UPDATED_ID", fill = FieldFill.UPDATE)
    private Long lastUpdatedId;

    /**
     * 最后更新人
     */
    @TableField(value = "LAST_UPDATED_BY", fill = FieldFill.UPDATE)
    private String lastUpdatedBy;

    /**
     * 最后更新时间
     */
    @TableField(value = "LAST_UPDATE_DATE", fill = FieldFill.INSERT_UPDATE)
    private Date lastUpdateDate;

    /**
     * 最后更新人IP
     */
    @TableField(value = "LAST_UPDATED_BY_IP", fill = FieldFill.UPDATE)
    private String lastUpdatedByIp;

    /**
     * 租户ID
     */
    @TableField("TENANT_ID")
    private String tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;


}
