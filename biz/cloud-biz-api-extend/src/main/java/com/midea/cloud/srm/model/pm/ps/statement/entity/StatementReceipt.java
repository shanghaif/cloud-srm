package com.midea.cloud.srm.model.pm.ps.statement.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import javax.validation.constraints.NotNull;

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

/**
 *
 * <pre>
 * 对账单 - 入库明细
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
@TableName("scc_sc_statement_receipt")
public class StatementReceipt extends BaseEntity {

	private static final long serialVersionUID = -6852192051476240399L;

	/**
	 * 主键ID
	 */
	@TableId("STATEMENT_RECEIPT_ID")
	private Long statementReceiptId;

	/**
	 * 对账单头ID
	 */
	@TableField("STATEMENT_HEAD_ID")
	private Long statementHeadId;

	/**
	 * 入库单ID
	 */
	@TableField("WAREHOUSE_RECEIPT_ID")
	@NotNull(groups = { InsertGroup.class, UpdateGroup.class, SubmitGroup.class }, message = "入库单ID不能为空")
	private Long warehouseReceiptId;

	/**
	 * 入库单号
	 */
	@TableField("WAREHOUSE_RECEIPT_NUMBER")
	private String warehouseReceiptNumber;

	/**
	 * 入库单行号
	 */
	@TableField("WAREHOUSE_RECEIPT_ROW_NUM")
	private Long warehouseReceiptRowNum;

	/**
	 * 发生日期
	 */
	@TableField("HAPPEN_DATE")
	private LocalDate happenDate;

	/**
	 * 订单ID
	 */
	@TableField("ORDER_ID")
	private Long orderId;

	/**
	 * 订单行ID
	 */
	@TableField("ORDER_DETAIL_ID")
	private Long orderDetailId;

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
	 * 单位
	 */
	@TableField("UNIT")
	private String unit;

	/**
	 * 采购订单号
	 */
	@TableField("ORDER_NUMBER")
	private String orderNumber;

	/**
	 * 采购订单行号
	 */
	@TableField("ORDER_LINE_NUM")
	private Long orderLineNum;

	/**
	 * 入库数量
	 */
	@TableField("WAREHOUSE_RECEIPT_QUANTITY")
	private BigDecimal warehouseReceiptQuantity;

	/**
	 * 单价（不含税）
	 */
	@TableField("UNIT_PRICE_NO_TAX")
	private BigDecimal unitPriceNoTax;

	/**
	 * 未税总金额
	 */
	@TableField("TOTAL_AMOUNT_NO_TAX")
	private BigDecimal totalAmountNoTax;

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