package com.midea.cloud.srm.model.report.supplier.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
@Data
public class SupplierPerformanceDTO implements Serializable {
	private Long overallScoreId;
	private Long companyId;
	private BigDecimal score;
	private String fullPathId;
	private Long categoryId;
	private Date creationDate;
	private String perStartMonth;
	private String perEndMonth;
}
