package com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.BaseErrorCell;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <pre>
 *  罚扣款单表 模型
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/21 14:45
 *  修改内容:
 * </pre>
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sc_penalty")
public class Penalty extends BaseErrorCell {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId("PENALTY_ID")
    private Long penaltyId;

    /**
     * 罚扣款编号
     */
    @TableField("PENALTY_NUMBER")
    private String penaltyNumber;

    /**
     * 供应商ID
     */
    @TableField("VENDOR_ID")
    private Long vendorId;

    /**
     * 供应商编号
     */
    @TableField("VENDOR_CODE")
    private String vendorCode;

    /**
     * 供应商名称
     */
    @TableField("VENDOR_NAME")
    private String vendorName;

    /**
     * 采购组织ID
     */
    @TableField("ORGANIZATION_ID")
    private Long organizationId;

    /**
     * 组织全路径虚拟ID
     */
    @TableField("FULL_PATH_ID")
    private String fullPathId;

    /**
     * 组织编号
     */
    @TableField("ORGANIZATION_CODE")
    private String organizationCode;

    /**
     * 组织名称
     */
    @TableField("ORGANIZATION_NAME")
    private String organizationName;

    /**
     * 自助开票ID
     */
    @TableField("INVOICE_ID")
    private Long invoiceId;

    /**
     * 税控发票号
     */
    @TableField("INVOICE_NUMBER")
    private String invoiceNumber;

    /**
     * 发票状态
     */
    @TableField("INVOICE_STATUS")
    private String invoiceStatus;

    /**
     * 匹配发票时间
     */
    @TableField("MATCH_INVOICE_TIME")
    private Date matchInvoiceTime;

    /**
     * 匹配发票人ID
     */
    @TableField("MATCH_USER_ID")
    private Long matchUserId;

    /**
     * 匹配发票人名称
     */
    @TableField("MATCH_USER_NAME")
    private String matchUserName;

    /**
     * 对账单ID
     */
    @TableField("RECONCILIAT_ID")
    private Long reconciliatId;

    /**
     * 对账单号
     */
    @TableField("RECONCILIAT_NUMBER")
    private String reconciliatNumber;

    /**
     * 对账状态
     */
    @TableField("RECONCILIAT_STATUS")
    private String reconciliatStatus;

    /**
     * 关联对账时间
     */
    @TableField("RECONCILIAT_TIME")
    private Date reconciliatTime;

    /**
     * 关联对账人ID
     */
    @TableField("RECONCILIAT_USER_ID")
    private Long reconciliatUserId;

    /**
     * 关联对账人名称
     */
    @TableField("RECONCILIAT_USER_NAME")
    private String reconciliatUserName;

    /**
     * 罚扣款日期
     */
    @TableField("PENALTY_TIME")
    private Date penaltyTime;

    /**
     * 罚扣款类型
     */
    @TableField("PENALTY_TYPE")
    private String penaltyType;

    /**
     * 罚扣款描述
     */
    @TableField("PENALTY_COMMONS")
    private String penaltyCommons;

    /**
     * 币种
     */
    @TableField("RFQ_SETTLEMENT_CURRENCY")
    private String rfqSettlementCurrency;

    /**
     * 罚扣金额
     */
    @TableField("PENALTY_AMOUNT")
    private BigDecimal penaltyAmount;


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
     * 版本号
     */
    @TableField("VERSION")
    private Long version;

    /**
     * 租户ID
     */
    @TableField("TENANT_ID")
    private Long tenantId;
}