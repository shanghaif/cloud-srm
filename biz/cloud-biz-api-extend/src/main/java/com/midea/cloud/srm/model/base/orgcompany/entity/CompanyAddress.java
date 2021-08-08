package com.midea.cloud.srm.model.base.orgcompany.entity;

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
 *  组织-公司:地址表 模型
 * </pre>
*
* @author yourname@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-10-14 14:32:08
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_base_org_company_address")
public class CompanyAddress extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 地址ID
     */
    @TableId("COMPANY_ADDRESS_ID")
    private Long companyAddressId;

    /**
     * 组织公司头Id
     */
    @TableField("ORG_COMPANY_HEAD_ID")
    private Long orgCompanyHeadId;

    /**
     * 公司ID
     */
    @TableField("COMPANY_ID")
    private String companyId;

    /**
     * 国家
     */
    @TableField("COUNTRY")
    private String country;

    /**
     * 地区
     */
    @TableField("AREA")
    private String area;

    /**
     * 城市
     */
    @TableField("CITY")
    private String city;

    /**
     * 详细地址
     */
    @TableField("ADDRESS")
    private String address;

    /**
     * 邮政编码
     */
    @TableField("POSTAL_CODE")
    private String postalCode;

    /**
     * 地址备注
     */
    @TableField("REMARK")
    private String remark;

    /**
     * 是否激活(Y-是,N-否)
     */
    @TableField("IS_ACTIVE")
    private String isActive;

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
