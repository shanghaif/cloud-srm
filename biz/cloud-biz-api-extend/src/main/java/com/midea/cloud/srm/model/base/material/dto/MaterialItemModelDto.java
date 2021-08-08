package com.midea.cloud.srm.model.base.material.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <pre>
 *  功能名称 采购物料维护EXCEL导入模型
 * </pre>
 *
 * @author haiping2.li@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/23 11:22
 *  修改内容:
 * </pre>
 */
@Data
@ColumnWidth(20)
@HeadRowHeight(-10)
public class MaterialItemModelDto implements Serializable {


	@ExcelProperty(value = "物料编码", index = 0)
	private String materialCode;

	@ExcelProperty(value = "物料描述", index = 1)
	private String materialName;

	@ExcelProperty(value = "品类", index = 2)
	private String categoryName;

	@ExcelProperty(value = "规格/型号", index = 3)
	private String specification;

	@ExcelProperty(value = "最小起订量", index = 4)
	private BigDecimal ceeaOrderQuantityMinimum;

	@ExcelProperty(value = "送货周期", index = 5)
	private String ceeaDeliveryCycle;

	@ExcelProperty(value = "重量", index = 6)
	private String ceeaWeight;

	@ExcelProperty(value = "尺寸", index = 7)
	private String ceeaSize;

	@ExcelProperty(value = "品牌", index = 8)
	private String ceeaBrand;

	@ExcelProperty(value = "颜色", index = 9)
	private String ceeaColor;

	@ExcelProperty(value = "备件材质", index = 10)
	private String ceeaTexture;

	@ExcelProperty(value = "备件使用用途", index = 11)
	private String ceeaUsage;
}
