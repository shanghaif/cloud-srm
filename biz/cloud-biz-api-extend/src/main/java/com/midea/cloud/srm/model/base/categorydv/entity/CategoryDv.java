package com.midea.cloud.srm.model.base.categorydv.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.Date;

/**
*  <pre>
 *  品类分工 模型
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-06 10:04:24
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_base_category_dv")
public class CategoryDv extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 品类分工ID
     */
    @TableId("CATEGORY_DV_ID")
    private Long categoryDvId;

    /**
     * 租户
     */
    @TableField("TENANT_ID")
    private Long tenantId;



    /**
     * 组织ID
     */
    @TableField("ORG_ID")
    private Long orgId;

    /**
     * 组织全路径虚拟ID
     */
    @TableField("FULL_PATH_ID")
    private String fullPathId;

    /**
     * 组织名称
     */
    @TableField("ORG_NAME")
    private String orgName;

    /**
     * 品类ID
     */
    @TableField("CATEGORY_ID")
    private Long categoryId;

    /**
     * 品类名称
     */
    @TableField("CATEGORY_NAME")
    private String categoryName;

    /**
     * 采购分类全名
     */
    @TableField("CATEGORY_FULL_NAME")
    private String categoryFullName;

    /**
     * 用户ID
     */
    @TableField("USER_ID")
    private Long userId;

    /**
     * 用户账号
     */
    @TableField("USER_NAME")
    private String userName;

    /**
     * 用户姓名
     */
    @TableField("FULL_NAME")
    private String fullName;

    /**
     * 生效时间
     */
    @TableField("START_DATE")
    private LocalDate startDate;

    /**
     * 失效时间
     */
    @TableField("END_DATE")
    private LocalDate endDate;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;

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


}
