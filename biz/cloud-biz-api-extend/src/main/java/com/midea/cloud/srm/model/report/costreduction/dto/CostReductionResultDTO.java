package com.midea.cloud.srm.model.report.costreduction.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.midea.cloud.srm.model.report.chart.dto.ChartDTO;

import lombok.Data;

@Data
public class CostReductionResultDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal curAmount; //目前采购金额
	private BigDecimal yoyRate; //同比率
	private BigDecimal crAmount;//采购进本金额
	private BigDecimal crTragetAmount;//目标降本金额
	private BigDecimal crRate;//采购降本率
	private BigDecimal crTragetRate;//目标降本率
	private BigDecimal upMaterialAmount;//上涨物料金额
	private BigDecimal yoyUpMaterialRate;//上涨物料金额同比率
	
	private ChartDTO monthsTrend;//月度简本趋势
	
	private ChartDTO yearCumulativeRate;//年度累计降本金额
	
	private ChartDTO categoryAmountRate; //品类降本金额区间占比
	
	private List<ReachRateDTO> categoryReachRate; //品类降本达成率排名
	
	private List<ReachRateDTO> categoryUpAmount;//品类上涨金额排名

	
}
