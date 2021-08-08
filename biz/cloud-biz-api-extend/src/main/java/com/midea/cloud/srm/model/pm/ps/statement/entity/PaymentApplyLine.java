package com.midea.cloud.srm.model.pm.ps.statement.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 *
 * <pre>
 *
 * </pre>
 *
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Jun 11, 20202:30:21 PM
 *  修改内容:
 *          </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_ps_payment_apply_line")
public class PaymentApplyLine extends BaseEntity {

	private static final long serialVersionUID = 7489127367732207738L;

	/**
	 * 主键ID
	 */
	@TableId("PAYMENT_APPLY_LINE_ID")
	private Long paymentApplyLineId;

	/**
	 * 付款申请头ID
	 */
	@TableId("PAYMENT_APPLY_HEAD_ID")
	private Long paymentApplyHeadId;

	/**
	 * 来源类型
	 */
	@TableField("SOURCE_TYPE")
	private String sourceType;

	/**
	 * 来源单号
	 */
	@TableField("SOURCE_NUMBER")
	private String sourceNumber;

	/**
	 * 来源ID
	 */
	@TableField("SOURCE_ID")
	private Long sourceId;

	/**
	 * 合同付款阶段
	 */
	@TableField("PAY_STAGE")
	private Long payStage;

	/**
	 * 付款类型
	 */
	@TableField("PAY_TYPE")
	private String payType;

	/**
	 * 付款方式
	 */
	@TableField("PAY_METHOD")
	private String payMethod;

	/**
	 * 付款条件
	 */
	@TableField("TERM_OF_PAYMENT")
	private String termOfPayment;

	/**
	 * 税率
	 */
	@TableField("TAX_RATE")
	private BigDecimal taxRate;

	/**
	 * 总金额（未税）
	 */
	@TableField("TOTAL_AMOUNT_NO_TAX")
	private BigDecimal totalAmountNoTax;

	/**
	 * 未付金额（未税）
	 */
	@TableField("UNPAID_AMOUNT_NO_TAX")
	private BigDecimal unpaidAmountNoTax;

	/**
	 * 本次计划付款金额（未税）
	 */
	@TableField("APPLY_PAYMENT_AMOUNT_NO_TAX")
	private BigDecimal applyPaymentAmountNoTax;

	/**
	 * 付款计划头ID
	 */
	@TableField("PAYMENT_PLAN_HEAD_ID")
	private Long paymentPlanHeadId;

	/**
	 * 付款计划行ID
	 */
	@TableField("PAYMENT_PLAN_LINE_ID")
	private Long paymentPlanLineId;

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
