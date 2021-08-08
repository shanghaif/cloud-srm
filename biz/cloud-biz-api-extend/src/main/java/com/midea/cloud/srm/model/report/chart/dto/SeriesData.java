package com.midea.cloud.srm.model.report.chart.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class SeriesData {
	private String name;
	private BigDecimal value;
	public SeriesData(String name, BigDecimal value) {
		super();
		this.name = name;
		this.value = value;
	}
	public SeriesData() {
		super();
	}
	
	
	
}
