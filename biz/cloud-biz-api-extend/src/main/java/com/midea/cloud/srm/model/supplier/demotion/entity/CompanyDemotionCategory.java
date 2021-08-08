package com.midea.cloud.srm.model.supplier.demotion.entity;

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
 *  供应商升降级品类行表 模型
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-01-05 17:50:08
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_sup_company_demotion_category")
public class CompanyDemotionCategory extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 升降级业务实体行主键ID
     */
    @TableId("COMPANY_DEMOTION_CATEGORY_ID")
    private Long companyDemotionCategoryId;

    /**
     * 升降级头表ID
     */
    @TableField("COMPANY_DEMOTION_ID")
    private Long companyDemotionId;

    /**
     * 品类ID
     */
    @TableField("CATEGORY_ID")
    private Long categoryId;

    /**
     * 品类编码
     */
    @TableField("CATEGORY_CODE")
    private String categoryCode;

    /**
     * 品类名称
     */
    @TableField("CATEGORY_NAME")
    private String categoryName;

    /**
     * 品类全路径ID
     */
    @TableField("CATEGORY_FULL_ID")
    private String categoryFullId;

    /**
     * 品类全路径名称
     */
    @TableField("CATEGORY_FULL_NAME")
    private String categoryFullName;

    /**
     * 关联绩效单id
     */
    @TableField("PERFORMANCE_ID")
    private Long performanceId;

    /**
     * 关联绩效单号
     */
    @TableField("PERFORMANCE_NUMBER")
    private String performanceNumber;

    /**
     * 备用字段1
     */
    @TableField("ATTR1")
    private String attr1;

    /**
     * 备用字段2
     */
    @TableField("ATTR2")
    private String attr2;

    /**
     * 备用字段3
     */
    @TableField("ATTR3")
    private String attr3;

    /**
     * 备用字段4
     */
    @TableField("ATTR4")
    private String attr4;

    /**
     * 备用字段5
     */
    @TableField("ATTR5")
    private String attr5;

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
     * 更新人
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
    private String tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;


}
