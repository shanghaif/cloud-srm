package com.midea.cloud.srm.model.report.costreduction.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;
@Data
public class ReachRateDTO implements Serializable {
	private String categoryName;
	private BigDecimal amount;
	private BigDecimal rate;
}
