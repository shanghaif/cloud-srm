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
 *  修改日期: Apr 16, 2021 6:29:05 PM
 *  修改内容:
 * </pre>
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sup_quest_template_prop_group")
public class QuestTemplatePropGroup extends BaseEntity {
    private static final long serialVersionUID = 512781L;
    /**
     * 主键ID
     */
    @TableId("QUEST_TEMPLATE_PROP_GROUP_ID")
    private Long questTemplatePropGroupId;
    /**
     * 问卷模板定义表主键ID
     */
    @TableField("QUEST_TEMPLATE_ID")
    private Long questTemplateId;
    /**
     * 页签组编码
     */
    @TableField("QUEST_TEMPLATE_PROP_GROUP_CODE")
    private String questTemplatePropGroupCode;
    /**
     * 页签组名称
     */
    @TableField("QUEST_TEMPLATE_PROP_GROUP_NAME")
    private String questTemplatePropGroupName;
    /**
     * 页签组类型: single单表页签 detail明细表页签
     */
    @TableField("QUEST_TEMPLATE_PROP_GROUP_TYPE")
    private String questTemplatePropGroupType;
    /**
     * 是否显示: N不显示  Y显示
     */
    @TableField("SHOW_FLAG")
    private String showFlag;

    /**
     * 是否必填一行: N否  Y是
     */
    @TableField("FILL_ONE_LINE_FLAG")
    private String fillOneLineFlag;
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