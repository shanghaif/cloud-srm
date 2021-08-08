package com.midea.cloud.srm.model.pm.ps.statement.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;
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
 *  修改日期: Jun 12, 20203:15:13 PM
 *  修改内容:
 *          </pre>
 */
@Data
@Accessors(chain = true)
public class StatementReceiptDTO {

	private Long warehouseReceiptId;// 入库单ID
	private String warehouseReceiptNumber;// 入库单号
	private Long warehouseReceiptRowNum;// 入库单行号
	private LocalDate happenDate;// 发生日期
	private Long orderId;// 订单ID
	private Long orderDetailId;// 订单行ID
	private Long materialId;// 物料ID
	private String materialCode;// 物料编码
	private String materialName;// 物料名称
	private String unit;// 单位
	private String orderNumber;// 采购订单号
	private Long orderLineNum;// 采购订单行号
	private BigDecimal warehouseReceiptQuantity;// 入库数量
	private BigDecimal unitPriceNoTax;// 单价（不含税）
	private BigDecimal totalAmountNoTax;// 未税总金额

}