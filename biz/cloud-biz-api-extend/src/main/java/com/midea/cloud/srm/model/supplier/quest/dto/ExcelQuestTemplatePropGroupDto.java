package com.midea.cloud.srm.model.supplier.quest.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.util.Date;

/**
 * <pre>
 *  问卷调查 excel导出模型
 * </pre>
 *
 * @author bing5.wang@midea.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Apr 16, 2021 6:29:05 PM
 *  修改内容:
 * </pre>
 */

@Data
@ColumnWidth(15) //列宽
public class ExcelQuestTemplatePropGroupDto {
    private static final long serialVersionUID = 355018L;
    /**
     * 问卷模板定义表主键ID
     */
    @ExcelProperty(value = "问卷模板定义表主键ID", index = 0)
    private Long questTemplateId;
    /**
     * 页签组编码
     */
    @ExcelProperty(value = "页签组编码", index = 1)
    private String questTemplatePropGroupCode;
    /**
     * 页签组名称
     */
    @ExcelProperty(value = "页签组名称", index = 2)
    private String questTemplatePropGroupName;
    /**
     * 页签组类型: single单表页签 detail明细表页签
     */
    @ExcelProperty(value = "页签组类型: single单表页签 detail明细表页签", index = 3)
    private String questTemplatePropGroupType;
    /**
     * 是否显示: N不显示  Y显示
     */
    @ExcelProperty(value = "是否显示: N不显示  Y显示", index = 4)
    private String showFlag;

    /**
     * 是否显示: N不显示  Y显示
     */
    @ExcelProperty(value = "是否必填一行: N否  Y是", index = 5)
    private String fillOneLineFlag;

    /**
     * 创建人账号
     */
    @ExcelProperty(value = "创建人账号", index = 6)
    private String createdBy;
    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间", index = 7)
    private Date creationDate;
    /**
     * 最后更新人账号
     */
    @ExcelProperty(value = "最后更新人账号", index = 8)
    private String lastUpdatedBy;
    /**
     * 最后更新时间
     */
    @ExcelProperty(value = "最后更新时间", index = 9)
    private Date lastUpdateDate;
    /**
     * 创建人姓名
     */
    @ExcelProperty(value = "创建人姓名", index = 10)
    private String createdFullName;
    /**
     * 最后更新人姓名
     */
    @ExcelProperty(value = "最后更新人姓名", index = 11)
    private String lastUpdatedFullName;
    /**
     * 是否删除 0不删除 1删除
     */
    @ExcelProperty(value = "是否删除 0不删除 1删除", index = 12)
    private Boolean deleteFlag;
    /**
     * 版本号
     */
    @ExcelProperty(value = "版本号", index = 13)
    private Long version;
    /**
     * 错误信息提示
     */
    @ExcelProperty(value = "错误信息提示", index = 14)
    private String errorMsg;

}