package com.midea.cloud.srm.model.suppliercooperate.order.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 *
 * <pre>
 * 退货订单列表
 * </pre>
 *
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: May 29, 202011:01:12 AM
 *  修改内容:
 *          </pre>
 */
@Data
public class ReturnOrderPageDTO {

	private Long returnOrderId;// 退货单ID
	private String returnOrderNumber;// 退货单号
	private String returnStatus;// 退货单状态
	private Long organizationId;// 采购组织ID
	private String fullPathId; // 组织全路径虚拟ID
	private String organizationCode;// 采购组织编码
	private String organizationName;// 采购组织名称
	private Long materialId;//
	private String materialCode;// 物料编码
	private String materialName;// 物料名称
	private BigDecimal returnNum;// 退货数量
	private BigDecimal warehouseReceiptQuantity;// 入库数量
	private String warehouseReceiptNumber;// 入库单号
	private Long warehouseReceiptRowNum;// 入库单行号
	private String deliveryNumber;// 送货单号
	private Integer deliveryLineNum;// 送货单行号
	private String orderNumber;// 采购订单号
	private Integer orderLineNum;// 采购订单行号
	private Long vendorId;// 供应商ID
	private String vendorCode;// 供应商编码
	private String vendorName;// 供应商名称
	private String receivedFactory;// 收货工厂
	private String inventoryPlace;// 库存地点
	private Long orderId;// 订单ID
	private Long orderDetailId;// 订单明细行ID
	private Long returnDetailId;// 退货订单行ID

}
