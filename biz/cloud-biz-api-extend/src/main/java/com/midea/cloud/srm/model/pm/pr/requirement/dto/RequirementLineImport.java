package com.midea.cloud.srm.model.pm.pr.requirement.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

/**
 *
 * <pre>
 *  采购订单物料详细 导入DTO
 * </pre>
 *
 * @author chenjj120@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-10-4 16:17:18
 *  修改内容:
 * </pre>
 */
@Data
@ColumnWidth(40)
@HeadRowHeight(20)
public class RequirementLineImport implements Serializable {
    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "*物料编码", index = 0 )
    private String materialCode;

    @ExcelProperty(value = "*申请数量" , index = 1)
    private BigDecimal orderQuantity;

    @ExcelProperty(value = "预算单价" , index = 2)
    private BigDecimal notaxPrice;

    @ExcelProperty(value = "*需求日期" , index = 3)
    private String requirementDate;

    @ExcelProperty(value = "指定供应商编码" , index = 4)
    private String vendorCode;

    @ExcelProperty(value = "指定供应商名称" , index = 5)
    private String vendorName;

    @ExcelProperty(value = "明细备注",index = 6)
    private String comments;

    @ExcelProperty(value = "需求人",index = 7)
    private String dmandLineRequest;

    @ExcelProperty(value = "错误信息提示",index = 8)
    private String errorMsg;


    /**
     * 数据行号
     */
    @ExcelIgnore
    private int row;

}
