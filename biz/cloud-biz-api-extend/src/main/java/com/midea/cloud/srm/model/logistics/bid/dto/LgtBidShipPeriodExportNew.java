package com.midea.cloud.srm.model.logistics.bid.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
public class LgtBidShipPeriodExportNew implements Serializable {

    private static final long serialVersionUID = 1L;

    @ExcelProperty( value = "始发地",index = 0)
    private String startAddress;

    @ExcelProperty( value = "目的地",index = 1)
    private String endAddress;

    @ExcelProperty( value = "车数",index = 2)
    private String charNum;

    @ExcelProperty( value = "兆瓦数",index = 3)
    private String megawatt;

    @ExcelProperty( value = "是否满足(Y/N)",index = 4)
    private String ifSatisfied;
    /**
     * 可满足情况（是否满足为否时必填）
     */
    @ExcelProperty( value = "可满足情况",index = 5)
    private String satisfiableSituation;

    @ExcelProperty( value = "特殊说明",index = 6)
    private String specialInstructions;

    @ExcelProperty( value = "备注",index = 7)
    private String remarks;

    @ExcelProperty( value = "评标结论",index = 8)
    private String bidResult;
}
