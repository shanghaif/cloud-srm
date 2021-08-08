package com.midea.cloud.srm.model.base.organization.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.util.Objects;

import com.midea.cloud.srm.model.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
*  <pre>
 *  类别表（隆基类别同步） 模型
 * </pre>
*
* @author xiexh12@midea.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-21 19:28:00
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_base_category")
public class Category extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableField("ID")
    private Long id;

    /**
     * 物料ID
     */
    @TableField("ITEM_ID")
    private Long itemId;

    /**
     * 类别集代码
     */
    @TableField("CATEGORY_SET_CODE")
    private String categorySetCode;

    /**
     * 类别集名称（ZHS）
     */
    @TableField("CATEGORY_SET_NAME_ZHS")
    private String categorySetNameZhs;

    /**
     * 类别集名称（US）
     */
    @TableField("CATEGORY_SET_NAME_US")
    private String categorySetNameUs;

    /**
     * 类别值
     */
    @TableField("SET_VALUE")
    private String setValue;

    /**
     * 类别说明（ZHS）
     */
    @TableField("SET_VALUE_DESC_ZHS")
    private String setValueDescZhs;

    /**
     * 类别说明（US）
     */
    @TableField("SET_VALUE_DESC_US")
    private String setValueDescUs;

    /**
     * 接口状态(NEW-新增，UPDATE-更新)
     */
    @TableField("ITF_STATUS")
    private String itfStatus;

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
     * 创建日期
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
    @TableField(value = "LAST_UPDATED_ID", fill = FieldFill.INSERT_UPDATE)
    private Long lastUpdatedId;

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
     * 最后更新人IP
     */
    @TableField(value = "LAST_UPDATED_BY_IP", fill = FieldFill.INSERT_UPDATE)
    private String lastUpdatedByIp;

    /**
     * 租户ID
     */
    @TableField("TENANT_ID")
    private Long tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Category category = (Category) o;
        return Objects.equals(itemId, category.getItemId()) && Objects.equals(categorySetCode, category.getCategorySetCode());
    }

}
