package com.midea.cloud.srm.model.report.chart.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import lombok.Data;
@Data
public class LineSeriesData implements Serializable {
	private String name;
	private String type;
	private String stack;
	private Integer yAxisIndex;
	private List<BigDecimal> data;
}
