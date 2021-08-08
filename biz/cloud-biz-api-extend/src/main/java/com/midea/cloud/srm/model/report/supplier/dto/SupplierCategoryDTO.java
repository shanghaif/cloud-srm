package com.midea.cloud.srm.model.report.supplier.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;
@Data
public class SupplierCategoryDTO implements Serializable {
	private Long categoryId;
	private BigDecimal num;
	
}
