package com.midea.cloud.srm.model.rbac.user.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <pre>
 *  供应商用户导入信息
 * </pre>
 *
 * @author chenjj120@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/10/23 15:48
 *  修改内容:
 * </pre>
 */
@Data
@ColumnWidth(50)
@HeadRowHeight(20)
public class VendorUserImport implements Serializable {
    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "*账号" ,index = 0)
    @NotEmpty(message = "账号不能为空" )
    private String userId;

    @ExcelProperty(value = "*姓名" ,index = 1)
    @NotEmpty(message = "姓名不能为空")
    private String nikeName;

    @ExcelProperty(value = "*供应商编码" , index = 2)
    @NotEmpty(message = "供应商编码不能为空")
    private String companyCode;

    @ExcelProperty(value = "手机号", index = 3)
    private String phone;

    @ExcelProperty(value = "邮箱" ,index = 4)
//    暂时可以为空 2020-10-29
//    @NotEmpty(message = "邮箱不能为空")
    private String email;

    @ExcelProperty(value = "*生效日期" ,index = 5)
    @NotEmpty(message = "生效日期不能为空")
    private String startDate;

    @ExcelProperty(value = "失效日期" ,index = 6)
    private String endDate;

    @ExcelProperty(value = "错误信息" , index = 7)
    private String errorMsg;

    /**
     * 数据行号
     */
    @ExcelIgnore
    private int row;


}
