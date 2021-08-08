package com.midea.cloud.srm.model.pm.po.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 *
 * <pre>
 * 订单入库分页列表DTO
 * </pre>
 *
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: May 25, 20203:10:59 PM
 *  修改内容:
 *          </pre>
 */
@Data
@Accessors(chain = true)
public class WarehouseReceiptPageDTO {

	private Long warehouseReceiptId;// 入库单ID
	private Long deliveryNoteId;// 送货单头ID
	private Long deliveryNoteDetailId;// 送货单行ID
	private String categoryName;// 采购分类
	private String materialCode;// 物料编码
	private String materialName;// 物料名称
	private Date deliveryDate;// 送货时间
	private BigDecimal deliveryQuantity;// 送货数量
	private BigDecimal warehouseReceiptQuantity;// 入库数量
	private String warehouseReceiptNumber;// 入库单号
	private Long warehouseReceiptRowNum;// 入库单行号
	private Date confirmTime;// 确认时间
	private String warehouseReceiptStatus;// 状态
	private String organizationName;// 采购组织
	private String orderNumber;// 采购订单编号
	private String vendorCode;// 供应商编码
	private String vendorName;// 供应商名称
	private String deliveryNumber;// 送货单号
	private String contactPeople;// 送货联系人
	private String contactNumber;// 联系方式
	private String receivedFactory;// 收货工厂
	private String inventoryPlace;// 库存地点
	private String returnOrderNumber;// 退货单号
	private Integer orderLineNum;// 订单行号
	private Long orderDetailId;// 订单行ID

}
