package com.midea.cloud.srm.model.perf.template.entity;

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
 *  <pre>
 *  绩效模型采购分类表 模型
 * </pre>
 *
 * @author wuwl18@meiCloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-28 16:43:12
 *  修改内容:
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_perf_template_category")
public class PerfTemplateCategory extends BaseEntity {

    private static final long serialVersionUID = 1164045731145424387L;

    /**
     * 主键ID
     */
    @TableId("TEMPLATE_CATEGORY_ID")
    private Long TemplateCategoryId;

    /**
     * 关联绩效模型头表行ID
     */
    @TableField("TEMPLATE_HEAD_ID")
    private Long templateHeadId;

    /**
     * 冗余采购分类ID
     */
    @TableField("CATEGORY_ID")
    private Long categoryId;

    /**
     * 冗余采购分类CODE
     */
    @TableField("CATEGORY_CODE")
    private String categoryCode;

    /**
     * 冗余采购分类名称
     */
    @TableField("CATEGORY_NAME")
    private String categoryName;

    /**
     * 冗余采购分类全名
     */
    @TableField("CATEGORY_FULL_NAME")
    private String categoryFullName;

    /**
     * 是否删除(Y-是/N-否)
     */
    @TableField("DELETE_FLAG")
    private String deleteFlag;

    /**
     * 生效开始日期
     */
    @TableField("START_DATE")
    private Date startDate;

    /**
     * 生效日期
     */
    @TableField("END_DATE")
    private Date endDate;

    /**
     * 创建人ID
     */
    @TableField(value = "CREATED_ID", fill = FieldFill.INSERT)
    private Long createdId;

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
     * 创建人IP
     */
    @TableField(value = "CREATED_BY_IP", fill = FieldFill.INSERT)
    private String createdByIp;

    /**
     * 最后更新人ID
     */
    @TableField(value = "LAST_UPDATED_ID", fill = FieldFill.UPDATE)
    private Long lastUpdatedId;

    /**
     * 更新人
     */
    @TableField(value = "LAST_UPDATED_BY", fill = FieldFill.UPDATE)
    private String lastUpdatedBy;

    /**
     * 最后更新时间
     */
    @TableField(value = "LAST_UPDATE_DATE", fill = FieldFill.INSERT_UPDATE)
    private Date lastUpdateDate;

    /**
     * 最后更新人IP
     */
    @TableField(value = "LAST_UPDATED_BY_IP", fill = FieldFill.UPDATE)
    private String lastUpdatedByIp;

    /**
     * 租户ID
     */
    @TableField("TENANT_ID")
    private String tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;

    /**
     * 扩展字段1
     */
    @TableField("ATTRIBUTE1")
    private String attribute1;

    /**
     * 扩展字段2
     */
    @TableField("ATTRIBUTE2")
    private String attribute2;


}
