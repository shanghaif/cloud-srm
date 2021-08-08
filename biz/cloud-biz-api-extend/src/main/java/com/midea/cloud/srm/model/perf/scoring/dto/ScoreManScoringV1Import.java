package com.midea.cloud.srm.model.perf.scoring.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * <pre>
 *  评分人绩效评分 导入DTO
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-02-03 10:00:05
 *  修改内容:
 * </pre>
 */
@Data
@ColumnWidth(40)
@HeadRowHeight(30)
public class ScoreManScoringV1Import implements Serializable {

    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "*项目名称", index = 0 )
    private String projectName;

    @ExcelProperty(value = "*供应商名称" , index = 1)
    private String companyName;

    @ExcelProperty(value = "*指标维度" , index = 2)
    private String indicatorDimensionType;

    @ExcelProperty(value = "*指标名称" , index = 3)
    private String indicatorName;

    @ExcelProperty(value = "模型品类" , index = 4)
    private String templateCategoryNames;

    @ExcelProperty(value = "打分逻辑" , index = 5)
    private String indicatorLogic;

    @ExcelProperty(value = "指标评分值（百分制打分）" , index = 6)
    private String pefScore;

    @ExcelProperty(value = "项目已计算评分" , index = 7)
    private String ifScored;

    @ExcelProperty(value = "绩效开始月份" , index = 8)
    private String perStartMonth;

    @ExcelProperty(value = "绩效结束月份" , index = 9)
    private String perEndMonth;

    @ExcelProperty(value = "采购组织" , index = 10)
    private String organizationName;

    @ExcelProperty(value = "评分人" , index = 11)
    private String scoreNickName;

    @ExcelProperty(value = "打分说明" , index = 12)
    private String comments;

    @ExcelProperty(value = "唯一标识" , index = 13)
    private String scoreManScoringId;

    @ExcelProperty(value = "错误信息提示",index = 14)
    private String errorMsg;

}
