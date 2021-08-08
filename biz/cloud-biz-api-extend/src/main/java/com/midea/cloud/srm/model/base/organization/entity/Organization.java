package com.midea.cloud.srm.model.base.organization.entity;
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
import java.util.List;

/**
*  <pre>
 *  组织信息 模型
 * </pre>
*
* @author chensl26@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-02-15 12:42:29
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_base_organization")
public class Organization extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId("ORGANIZATION_ID")
    private Long organizationId;

    /**
     * organizationIdList
     */
    @TableField(exist = false)
    private List<Long> organizationIdList;

    /**
     * 组织全路径虚拟ID
     */
    @TableField(exist = false)
    private String fullPathId;

    /**
     * 组织类型ID
     */
    @TableField("ORGANIZATION_TYPE_ID")
    private Long organizationTypeId;

    /**
     * 组织类型编码
     */
    @TableField("ORGANIZATION_TYPE_CODE")
    private String organizationTypeCode;

    /**
     * 组织类型名称
     */
    @TableField("ORGANIZATION_TYPE_NAME")
    private String organizationTypeName;

    /**
     * 组织名称
     */
    @TableField("ORGANIZATION_NAME")
    private String organizationName;

    /**
     * 组织编码
     */
    @TableField("ORGANIZATION_CODE")
    private String organizationCode;

    /**
     * 父组织ID集
     */
    @TableField("PARENT_ORGANIZATION_IDS")
    private String parentOrganizationIds;

    /**
     * 公司名称
     */
    @TableField("CEEA_COMPANY_NAME")
    private String ceeaCompanyName;

    /**
     * 公司简称
     */
    @TableField("CEEA_COMPANY_SHORT")
    private String ceeaCompanyShort;

    /**
     * 公司代码
     */
    @TableField("CEEA_COMPANY_CODE")
    private String ceeaCompanyCode;

    /**
     * 生效日期
     */
    @TableField("START_DATE")
    private LocalDate startDate;

    /**
     * 失效日期
     */
    @TableField("END_DATE")
    private LocalDate endDate;

    /**
     * 是否有效（）
     */
    @TableField("ENABLED")
    private String enabled;

    /**
     * ERP组织ID
     */
    @TableField("ERP_ORG_ID")
    private String erpOrgId;

    /**
     * 组织地点(收货地点)
     */
    @TableField("ORGANIZATION_SITE")
    private String organizationSite;

    /**
     * 组织地点ID(收货地点ID)
     */
    @TableField("CEEA_ORGANIZATION_SITE_ID")
    private Long ceeaOrganizationSiteId;

    /**
     * 组织详细地点(收货详细地点)
     */
    @TableField("CEEA_ORGANIZATION_SITE_ADDRESS")
    private String ceeaOrganizationSiteAddress;
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
     * 版本号
     */
    @TableField("VERSION")
    private Long version;

    /**
     * 租户ID
     */
    @TableField("TENANT_ID")
    private Long tenantId;

    @TableField("ATTRIBUTE_CATEGORY")
    private String attributeCategory;

//    /**erp库存组织ID**/(弃用)
//    @TableField("CEEA_ERP_ORG_ID")
//    private String ceeaErpOrgId;

    /**erp业务实体ID**/
    @TableField("CEEA_ERP_UNIT_ID")
    private String ceeaErpUnitId;
    /**erp业务实体code**/
    @TableField("CEEA_ERP_UNIT_CODE")
    private String ceeaErpUnitCode;

    /**收单地址**/
    @TableField("CEEA_RECEIVING_LOCATION")
    private String ceeaReceivingLocation;
    /**收单地址ID**/
    @TableField("CEEA_RECEIVING_LOCATION_ID")
    private String ceeaReceivingLocationId;
    /**收单详细地址**/
    @TableField("CEEA_RECEIVING_LOCATION_ADDRES")
    private String ceeaReceivingLocationAddres;
    /**数据来源**/
    @TableField("DATA_RESOURCE")
    private String dataResource;
    /**事业部**/
    @TableField("DIVISION")
    private String division;
    /**事业部ID**/
    @TableField("DIVISION_ID")
    private String divisionId;
    /**公司ID**/
    @TableField("COMPANY")
    private String company;

    /**
     * 父组织名称集(以,为分隔符拼接)
     */
    @TableField(exist = false)
    private String parentOrganizationNames;

    /**
     * ouId列
     */
    @TableField(exist = false)
    private List<Long> ouIds;

    /**
     * 父Id
     */
    @TableField(exist = false)
    private Long fatherId;

    /**币种设置表ID*/
    @TableField("CEEA_CURRENCY_ID")
    private Long ceeaCurrencyId;

    /**币种设置-货币编码*/
    @TableField("CEEA_CURRENCY_CODE")
    private String ceeaCurrencyCode;

    /**币种设置-货币名称*/
    @TableField("CEEA_CURRENCY_NAME")
    private String ceeaCurrencyName;
    /**
     * ou编码(用于验收单推费控)
     */
    @TableField("CEEA_BUSINESS_CODE")
    private String ceeaBusinessCode;

    /**
     * 是否项目公司
     */
    @TableField("IF_PROJECT_COMPANY")
    private String ifProjectCompany;

    
    /**
     * 父组织ID集
     */
    @TableField(exist = false)
    private String parentOrganizationCodes;
}
