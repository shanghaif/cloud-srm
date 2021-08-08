package com.midea.cloud.srm.model.pm.po.dto;

import java.util.Date;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;

import lombok.Data;

/**
 *
 * <pre>
 * 导入订单行明细
 * </pre>
 *
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Jun 4, 202010:39:29 AM
 *  修改内容:
 *          </pre>
 */
@Data
public class ImportOrderDetailTemplateDTO {

	@ExcelProperty(index = 0, value = "合同编号")
	private String contractNo;

	@ExcelProperty(index = 1, value = "物料编码")
	private String materialCode;

	@ExcelProperty(index = 2, value = "订单数量")
	private Integer orderNum;

	@ExcelProperty(index = 3, value = "需求日期")
	@DateTimeFormat("yyyy-MM-dd")
	private Date requirementDate;

	@ExcelProperty(index = 4, value = "收货工厂")
	private String receivedFactory;

	@ExcelProperty(index = 5, value = "库存地点")
	private String inventoryPlace;

	@ExcelProperty(index = 6, value = "寄售标识")
	private String saleLabel;

	@ExcelProperty(index = 7, value = "成本类型")
	private String costType;

}
