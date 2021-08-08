package com.midea.cloud.srm.model.base.questionairesurvey.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

/**
 * <pre>
 *  问卷调查  问题导入模板
 * </pre>
 *
 * @author liuzh163@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021/4/23 10:13
 *  修改内容:
 * </pre>
 */
@Data
public class ExcelSurveyQuestionDto {
    private static final long serialVersionUID = 703220L;

    @ExcelProperty( value = "*题目名称", index = 0)
    private String questionName;

    @ExcelProperty( value = "*题目类型(单选题、多选题)", index = 1)
    private String questionType;

    @ExcelProperty( value = "是否员工调查（是、否）", index = 2)
    private String employeeFlag;

    @ExcelProperty( value = "员工调研范围（用英文';'分隔）", index = 3)
    private String job;

    @ExcelProperty( value = "最多可选", index = 4)
    private Long maxSelection;

    @ExcelProperty( value = "A", index = 5)
    private String selectionA;

    @ExcelProperty( value = "B", index = 6)
    private String selectionB;

    @ExcelProperty( value = "C", index = 7)
    private String selectionC;

    @ExcelProperty( value = "D", index = 8)
    private String selectionD;

    @ExcelProperty( value = "E", index = 9)
    private String selectionE;

    @ExcelProperty( value = "F", index = 10)
    private String selectionF;

    @ExcelProperty( value = "G", index = 11)
    private String selectionG;

    @ExcelProperty( value = "H", index = 12)
    private String selectionH;

    @ExcelProperty( value = "I", index = 13)
    private String selectionI;

    @ExcelProperty( value = "错误信息提示",index = 14)
    private String errorMsg;

}
