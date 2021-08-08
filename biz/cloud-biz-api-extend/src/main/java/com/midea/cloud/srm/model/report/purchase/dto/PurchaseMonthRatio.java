	package com.midea.cloud.srm.model.report.purchase.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;
@Data
public class PurchaseMonthRatio implements Serializable {
	
	private String month; //月份
	
	private BigDecimal deliveryAmount; //交货金额
	
	private BigDecimal orderAmount;//订单金额
	
	private BigDecimal rate; //占比
}
