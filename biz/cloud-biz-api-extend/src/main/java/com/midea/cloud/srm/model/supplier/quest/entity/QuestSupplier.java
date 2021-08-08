package com.midea.cloud.srm.model.supplier.quest.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <pre>
 *  问卷调查 模型
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
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sup_quest_supplier")
public class QuestSupplier extends BaseEntity {
    private static final long serialVersionUID = 510074L;
    /**
     * 主键ID
     */
    @TableId("QUEST_SUP_ID")
    private Long questSupId;
    /**
     * 调查模板定义主键ID
     */
    @TableField("QUEST_TEMPLATE_ID")
    private Long questTemplateId;
    /**
     * 调查表编号
     */
    @TableField("QUEST_NO")
    private String questNo;
    /**
     * 调查表名称
     */
    @TableField("QUEST_NAME")
    private String questName;
    /**
     * 调查模板类型
     */
    @TableField("QUEST_TEMPLATE_TYPE")
    private String questTemplateType;
    /**
     * 调查模板类型名称
     */
    @TableField("QUEST_TEMPLATE_TYPE_NAME")
    private String questTemplateTypeName;
    /**
     * 调查模板所属组织ID
     */
    @TableField("QUEST_TEMPLATE_ORG_ID")
    private String questTemplateOrgId;
    /**
     * 调查模板所属组织编码
     */
    @TableField("QUEST_TEMPLATE_ORG_CODE")
    private String questTemplateOrgCode;
    /**
     * 调查模板所属组织名称
     */
    @TableField("QUEST_TEMPLATE_ORG_NAME")
    private String questTemplateOrgName;
    /**
     * 供应商表主键ID
     */
    @TableField("COMPANY_ID")
    private Long companyId;
    /**
     * 供应商编码
     */
    @TableField("COMPANY_CODE")
    private String companyCode;
    /**
     * 供应商名称
     */
    @TableField("COMPANY_NAME")
    private String companyName;
    /**
     * 社会信用代码
     */
    @TableField("LC_CODE")
    private String lcCode;
    /**
     * 联系人
     */
    @TableField("CONTACT_NAME")
    private String contactName;
    /**
     * 联系方式
     */
    @TableField("CEEA_CONTACT_METHOD")
    private String ceeaContactMethod;
    /**
     * 邮箱
     */
    @TableField("EMAIL")
    private String email;
    /**
     * 审批状态
     */
    @TableField("APPROVAL_STATUS")
    private String approvalStatus;
    /**
     * 问卷反馈备注
     */
    @TableField("QUEST_FEEDBACK")
    private String questFeedback;
    /**
     * 创建人
     */
    @TableField(value = "CREATED_BY", fill = FieldFill.INSERT)
    private String createdBy;
    /**
     * 创建时间
     */
    @TableField(value = "CREATION_DATE", fill = FieldFill.INSERT)
    private Date creationDate;
    /**
     * 最后更新人
     */
    @TableField(value = "LAST_UPDATED_BY", fill = FieldFill.INSERT_UPDATE)
    private String lastUpdatedBy;
    /**
     * 最后更新时间
     */
    @TableField(value = "LAST_UPDATE_DATE", fill = FieldFill.INSERT_UPDATE)
    private Date lastUpdateDate;
    /**
     * 创建人姓名
     */
    @TableField("CREATED_FULL_NAME")
    private String createdFullName;
    /**
     * 最后更新人姓名
     */
    @TableField("LAST_UPDATED_FULL_NAME")
    private String lastUpdatedFullName;
    /**
     * 是否删除 0不删除 1删除
     */
    @TableField("DELETE_FLAG")
    private Boolean deleteFlag;
    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;
}