package com.midea.cloud.srm.model.pm.ps.statement.entity;

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
@TableName("scc_ps_payment_apply_head")
public class PaymentApplyHead extends BaseEntity {

	private static final long serialVersionUID = 7489736772239507738L;

	/**
	 * 主键ID
	 */
	@TableId("PAYMENT_APPLY_HEAD_ID")
	private Long paymentApplyHeadId;

	/**
	 * 付款申请单号
	 */
	@TableField("PAYMENT_APPLY_NUMBER")
	private String paymentApplyNumber;

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
	 * 币种
	 */
	@TableField("CURRENCY")
	private String currency;

	/**
	 * 税率
	 */
	@TableField("TAX_RATE")
	private BigDecimal taxRate;

	/**
	 * 银行账号
	 */
	@TableField("BANK_ACCOUNT")
	private String bankAccount;

	/**
	 * 银行账户名
	 */
	@TableField("BANK_ACCOUNT_NAME")
	private String bankAccountName;

	/**
	 * 开户行
	 */
	@TableField("OPENING_BANK")
	private String openingBank;

	/**
	 * 总金额（未税）
	 */
	@TableField("TOTAL_AMOUNT_NO_TAX")
	private BigDecimal totalAmountNoTax;

	/**
	 * ERP付款单号
	 */
	@TableField("ERP_PAYMENT_NUMBER")
	private String erpPaymentNumber;

	/**
	 * ERP付款日期
	 */
	@TableField("ERP_PAYMENT_DATE")
	private LocalDate erpPaymentDate;

	/**
	 * 发票编号
	 */
	@TableField("INVOCIE_NUMBER")
	private String invocieNumber;

	/**
	 * 发票日期
	 */
	@TableField("INVOCIE_DATE")
	private LocalDate invocieDate;

	/**
	 * 同步状态
	 */
	@TableField("SYNC_STATUS")
	private String syncStatus;

	/**
	 * 总账日期
	 */
	@TableField("GL_DATE")
	private LocalDate glDate;

	/**
	 * 付款计划ID
	 */
	@TableId("PAYMENT_PLAN_HEAD_ID")
	private Long paymentPlanHeadId;

	/**
	 * 付款计划单号
	 */
	@TableField("PAYMENT_PLAN_NUMBER")
	private String paymentPlanNumber;

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
