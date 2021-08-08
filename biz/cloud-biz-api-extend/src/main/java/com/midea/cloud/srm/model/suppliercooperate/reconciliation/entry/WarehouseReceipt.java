package com.midea.cloud.srm.model.suppliercooperate.reconciliation.entry;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <pre>
 *  入库单表 模型
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
@TableName("scc_sc_warehouse_receipt")
public class WarehouseReceipt extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@TableId("WAREHOUSE_RECEIPT_ID")
	private Long warehouseReceiptId;

	/**
	 * 入库单编号
	 */
	@TableField("WAREHOUSE_RECEIPT_NUMBER")
	private String warehouseReceiptNumber;

	/**
	 * 供应商id
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
	 * 业务实体id
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
	 * 送货地址
	 */
	@TableField("DELIVERY_ADDRESS")
	private String deliveryAddress;

	/**
	 * 收单地址
	 */
	@TableField("RECEIVE_ORDER_ADDRESS")
	private String receiveOrderAddress;

	/**
	 * 收货地址
	 */
	@TableField("RECEIVE_ADDRESS")
	private String receiveAddress;

	/**
	 * 入库日期
	 */
	@TableField("WAREHOUSE_DATE")
	private Date warehouseDate;

	/**
	 * 入库状态
	 * {{@link com.midea.cloud.common.enums.neworder.WarehouseReceiptStatus}}
	 * 字典：WAREHOUSE_RECEIPT_STATUS
	 */
	@TableField("WAREHOUSE_RECEIPT_STATUS")
	private String warehouseReceiptStatus;

	/**
	 * 备注
	 */
	@TableField("COMMENTS")
	private String comments;

	/**
	 * 冲销标识
	 */
	@TableId("WRITE_OFF")
	private String writeOff;


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

//	/**
//	 * 送货单头ID
//	 */
//	@TableId("DELIVERY_NOTE_ID")
//	private Long deliveryNoteId;
//
//	/**
//	 * 送货单行ID
//	 */
//	@TableId("DELIVERY_NOTE_DETAIL_ID")
//	private Long deliveryNoteDetailId;
//
//	/**
//	 * 入库单行号
//	 */
//	@TableId("WAREHOUSE_RECEIPT_ROW_NUM")
//	private Long warehouseReceiptRowNum;
//
//	/**
//	 * 确认时间
//	 */
//	@TableId("CONFIRM_TIME")
//	private Date confirmTime;
//
//	/**
//	 * 入库数量
//	 */
//	@TableId("WAREHOUSE_RECEIPT_QUANTITY")
//	private BigDecimal warehouseReceiptQuantity;
//
//
//	/**
//	 * 原入库单ID(冲销对应的单据)
//	 */
//	@TableId("ORIGINAL_ID")
//	private Long originalId;
//
//	/**
//	 * 订单行ID
//	 */
//	@TableId("ORDER_DETAIL_ID")
//	private Long orderDetailId;
//
//	/**
//	 * 对账单引用
//	 */
//	@TableField("STATEMENT_REFER")
//	private String statementRefer;

}
