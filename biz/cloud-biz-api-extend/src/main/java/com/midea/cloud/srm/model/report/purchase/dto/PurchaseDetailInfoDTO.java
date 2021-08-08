package com.midea.cloud.srm.model.report.purchase.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class PurchaseDetailInfoDTO implements Serializable {
	private String orderStatus; //订单状态
	private String organizationName; //采购组织
	private String orderNumber; //采购订单编码
	private String buyerName; //采购员
	private String currency; //币种
	private BigDecimal taxRate; //税率
	private Date creationDate; //创建日期
	private String jitOrder; //JIT订单
	private String orderType; //订单类型
	private BigDecimal unitPriceContainingTax; //订单含税单价
	private Date requirementDate; //需求日期
	private String categoryName; //品类名称
	private Integer level; //品类层级
	private String materialCode; //物料编码
	private String materialName; //物料名称
	private String unit; //单位
	private Date comfirmTime; //订单确认时间
	private String warehouseReceiptStatus; //入库状态
	private String warehouseReceiptNumber; //入库单号
	private BigDecimal warehouseReceiptQuantity; //入库数量
	private Date warehouseReceiptTime; //入库时间
	private String receivedFactory; //收货工厂
	private String inventoryPlace; //库存地点
	private String comments; //备注
}
