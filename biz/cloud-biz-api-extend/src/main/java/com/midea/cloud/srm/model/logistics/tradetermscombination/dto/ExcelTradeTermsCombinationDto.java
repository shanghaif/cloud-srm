package com.midea.cloud.srm.model.logistics.tradetermscombination.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.util.Date;

/**
* <pre>
 *  贸易术语组合 excel导出模型
 * </pre>
*
* @author yancj9@meicloud.com
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Mar 3, 2021 11:52:27 AM
 *  修改内容:
 * </pre>
*/

@Data
@ColumnWidth(15) //列宽
public class ExcelTradeTermsCombinationDto {
private static final long serialVersionUID = 823005L;
 /**
 * 贸易术语名称
 */
 @ExcelProperty( value = "贸易术语名称",index = 0)
 private String tradeTermsName;
 /**
 * 进出口名称
 */
 @ExcelProperty( value = "进出口名称",index = 1)
 private String importExportName;
 /**
 * LEG编码
 */
 @ExcelProperty( value = "LEG编码",index = 2)
 private String legCode;
 /**
 * 费用项
 */
 @ExcelProperty( value = "费用项",index = 3)
 private String feeName;
 /**
 * 错误信息提示
 */
 @ExcelProperty( value = "错误信息提示",index = 4)
 private String errorMsg;

}