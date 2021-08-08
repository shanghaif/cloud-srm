package com.midea.cloud.srm.model.logistics.expense.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * <pre>
 *  费用项定义excel导入DTO
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/12/1 21:47
 *  修改内容:
 * </pre>
 */
@Data
@ColumnWidth(20)
public class ExpenseItemImportDTO {

    /**
     * 业务模式
     */
    @NotEmpty(message = "业务模式必填")
    @ExcelProperty(value = "业务模式", index = 0)
    private String businessModeName;

    /**
     * 运输方式
     */
    @NotEmpty(message = "运输方式必填")
    @ExcelProperty(value = "运输方式", index = 1)
    private String transportModeName;

    /**
     * leg
     */
    @ExcelProperty(value = "leg", index = 2)
    private String legName;

    /**
     * 费用项
     */
    @NotEmpty(message = "费用项必填")
    @ExcelProperty(value = "费用项", index = 3)
    private String chargeName;

    /**
     * 错误信息提示
     */
    @ExcelProperty( value = "错误信息提示", index = 4)
    private String errorMsg;

}
