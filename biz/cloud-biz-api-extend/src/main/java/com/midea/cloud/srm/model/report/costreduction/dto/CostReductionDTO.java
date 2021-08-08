package com.midea.cloud.srm.model.report.costreduction.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
@Data
public class CostReductionDTO implements Serializable {
	private BigDecimal amount;//降本金额
	private BigDecimal rate;//降本率
	private BigDecimal upAmount;//上涨物料金额
	private BigDecimal sumAmount;//总金额
	private BigDecimal targetAmount;//目标金额
	private BigDecimal targetRate;//目标率
	private String ym;//入库年月
	private BigDecimal reachRate;//达成率
	private Integer no;//排名
	private String categoryName;//品类名称
	private Long categoryId;//品类ID
	private BigDecimal amountRate;//降本金额占比
	private String companyName;//供应商名称
	private String organizationName;//组织名称
	private String materialName;//物料名称
	private Long materialId;//物料ID
	private BigDecimal materialUpAmount;//物料上涨金额
	private BigDecimal materialCrAmount;//物料降本金额
	private BigDecimal materialCrRate;//物料降本率
	private BigDecimal categoryCrAmount;//品类降本金额
	private BigDecimal categoryReachRate;//品类达成率
	private Date confirmTime;//入库时间

	private String belongRange;//所在区间
}
