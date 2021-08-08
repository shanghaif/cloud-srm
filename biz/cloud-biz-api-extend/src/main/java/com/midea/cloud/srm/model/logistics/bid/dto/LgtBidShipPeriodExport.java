package com.midea.cloud.srm.model.logistics.bid.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
*  <pre>
 *  物流招标船期导出模板
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
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ColumnWidth(20)
public class LgtBidShipPeriodExport implements Serializable {

    private static final long serialVersionUID = 1L;

    @ExcelProperty( value = "始发地",index = 0)
    private String startAddress;

    @ExcelProperty( value = "目的地",index = 1)
    private String endAddress;

    @ExcelProperty( value = "FCL/LCL",index = 2)
    private String wholeArk;

    @ExcelProperty( value = "Mon",index = 3)
    private BigDecimal mon;

    @ExcelProperty( value = "Tue",index = 4)
    private BigDecimal tue;

    @ExcelProperty( value = "Wed",index = 5)
    private BigDecimal wed;

    @ExcelProperty( value = "Thu",index = 6)
    private BigDecimal thu;

    @ExcelProperty( value = "Fri",index = 7)
    private BigDecimal fri;

    @ExcelProperty( value = "Sat",index = 8)
    private BigDecimal sat;

    @ExcelProperty( value = "Sun",index = 9)
    private BigDecimal sun;

    @ExcelProperty( value = "Transit Time_PTP（Days）",index = 10)
    private BigDecimal transitTime;

    @ExcelProperty( value = "船公司/航空公司",index = 11)
    private String shipCompanyName;

    @ExcelProperty( value = "中转港/中转站",index = 12)
    private String transferPort;
}
