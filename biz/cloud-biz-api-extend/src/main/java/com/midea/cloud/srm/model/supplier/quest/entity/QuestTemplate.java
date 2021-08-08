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
 *  修改日期: Apr 16, 2021 5:17:12 PM
 *  修改内容:
 * </pre>
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sup_quest_template")
public class QuestTemplate extends BaseEntity {
    private static final long serialVersionUID = 491971L;
    /**
     * 主键ID
     */
    @TableId("QUEST_TEMPLATE_ID")
    private Long questTemplateId;
    /**
     * 调查模板编码
     */
    @TableField("QUEST_TEMPLATE_CODE")
    private String questTemplateCode;
    /**
     * 调查模板名称
     */
    @TableField("QUEST_TEMPLATE_NAME")
    private String questTemplateName;
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
     * 调查模板描述
     */
    @TableField("QUEST_TEMPLATE_DESC")
    private String questTemplateDesc;
    /**
     * 备注
     */
    @TableField("QUEST_TEMPLATE_REMARK")
    private String questTemplateRemark;
    /**
     * 是否生效  N否  Y是
     */
    @TableField("QUEST_TEMPLATE_STATUS")
    private String questTemplateStatus;
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