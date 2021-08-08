package com.midea.cloud.srm.model.supplier.responsibility.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
*  <pre>
 * 供应商sipplier leader维护
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-19 14:45:21
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_sup_supplier_leader")
public class SupplierLeader extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId("SUPPLIER_LEADER_ID")
    private Long supplierLeaderId;

    /**
     * 供应商Id
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
     * 负责人Id
     */
    @TableField("RESPONSIBILITY_ID")
    private Long responsibilityId;

    /**
     * 负责人编号（对应员工工号）
     */
    @TableField("RESPONSIBILITY_CODE")
    private String responsibilityCode;

    /**
     * 负责人账号
     */
    @TableField("RESPONSIBILITY_USERNAME")
    private String responsibilityUsername;

    /**
     * 负责人姓名
     */
    @TableField("RESPONSIBILITY_NAME")
    private String responsibilityName;

    /**
     * 小类Id
     */
    @TableField("CATEGORY_ID")
    private Long categoryId;

    /**
     * 小类编码
     */
    @TableField("CATEGORY_CODE")
    private String categoryCode;

    /**
     * 小类名称
     */
    @TableField("CATEGORY_NAME")
    private String categoryName;

    /**
     * 小类全路径Id
     */
    @TableField("CATEGORY_FULL_ID")
    private String categoryFullId;

    /**
     * 小类全路径名称
     */
    @TableField("CATEGORY_FULL_NAME")
    private String categoryFullName;

    /**
     * 业务实体Id
     */
    @TableField("ORG_ID")
    private Long orgId;

    /**
     * 业务实体编码
     */
    @TableField("ORG_CODE")
    private String orgCode;

    /**
     * 业务实体名称
     */
    @TableField("ORG_NAME")
    private String orgName;

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
