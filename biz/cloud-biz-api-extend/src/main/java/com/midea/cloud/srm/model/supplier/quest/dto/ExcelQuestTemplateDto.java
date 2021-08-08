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
 *  修改日期: Apr 16, 2021 5:17:12 PM
 *  修改内容:
 * </pre>
 */

@Data
@ColumnWidth(15) //列宽
public class ExcelQuestTemplateDto {
    private static final long serialVersionUID = 879380L;
    /**
     * 调查模板编码
     */
    @ExcelProperty(value = "调查模板编码", index = 0)
    private String questTemplateCode;
    /**
     * 调查模板名称
     */
    @ExcelProperty(value = "调查模板名称", index = 1)
    private String questTemplateName;
    /**
     * 调查模板类型
     */
    @ExcelProperty(value = "调查模板类型", index = 2)
    private String questTemplateType;
    /**
     * 调查模板类型
     */
    @ExcelProperty(value = "调查模板类型名称", index = 3)
    private String questTemplateTypeName;
    /**
     * 调查模板描述
     */
    @ExcelProperty(value = "调查模板描述", index = 4)
    private String questTemplateDesc;
    /**
     * 备注
     */
    @ExcelProperty(value = "备注", index = 5)
    private String questTemplateRemark;
    /**
     * 是否生效  N否  Y是
     */
    @ExcelProperty(value = "是否生效  N否  Y是", index = 6)
    private String questTemplateStatus;
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