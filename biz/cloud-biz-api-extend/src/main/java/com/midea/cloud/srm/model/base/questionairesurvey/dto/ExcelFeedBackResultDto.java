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
 *  修改日期: 2021/4/23 22:08
 *  修改内容:
 * </pre>
 */
@Data
@ColumnWidth(15)
public class ExcelFeedBackResultDto {
    private static final long serialVersionUID = 703123L;

    @ExcelProperty( value = "序号", index = 0)
    private int num;

    @ExcelProperty( value = "问卷标题", index = 1)
    private String surveyTitle;

    @ExcelProperty( value = "问卷编号", index = 2)
    private String surveyNum;

    @ColumnWidth(25)
    @ExcelProperty( value = "供应商名称", index = 3)
    private String vendorName;

    @ExcelProperty( value = "供应商编码", index = 4)
    private String vendorCode;

    @ExcelProperty( value = "跟踪人员", index = 5)
    private String follower;

    @ExcelProperty( value = "反馈状态", index = 6)
    private String resultFlag;

    @ColumnWidth(20)
    @ExcelProperty( value = "反馈时间", index = 7)
    private String feedbackTime;

    @ExcelProperty( value = "反馈账号", index = 8)
    private String userName;

}
