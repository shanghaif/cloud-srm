package com.midea.cloud.srm.model.report.purchase.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class PurchaseCategoryDTO implements Serializable {
	
	private Long categoryId;
	/**
	 * 品类名称
	 */
	private String categoryName;
	
	/**
	 * 占比
	 */
	private BigDecimal rate;
	
	private BigDecimal purchaseAmount; //采购金额
	
	private BigDecimal warehousingAmount;//入库金额
	
	private Integer num;//排名
	
}
