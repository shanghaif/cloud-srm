package com.midea.cloud.srm.model.supplier.responsibility.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@ColumnWidth(20)
public class SupplierLeaderDTO implements Serializable {

    /**
     * 供应商编码
     */
    @NotEmpty(message = "供应商SRM编号必填")
    @ExcelProperty(value = "供应商SRM编号", index = 0)
    private String companyCode;

    /**
     * 供应商名称
     */
    @ExcelProperty(value = "供应商名称", index = 1)
    private String companyName;

    /**
     * 负责人编号（对应员工工号）
     */
    @ExcelProperty(value = "负责人工号", index = 2)
    private String responsibilityCode;

    /**
     * 负责人姓名
     */
    @ExcelProperty(value = "负责人姓名", index = 3)
    private String responsibilityName;

    /**
     * 错误信息提示
     */
    @ExcelProperty( value = "错误信息提示", index = 4)
    private String errorMsg;

}
