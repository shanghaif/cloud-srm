package com.midea.cloud.srm.model.report.purchase.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PurchaseDetailDTO {
	
	private String fullPathId;//路径ID
	
	private String organizationName; //组织名称
	
	private BigDecimal purchaseAmount;//采购金额
	
	private BigDecimal warehousingAmount;//入库金额
	
	private BigDecimal OrderNumber;//采购订单行
	
	private BigDecimal confirmNumber;//已确认采购订单
	
	private String categoryName;//品类名称
	
	private BigDecimal rate; //占比
	
	private Integer num;//排名
}
