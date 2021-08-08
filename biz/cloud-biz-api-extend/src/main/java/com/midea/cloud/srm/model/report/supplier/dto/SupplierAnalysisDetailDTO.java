package com.midea.cloud.srm.model.report.supplier.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.midea.cloud.srm.model.common.BaseDTO;

import lombok.Data;
@Data
public class SupplierAnalysisDetailDTO extends BaseDTO {

	private String companyName; //供应商名称
	private String organizationName; //合作组织
	private Date companyRegisteredDate;//注册时间
	private Date startDate;//供方生效时间
	private Date endDate;//失效时间
	private Integer cooperationYear;//合作年限
	private String dataSources;//数据来源
	private Integer no; //排名
	private String categoryName; //合作品类
	private BigDecimal orderAmount; //累计采购金额
	private BigDecimal score; //绩效的分
	private String perStartMonth; //绩效开始月份
	private String perEndMonth; //绩效结束月份
	private String indicatorLineDes; //指标评分值
	private Date evalutionDate; //评分时间
	private String materialName; //物料名称
	private Integer cooperationVendorNum; //合作供应商数
	private Integer outVendorNum; //退出供应商数
	private String indicatorName;//指标名称
	private String scoreNickName;//评分人
	private Long vendorId;
	private String fullPathId;
	private String materialCode;
	private Long categoryId;
	private BigDecimal warehouseReceiptQuantity;
	private BigDecimal returnQuantity;
	private BigDecimal unitPriceContainingTax;
	private String currency;
	private BigDecimal orderNum;

	//品类所在区间
	private String belongRange;//所在区间
	private BigDecimal belongRangePercent;//所在区间占比
}
