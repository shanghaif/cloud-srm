package com.midea.cloud.srm.model.report.chart.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
@Data
public class ChartDTO {
	private List<String> legend;
	private List<SeriesData> seriesData;
	private List<String> xAxisData;
	private List<LineSeriesData> lineSeriesData;
	
	public List<String> getLegend() {
		if (null == this.legend) {
			List<String> legend = new ArrayList<String>();
			if (null != seriesData && seriesData.size() > 0) {
				for (int i =0 ;i<seriesData.size() ;i ++) {
					legend.add(seriesData.get(i).getName());
				}
			}
			if(null != lineSeriesData && lineSeriesData.size() > 0) {
				for (int i =0 ;i<lineSeriesData.size() ;i ++) {
					legend.add(lineSeriesData.get(i).getName());
				}
			}
			return legend;
		}
		return this.legend;
	}

}
