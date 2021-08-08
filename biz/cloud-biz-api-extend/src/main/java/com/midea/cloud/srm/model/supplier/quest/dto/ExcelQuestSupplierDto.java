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
 *  修改日期: Apr 16, 2021 5:33:46 PM
 *  修改内容:
 * </pre>
 */

@Data
@ColumnWidth(15) //列宽
public class ExcelQuestSupplierDto {
    private static final long serialVersionUID = 589278L;
    /**
     * 调查模板定义主键ID
     */
    @ExcelProperty(value = "调查模板定义主键ID", index = 0)
    private Long questTemplateId;
    /**
     * 调查表编号
     */
    @ExcelProperty(value = "调查表编号", index = 1)
    private String questNo;
    /**
     * 调查表名称
     */
    @ExcelProperty(value = "调查表名称", index = 2)
    private String questName;
    /**
     * 调查模板类型
     */
    @ExcelProperty(value = "调查模板类型", index = 3)
    private String questTemplateType;
    /**
     * 调查模板类型
     */
    @ExcelProperty(value = "调查模板类型名称", index = 4)
    private String questTemplateTypeName;
    /**
     * 调查模板所属组织编码
     */
    @ExcelProperty(value = "调查模板所属组织Id", index = 5)
    private String questTemplateOrgId;
    /**
     * 调查模板所属组织编码
     */
    @ExcelProperty(value = "调查模板所属组织编码", index = 6)
    private String questTemplateOrgCode;
    /**
     * 调查模板所属组织名称
     */
    @ExcelProperty(value = "调查模板所属组织名称", index = 7)
    private String questTemplateOrgName;
    /**
     * 供应商表主键ID
     */
    @ExcelProperty(value = "供应商表主键ID", index = 8)
    private Long companyId;
    /**
     * 供应商编码
     */
    @ExcelProperty(value = "供应商编码", index = 9)
    private String companyCode;
    /**
     * 审批状态
     */
    @ExcelProperty(value = "审批状态", index = 10)
    private String approvalStatus;
    /**
     * 问卷反馈备注
     */
    @ExcelProperty(value = "问卷反馈备注", index = 11)
    private String questFeedback;
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