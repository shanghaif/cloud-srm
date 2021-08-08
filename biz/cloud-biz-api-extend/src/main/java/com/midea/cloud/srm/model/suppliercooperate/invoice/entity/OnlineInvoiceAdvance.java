package com.midea.cloud.srm.model.suppliercooperate.invoice.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.time.LocalDate;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
*  <pre>
 *  网上开票-预付款 模型
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-31 16:51:12
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_sc_online_invoice_advance")
public class OnlineInvoiceAdvance extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID,网上开票预付款ID
     */
    @TableId("ONLINE_INVOICE_ADVANCE_ID")
    private Long onlineInvoiceAdvanceId;

    /**
     * 网上开票ID
     */
    @TableField("ONLINE_INVOICE_ID")
    private Long onlineInvoiceId;

    /**
     * 预付款申请头ID
     */
    @TableField("ADVANCE_APPLY_HEAD_ID")
    private Long advanceApplyHeadId;

    /**
     * 预付款申请单号
     */
    @TableField("ADVANCE_APPLY_NUM")
    private String advanceApplyNum;

    /**
     * 合同序号
     */
    @TableField("CONTRACT_NUM")
    private String contractNum;

    /**
     * 合同编号
     */
    @TableField("CONTRACT_CODE")
    private String contractCode;

    /**
     * 合同头信息ID
     */
    @TableId("CONTRACT_HEAD_ID")
    private Long contractHeadId;

    /**
     * 单据日期(申请日期)
     */
    @TableField("APPLY_DATE")
    private LocalDate applyDate;

    /**
     * 项目编号
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
     * 业务类型
     */
    @TableField("BUSINESS_TYPE")
    private String businessType;

    /**
     * 货币ID
     */
    @TableField("CURRENCY_ID")
    private Long currencyId;

    /**
     * 货币编码
     */
    @TableField("CURRENCY_CODE")
    private String currencyCode;

    /**
     * 货币名称
     */
    @TableField("CURRENCY_NAME")
    private String currencyName;

    /**
     * 挂账金额
     */
    @TableField("HANG_ACCOUNT_AMOUNT")
    private BigDecimal hangAccountAmount;

    /**
     * 可用金额
     */
    @TableField("USABLE_AMOUNT")
    private BigDecimal usableAmount;

    /**
     * 锁定可用金额
     */
    @TableField("LOCK_USABLE_AMOUNT")
    private BigDecimal lockUsableAmount;

    /**
     * 本次核销金额
     */
    @TableField("CHARGE_OFF_AMOUNT")
    private BigDecimal chargeOffAmount;

    /**
     * 累计核销金额(废弃)
     */
    @TableField("TOTAL_CHARGE_OFF_AMOUNT")
    private BigDecimal totalChargeOffAmount;

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
    private String tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;


}
