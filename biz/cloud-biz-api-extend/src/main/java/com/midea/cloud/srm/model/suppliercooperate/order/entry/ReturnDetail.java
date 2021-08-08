package com.midea.cloud.srm.model.suppliercooperate.order.entry;

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
 * <pre>
 *  退货单明细表 模型
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/21 14:45
 *  修改内容:
 *          </pre>
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sc_return_detail")
public class ReturnDetail extends BaseEntity {

	private static final long serialVersionUID = 8813254628844491946L;

	/**
	 * 主键ID
	 */
	@TableId("RETURN_DETAIL_ID")
	private Long returnDetailId;

	/**
	 * 行号
	 */
	@TableField("LINE_NUM")
	private Integer lineNum;

	/**
	 * 送货单明细ID
	 */
	@TableField("DELIVERY_NOTE_DETAIL_ID")
	private Long deliveryNoteDetailId;

	/**
	 * 退货单ID
	 */
	@TableField("RETURN_ORDER_ID")
	private Long returnOrderId;

	/**
	 * 退货数量
	 */
	@TableField("RETURN_NUM")
	private BigDecimal returnNum;

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

	/**
	 * 入库单ID
	 */
	@TableField("WAREHOUSE_RECEIPT_ID")
	private Long warehouseReceiptId;

	/**
	 * 订单行ID
	 */
	@TableField("ORDER_DETAIL_ID")
	private Long orderDetailId;

	/**
	 * 对账单引用
	 */
	@TableField("STATEMENT_REFER")
	private String statementRefer;

	/**
	 * 送货单号
	 */
	@TableField(exist = false)
	private String deliveryNumber;

	/**
	 * 送货单行号
	 */
	@TableField(exist = false)
	private Integer deliveryNoteLineNum;

	/**
	 * 订单号
	 */
	@TableField(exist = false)
	private String orderNumber;

	/**
	 * 订单行号
	 */
	@TableField(exist = false)
	private Integer orderLineNum;

	/**
	 * 物料分类id
	 */
	@TableField(exist = false)
	private Long categoryId;

	/**
	 * 物料分类名称
	 */
	@TableField(exist = false)
	private String categoryName;

	/**
	 * 物料分类编码
	 */
	@TableField(exist = false)
	private String categoryCode;

	/**
	 * 物料id
	 */
	@TableField(exist = false)
	private Long materialId;

	/**
	 * 物料名称
	 */
	@TableField(exist = false)
	private String materialName;

	/**
	 * 物料编码
	 */
	@TableField(exist = false)
	private String materialCode;

	/**
	 * 送货数量
	 */
	@TableField(exist = false)
	private BigDecimal deliveryQuantity;
}
