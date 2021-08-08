package com.midea.cloud.srm.model.base.questionairesurvey.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.util.Date;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author liuzh163@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021/4/24 23:10
 *  修改内容:
 * </pre>
 */
@Data
@ColumnWidth(15)
public class ExcelFeedBackInfoDto {
    @ExcelProperty( value = "序号", index = 0)
    private int num;

    @ExcelProperty( value = "题目序号", index = 1)
    private int titleNum;

    @ExcelProperty( value = "题目名称", index = 2)
    private String questionName;

    @ExcelProperty( value = "题目类型", index = 3)
    private String questionType;

    @ExcelProperty( value = "是否员工调查", index = 4)
    private String employeeFlag;

    @ExcelProperty( value = "员工姓名", index = 5)
    private String employeeName;

    @ExcelProperty( value = "员工账号", index = 6)
    private String UsreName;

    @ExcelProperty( value = "员工岗位", index = 7)
    private String employeeJob;

    @ExcelProperty( value = "所在部门", index = 8)
    private String department;

    @ExcelProperty( value = "供应商反馈（选项）", index = 9)
    private String result;

    @ExcelProperty( value = "供应商名称", index = 10)
    private String vendorName;

    @ExcelProperty( value = "供应商编码", index = 11)
    private String vendorCode;

    @ExcelProperty( value = "提交时间", index = 12)
    private Date lastUpdateTime;
}
