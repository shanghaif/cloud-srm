package com.midea.cloud.srm.model.pm.ps.payment.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
*  <pre>
 *  付款申请-合同付款计划行表 模型
 * </pre>
*
* @author xiexh12@midea.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-26 11:11:28
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_ps_payment_apply_plan")
public class CeeaPaymentApplyPlan extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 付款计划行表主键ID
     */
    @TableId("PAYMENT_APPLY_PLAN_ID")
    private Long paymentApplyPlanId;

    /**
     * 付款申请头ID
     */
    @TableField("PAYMENT_APPLY_HEAD_ID")
    private Long paymentApplyHeadId;

    /**
     * 合同头ID
     */
    @TableField("CONTRACT_HEAD_ID")
    private Long contractHeadId;

    /**
     * 合同序号
     */
    @TableField("CONTRACT_NO")
    private String contractNo;

    /**
     * 合同编码
     */
    @TableField("CONTRACT_CODE")
    private String contractCode;

    /**
     * 付款计划ID(对应合同付款计划ID)
     */
    @TableField("PAY_PLAN_ID")
    private Long payPlanId;

    /**
     * 付款期数
     */
    @TableField("PAY_PERIODS")
    private BigDecimal payPeriods;

    /**
     * 合同付款阶段
     */
    @TableField("PAY_STAGE")
    private Long payStage;

    /**
     * 付款条件
     */
    @TableField("TERM_OF_PAYMENT")
    private String termOfPayment;

    /**
     * 付款账期
     */
    @TableField("PAY_ACCOUNT_PERIOD")
    private String payAccountPeriod;

    /**
     * 付款方式
     */
    @TableField("PAY_METHOD")
    private String payMethod;

    /**
     * 已付金额（未税）
     */
    @TableField("PAID_AMOUNT_NO_TAX")
    private BigDecimal paidAmountNoTax;

    /**
     * 未付金额（未税）
     */
    @TableField("UNPAID_AMOUNT_NO_TAX")
    private BigDecimal unpaidAmountNoTax;

    /**
     * 阶段付款金额（未税）
     */
    @TableField("PERIOD_PAYMENT_AMOUNT_NO_TAX")
    private BigDecimal periodPaymentAmountNoTax;

    /**
     * 本次实际付款金额（未税）
     */
    @TableField("ACTUAL_PAYMENT_AMOUNT_NO_TAX")
    private BigDecimal actualPaymentAmountNoTax;

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
    private String tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;


}
