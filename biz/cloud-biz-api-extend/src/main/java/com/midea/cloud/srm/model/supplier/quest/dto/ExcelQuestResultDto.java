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
 *  修改日期: Apr 16, 2021 5:34:12 PM
 *  修改内容:
 * </pre>
 */

@Data
@ColumnWidth(15) //列宽
public class ExcelQuestResultDto {
    private static final long serialVersionUID = 887291L;
    /**
     * 问卷分配给供应商表主键ID
     */
    @ExcelProperty(value = "问卷分配给供应商表主键ID", index = 0)
    private Long questSupId;
    /**
     * 调查模板定义表主键ID
     */
    @ExcelProperty(value = "调查模板定义表主键ID", index = 1)
    private Long questTemplateId;
    /**
     * 问卷模板属性组表主键ID
     */
    @ExcelProperty(value = "问卷模板属性组表主键ID", index = 2)
    private Long questTemplatePropGroupId;
    /**
     * 问卷模板属性表主键ID
     */
    @ExcelProperty(value = "问卷模板属性表主键ID", index = 3)
    private Long questTemplatePropId;
    /**
     * 字段编码
     */
    @ExcelProperty(value = "字段编码", index = 4)
    private String questTemplatePropField;
    /**
     * 字段值
     */
    @ExcelProperty(value = "字段值", index = 5)
    private String questTemplatePropFieldData;
    /**
     * 字段标签
     */
    @ExcelProperty(value = "字段标签", index = 6)
    private String questTemplatePropFieldLable;
    /**
     * 创建人账号
     */
    @ExcelProperty(value = "创建人账号", index = 7)
    private String createdBy;
    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间", index = 8)
    private Date creationDate;
    /**
     * 最后更新人账号
     */
    @ExcelProperty(value = "最后更新人账号", index = 9)
    private String lastUpdatedBy;
    /**
     * 最后更新时间
     */
    @ExcelProperty(value = "最后更新时间", index = 10)
    private Date lastUpdateDate;
    /**
     * 创建人姓名
     */
    @ExcelProperty(value = "创建人姓名", index = 11)
    private String createdFullName;
    /**
     * 最后更新人姓名
     */
    @ExcelProperty(value = "最后更新人姓名", index = 12)
    private String lastUpdatedFullName;
    /**
     * 是否删除 0不删除 1删除
     */
    @ExcelProperty(value = "是否删除 0不删除 1删除", index = 13)
    private Boolean deleteFlag;
    /**
     * 版本号
     */
    @ExcelProperty(value = "版本号", index = 14)
    private Long version;
    /**
     * 错误信息提示
     */
    @ExcelProperty(value = "错误信息提示", index = 15)
    private String errorMsg;

}