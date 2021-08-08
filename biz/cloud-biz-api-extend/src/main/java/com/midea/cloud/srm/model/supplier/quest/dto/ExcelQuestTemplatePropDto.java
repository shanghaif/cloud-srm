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
 *  修改日期: Apr 16, 2021 5:33:01 PM
 *  修改内容:
 * </pre>
 */

@Data
@ColumnWidth(15) //列宽
public class ExcelQuestTemplatePropDto {
    private static final long serialVersionUID = 689333L;
    /**
     * 问卷模板定义表主键ID
     */
    @ExcelProperty(value = "问卷模板定义表主键ID", index = 0)
    private Long questTemplateId;
    /**
     * 问卷模板属性组ID
     */
    @ExcelProperty(value = "问卷模板属性组ID", index = 1)
    private Long questTemplatePropGroupId;
    /**
     * 调查模板编码
     */
    @ExcelProperty(value = "调查模板编码", index = 2)
    private String questTemplateCode;
    /**
     * 页签组编码
     */
    @ExcelProperty(value = "页签组编码", index = 3)
    private String questTemplatePropGroupCode;
    /**
     * 排序号
     */
    @ExcelProperty(value = "排序号", index = 4)
    private Boolean questTemplatePropSort;
    /**
     * 字段编码
     */
    @ExcelProperty(value = "字段编码", index = 5)
    private String questTemplatePropField;
    /**
     * 字段描述
     */
    @ExcelProperty(value = "字段描述", index = 6)
    private String questTemplatePropFieldDesc;
    /**
     * 组件类型(1文本、2多行文本、3数值、4日期、5开关、6LOV值集、7下拉、8附件框)
     */
    @ExcelProperty(value = "组件类型(1文本、2多行文本、3数值、4日期、5开关、6LOV值集、7下拉、8附件框)", index = 7)
    private String questTemplatePropType;
    /**
     * 字典
     */
    @ExcelProperty(value = "字典", index = 8)
    private String questTemplatePropDict;
    /**
     * 组件属性
     */
    @ExcelProperty(value = "组件属性", index = 9)
    private String questTemplatePropComponent;
    /**
     * 是否启用(N否 Y是)
     */
    @ExcelProperty(value = "是否启用(N否 Y是)", index = 10)
    private String enabledFlag;
    /**
     * 是否必填(N否 Y是)
     */
    @ExcelProperty(value = "是否必填(N否 Y是)", index = 11)
    private String emptyFlag;
    /**
     * 创建人账号
     */
    @ExcelProperty(value = "创建人账号", index = 12)
    private String createdBy;
    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间", index = 13)
    private Date creationDate;
    /**
     * 最后更新人账号
     */
    @ExcelProperty(value = "最后更新人账号", index = 14)
    private String lastUpdatedBy;
    /**
     * 最后更新时间
     */
    @ExcelProperty(value = "最后更新时间", index = 15)
    private Date lastUpdateDate;
    /**
     * 创建人姓名
     */
    @ExcelProperty(value = "创建人姓名", index = 16)
    private String createdFullName;
    /**
     * 最后更新人姓名
     */
    @ExcelProperty(value = "最后更新人姓名", index = 17)
    private String lastUpdatedFullName;
    /**
     * 是否删除 0不删除 1删除
     */
    @ExcelProperty(value = "是否删除 0不删除 1删除", index = 18)
    private Boolean deleteFlag;
    /**
     * 版本号
     */
    @ExcelProperty(value = "版本号", index = 19)
    private Long version;
    /**
     * 错误信息提示
     */
    @ExcelProperty(value = "错误信息提示", index = 20)
    private String errorMsg;

}