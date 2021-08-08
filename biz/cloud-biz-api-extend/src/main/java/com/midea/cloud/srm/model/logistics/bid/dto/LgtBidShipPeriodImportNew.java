package com.midea.cloud.srm.model.logistics.bid.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.io.Serializable;

/**
*  <pre>
 *  物流招标船期导入模板
 * </pre>
*
* @author wangpr@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-01-06 09:08:34
 *  修改内容:
 * </pre>
*/
@Data
@ColumnWidth(20)
public class LgtBidShipPeriodImportNew implements Serializable {

    private static final long serialVersionUID = 1L;

    @ExcelProperty( value = "起运国",index = 0)
    private String fromCountry;

    @ExcelProperty( value = "始发省",index = 1)
    private String fromProvince;

    @ExcelProperty( value = "始发市",index = 2)
    private String fromCity;

    @ExcelProperty( value = "始发区县",index = 3)
    private String fromCounty;

    @ExcelProperty( value = "起运地",index = 4)
    private String fromPlace;

    @ExcelProperty( value = "目的国",index = 5)
    private String toCountry;

    @ExcelProperty( value = "目的省",index = 6)
    private String toProvince;

    @ExcelProperty( value = "目的市",index = 7)
    private String toCity;

    @ExcelProperty( value = "目的区县",index = 8)
    private String toCounty;

    @ExcelProperty( value = "目的地",index = 9)
    private String toPlace;

    @ExcelProperty( value = "起运港编码",index = 10)
    private String fromPort;

    @ExcelProperty( value = "目的港编码",index = 11)
    private String toPort;

    @ExcelProperty( value = "车数",index = 12)
    private String charNum;

    @ExcelProperty( value = "兆瓦数",index = 13)
    private String megawatt;

    @ExcelProperty( value = "是否满足(Y/N)",index = 14)
    private String ifSatisfied;
    /**
     * 可满足情况（是否满足为否时必填）
     */
    @ExcelProperty( value = "可满足情况",index = 15)
    private String satisfiableSituation;

    @ExcelProperty( value = "特殊说明",index = 16)
    private String specialInstructions;

    @ExcelProperty( value = "备注",index = 17)
    private String remarks;

    @ExcelProperty( value = "错误信息提示",index = 18)
    private String errorMsg;
}
