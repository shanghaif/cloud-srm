package com.midea.cloud.srm.model.base.questionairesurvey.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

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
 *  修改日期: 2021/4/24 16:14
 *  修改内容:
 * </pre>
 */
@Data
@ColumnWidth(15)
public class ExcelSurveyResultDto {
    @ExcelProperty( value = "序号", index = 0)
    private int num;

    @ExcelProperty( value = "题目名称", index = 1)
    private String questionName;

    @ExcelProperty( value = "题目类型", index = 2)
    private String questionType;

    @ExcelProperty( value = "是否员工调查", index = 3)
    private String employeeFlag;

    @ExcelProperty( value = "答题总数", index = 4)
    private int totalCount;

    @ExcelProperty( value = "A", index = 5)
    private int countA;

    @ExcelProperty( value = "B", index = 6)
    private int countB;

    @ExcelProperty( value = "C", index = 7)
    private int countC;

    @ExcelProperty( value = "D", index = 8)
    private int countD;

    @ExcelProperty( value = "E", index = 9)
    private int countE;

    @ExcelProperty( value = "F", index = 10)
    private int countF;

    @ExcelProperty( value = "G", index = 11)
    private int countG;

    @ExcelProperty( value = "H", index = 12)
    private int countH;

    @ExcelProperty( value = "I", index = 13)
    private int countI;
}
