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
public class LgtBidShipPeriodImport implements Serializable {

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

    @ExcelProperty( value = "FCL/LCL",index = 12)
    private String wholeArk;

    @ExcelProperty( value = "Mon",index = 13)
    private String mon;

    @ExcelProperty( value = "Tue",index = 14)
    private String tue;

    @ExcelProperty( value = "Wed",index = 15)
    private String wed;

    @ExcelProperty( value = "Thu",index = 16)
    private String thu;

    @ExcelProperty( value = "Fri",index = 17)
    private String fri;

    @ExcelProperty( value = "Sat",index = 18)
    private String sat;

    @ExcelProperty( value = "Sun",index = 19)
    private String sun;

    @ExcelProperty( value = "Transit Time_PTP（Days）",index = 20)
    private String transitTime;

    @ExcelProperty( value = "船公司/航空公司",index = 21)
    private String shipCompanyName;

    @ExcelProperty( value = "中转港/中转站",index = 22)
    private String transferPort;

    @ExcelProperty( value = "错误信息提示",index = 23)
    private String errorMsg;
}
