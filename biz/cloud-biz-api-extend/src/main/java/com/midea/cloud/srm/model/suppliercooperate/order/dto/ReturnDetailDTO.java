package com.midea.cloud.srm.model.suppliercooperate.order.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <pre>
 *  退货单明细 数据返回传输对象
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/18 10:54
 *  修改内容:
 *          </pre>
 */
@Data
@Accessors(chain = true)
public class ReturnDetailDTO {

	private Long returnDetailId;// 退货明细主键
	private Integer lineNum;// 退货单行号
	private String categoryName;// 采购分类
	private String materialCode;// 物料编码
	private String materialName;// 物料名称
	private BigDecimal deliveryQuantity;// 送货数量
	private BigDecimal warehouseReceiptQuantity;// 入库数量
	private BigDecimal returnNum;// 退货数量
	private Long warehouseReceiptId;// 入库单ID
	private String warehouseReceiptNumber;// 入库单编号
	private Long warehouseReceiptRowNum;// 入库单行号
	private Date confirmTime;// 确认时间
	private String orderNumber;// 采购订单编号
	private Integer orderLineNum;// 采购订单行号
	private Date deliveryDate;// 送货时间
	private String deliveryNumber;// 送货单号
	private String contactPeople;// 送货联系人
	private String contactNumber;// 联系方式
	private String receivedFactory;// 收货工厂
	private String inventoryPlace;// 库存地点
	private Long deliveryNoteDetailId;// 送货单明细行ID
	private Long orderDetailId;// 采购订单明细行ID

}
