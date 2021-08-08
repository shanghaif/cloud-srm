package com.midea.cloud.srm.model.report.supplier.dto;

import java.math.BigDecimal;

import com.midea.cloud.srm.model.report.chart.dto.ChartDTO;

import lombok.Data;
@Data
public class SupplierAnalysisDTO {
	private BigDecimal sum;//总数量
	private BigDecimal activeNum;//活跃数量
	private BigDecimal addNum;//新增数量
	private BigDecimal outNum;//退出数量
	private ChartDTO cooperation; //供应商合作年限分布
	private ChartDTO purchase;//采购金额供方数量占比
	private ChartDTO chinaMap;//供应商地图分布
	private ChartDTO purchaseRank;//采购金额供方排名
	private ChartDTO level;//供应商等级占比
	private ChartDTO category;//品类供货供方数占比
}
