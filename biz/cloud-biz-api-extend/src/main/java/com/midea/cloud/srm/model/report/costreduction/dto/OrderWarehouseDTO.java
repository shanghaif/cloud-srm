package com.midea.cloud.srm.model.report.costreduction.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
@Data
public class OrderWarehouseDTO implements Serializable {
	private BigDecimal warehouseReceiptQuantity;
	private BigDecimal returnQuantity; 
	private BigDecimal unitPriceContainingTax;    
	private String currency;   
	private BigDecimal orderNum;   
	private Long vendorId;
	private String companyName;
	private String categoryName;
	private Long categoryId;
	private String organizationName;
	private String fullPathId;
	private String buyerName;
	private Date confirmTime;
	private Long materialId;
	private String materialName;
}
