package com.midea.cloud.srm.model.pm.ps.statement.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.midea.cloud.srm.model.pm.ps.statement.dto.StatementDTO.InsertGroup;
import com.midea.cloud.srm.model.pm.ps.statement.dto.StatementDTO.SubmitGroup;
import com.midea.cloud.srm.model.pm.ps.statement.dto.StatementDTO.UpdateGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

/**
 *
 * <pre>
 * 对账单头表
 * </pre>
 *
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Jun 10, 20205:02:42 PM
 *  修改内容:
 *          </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sc_statement_head")
public class StatementHead extends BaseEntity {

	private static final long serialVersionUID = 3477330873701929380L;

	/**
	 * 主键ID
	 */
	@TableId("STATEMENT_HEAD_ID")
	@NotNull(groups = { UpdateGroup.class }, message = "对账单ID不能为空")
	private Long statementHeadId;

	/**
	 * 供应商ID
	 */
	@TableField("VENDOR_ID")
	@NotNull(groups = { InsertGroup.class, UpdateGroup.class, SubmitGroup.class }, message = "供应商ID不能为空")
	private Long vendorId;

	/**
	 * 供应商编号
	 */
	@TableField("VENDOR_CODE")
	@NotNull(groups = { InsertGroup.class, UpdateGroup.class, SubmitGroup.class }, message = "供应商编码不能为空")
	private String vendorCode;

	/**
	 * 供应商名称
	 */
	@TableField("VENDOR_NAME")
	@NotNull(groups = { InsertGroup.class, UpdateGroup.class, SubmitGroup.class }, message = "供应商名称不能为空")
	private String vendorName;

	/**
	 * 采购组织ID
	 */
	@TableField("ORGANIZATION_ID")
	@NotNull(groups = { InsertGroup.class, UpdateGroup.class, SubmitGroup.class }, message = "采购组织ID不能为空")
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
	@NotNull(groups = { InsertGroup.class, UpdateGroup.class, SubmitGroup.class }, message = "采购组织编码不能为空")
	private String organizationCode;

	/**
	 * 组织名称
	 */
	@TableField("ORGANIZATION_NAME")
	@NotNull(groups = { InsertGroup.class, UpdateGroup.class, SubmitGroup.class }, message = "采购组织名称不能为空")
	private String organizationName;

	/**
	 * 结算币种
	 */
	@TableField("CURRENCY")
	@NotNull(groups = { InsertGroup.class, UpdateGroup.class, SubmitGroup.class }, message = "结算币种不能为空")
	private String currency;

	/**
	 * 税率编码
	 */
	@TableField("TAX_KEY")
	private String taxKey;

	/**
	 * 税率
	 */
	@TableField("TAX_RATE")
	@NotNull(groups = { SubmitGroup.class }, message = "税率不能为空")
	private BigDecimal taxRate;

	/**
	 * 对账状态
	 */
	@TableField("STATEMENT_STATUS")
	private String statementStatus;

	/**
	 * 提交时间
	 */
	@TableField("SUBMIT_TIME")
	private LocalDate submitTime;

	/**
	 * 驳回原因
	 */
	@TableField("REJECT_REASON")
	private String rejectReason;

	/**
	 * 对账开始日期
	 */
	@TableField("STATEMENT_START_TIME")
	@NotNull(groups = { SubmitGroup.class }, message = "对账开始日期不能为空")
	private LocalDate statementStartTime;

	/**
	 * 对账结束日期
	 */
	@TableField("STATEMENT_END_TIME")
	@NotNull(groups = { SubmitGroup.class }, message = "对账结束日期不能为空")
	private LocalDate statementEndTime;

	/**
	 * 付款方式
	 */
	@TableField("PAYMENT_METHOD")
	@NotNull(groups = { SubmitGroup.class }, message = "付款方式不能为空")
	private String paymentMethod;

	/**
	 * 付款条件
	 */
	@TableField("TERM_OF_PAYMENT")
	@NotNull(groups = { SubmitGroup.class }, message = "付款条件不能为空")
	private String termOfPayment;

	/**
	 * 入库总金额（未税）
	 */
	@TableField("RECEIPT_AMOUNT")
	private BigDecimal receiptAmount;

	/**
	 * 退货总金额（未税）
	 */
	@TableField("RETURN_AMOUNT")
	private BigDecimal returnAmount;

	/**
	 * 对账总金额（未税）
	 */
	@TableField("STATEMENT_TOTAL_AMOUNT")
	private BigDecimal statementTotalAmount;

	/**
	 * 已付总金额
	 */
	@TableField("PAID_TOTAL_AMOUNT")
	private BigDecimal paidTotalAmount;

	/**
	 * 全部付款完成标志
	 */
	@TableField("PAID_FINISHED")
	private String paidFinished;
	/**
	 * 对账单号
	 */
	@TableField("STATEMENT_NUMBER")
	private String statementNumber;

	/**
	 * 需方备注
	 */
	@TableField("PURCHASER_NOTE")
	private String purchaserNote;

	/**
	 * 供方备注
	 */
	@TableField("SUPPLIER_NOTE")
	private String supplierNote;

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