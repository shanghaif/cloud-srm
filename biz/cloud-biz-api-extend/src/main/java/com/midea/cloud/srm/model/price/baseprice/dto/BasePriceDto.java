package com.midea.cloud.srm.model.price.baseprice.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.io.Serializable;

/**
*  <pre>
 *  基价表 模型
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-07-25 14:16:13
 *  修改内容:
 * </pre>
*/
@Data
@ColumnWidth(20)
public class BasePriceDto implements Serializable {

    /**
     * 组合编码
     */
    @ExcelProperty( value = "组合编码",index = 0)
    private String combinationCode;

    /**
     * 关键属性组合
     */
    @ExcelProperty( value = "关键属性组合",index = 1)
    private String keyAttributeCombination;

    /**
     * 基价版本
     */
    @ExcelProperty( value = "基价版本",index = 2)
    private String priceVersion;

    /**
     * 属性值组合
     */
    @ExcelProperty( value = "属性组合值",index = 3)
    private String attributeValueCombination;

    /**
     * 成本要素名称
     */
    @ExcelProperty( value = "要素名称",index = 4)
    private String elementName;

    /**
     * 成本要素编码
     */
    @ExcelProperty( value = "要素编码",index = 5)
    private String elementCode;

    /**
     * 成本要素类型(MATERIAL-材质,CRAFT-工艺,FEE-费用; 字典编码-COST_ELEMENT_TYPE )
     */
    @ExcelProperty( value = "要素类型",index = 6)
    private String elementType;

    /**
     * 基价
     */
    @ExcelProperty( value = "基价(不含税)",index = 7)
    private String basePrice;

    /**
     * 币种,字典: BID_TENDER_CURRENCY
     */
    @ExcelProperty( value = "币种",index = 8)
    private String clearCurrency;

    /**
     * 有效期从
     */
    @ExcelProperty( value = "生效日期",index = 9)
    private String startDate;

    /**
     * 有效期至
     */
    @ExcelProperty( value = "失效日期",index = 10)
    private String endDate;

    /**
     * 有效期至
     */
    @ExcelProperty( value = "错误信息提示",index = 11)
    private String errorMsg;

}
