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
 *  修改日期: Apr 16, 2021 5:33:01 PM
 *  修改内容:
 * </pre>
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sup_quest_template_prop")
public class QuestTemplateProp extends BaseEntity {
    private static final long serialVersionUID = 397724L;
    /**
     * 主键ID
     */
    @TableId("QUEST_TEMPLATE_PROP_ID")
    private Long questTemplatePropId;
    /**
     * 问卷模板定义表主键ID
     */
    @TableField("QUEST_TEMPLATE_ID")
    private Long questTemplateId;
    /**
     * 问卷模板属性组ID
     */
    @TableField("QUEST_TEMPLATE_PROP_GROUP_ID")
    private Long questTemplatePropGroupId;
    /**
     * 调查模板编码
     */
    @TableField("QUEST_TEMPLATE_CODE")
    private String questTemplateCode;
    /**
     * 页签组编码
     */
    @TableField("QUEST_TEMPLATE_PROP_GROUP_CODE")
    private String questTemplatePropGroupCode;
    /**
     * 排序号
     */
    @TableField("QUEST_TEMPLATE_PROP_SORT")
    private Integer questTemplatePropSort;
    /**
     * 字段编码
     */
    @TableField("QUEST_TEMPLATE_PROP_FIELD")
    private String questTemplatePropField;
    /**
     * 字段描述
     */
    @TableField("QUEST_TEMPLATE_PROP_FIELD_DESC")
    private String questTemplatePropFieldDesc;
    /**
     * 组件类型(1文本、2多行文本、3数值、4日期、5开关、6LOV值集、7下拉、8附件框)
     */
    @TableField("QUEST_TEMPLATE_PROP_TYPE")
    private String questTemplatePropType;
    /**
     * 字典
     */
    @TableField("QUEST_TEMPLATE_PROP_DICT")
    private String questTemplatePropDict;
    /**
     * 组件属性
     */
    @TableField("QUEST_TEMPLATE_PROP_COMPONENT")
    private String questTemplatePropComponent;
    /**
     * 是否启用(N否 Y是)
     */
    @TableField("ENABLED_FLAG")
    private String enabledFlag;
    /**
     * 是否必填(N否 Y是)
     */
    @TableField("EMPTY_FLAG")
    private String emptyFlag;
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