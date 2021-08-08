package com.midea.cloud.srm.model.supplier.change.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.util.Map;

import com.midea.cloud.srm.model.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
*  <pre>
 *  地点信息变更 模型
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-25 10:44:41
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sup_site_info_change")
public class SiteInfoChange extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 地点变更ID
     */
    @TableId("SITE_CHANGE_ID")
    private Long siteChangeId;

    /**
     * 变更ID
     */
    @TableField("CHANGE_ID")
    private Long changeId;

    /**
     * ID
     */
    @TableField("SITE_INFO_ID")
    private Long siteInfoId;

    /**
     * 供应商ID
     */
    @TableField("COMPANY_ID")
    private Long companyId;

    /**
     * ERP供应商Id（隆基新增）
     */
    @TableField("ERP_VENDOR_ID")
    private Long erpVendorId;

    /**
     * ERP供应商code(暂时冗余)
     */
    @TableField("ERP_VENDOR_CODE")
    private String erpVendorCode;

    /**
     * 地址名称
     */
    @TableField("ADDRESS_NAME")
    private String addressName;

    /**
     * 国家
     */
    @TableField("COUNTRY")
    private String country;

    /**
     * 省份
     */
    @TableField("PROVINCE")
    private String province;

    /**
     * 城市
     */
    @TableField("CITY")
    private String city;

    /**
     * 县
     */
    @TableField("COUNTY")
    private String county;

    /**
     * 详细地址
     */
    @TableField("ADDRESS_DETAIL")
    private String addressDetail;

    /**
     * 可采购
     */
    @TableField("PURCHASE_FLAG")
    private String purchaseFlag;

    /**
     * 可付款
     */
    @TableField("PAYMENT_FLAG")
    private String paymentFlag;

    /**
     * 仅限于询价
     */
    @TableField("RFQ_ONLY_FLAG")
    private String rfqOnlyFlag;

    /**
     * 启用状态
     */
    @TableField("ENABLED_FLAG")
    private String enabledFlag;

    /**
     * 业务实体Id（所属业务实体Id）
     */
    @TableField("ORG_ID")
    private Long orgId;

    /**
     * 业务实体编码（所属业务实体编码）
     */
    @TableField("ORG_CODE")
    private String orgCode;

    /**
     * 业务实体名称（所属业务实体名称）
     */
    @TableField("ORG_NAME")
    private String orgName;

    /**
     * 地点名称（ASSET：固定资产，MATERIAL：材料，EXPENSE：费用，CONSIGNMENT：寄售）
     */
    @TableField("VENDOR_SITE_CODE")
    private String vendorSiteCode;

    /**
     * 失效日期
     */
    @TableField("DISABLE_DATE")
    private String disableDate;

    /**
     * 邮政编码
     */
    @TableField("POST_CODE")
    private String postCode;

    /**
     * 地址备注
     */
    @TableField("SITE_COMMENT")
    private String siteComment;

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
    private Long tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;

    /**
     * 供应商地点ID(ERP)
     */
    @TableField("VENDOR_SITE_ID")
    private String vendorSiteId;

    /**
     * 企业名称(暂时冗余)
     */
    @TableField("COMPANY_NAME")
    private String companyName;

    /**
     * ERP业务实体ID(暂时冗余)
     */
    @TableField("BELONG_OPR_ID")
    private String belongOprId;

    /**
     * 操作类型
     */
    @TableField("OP_TYPE")
    private String opType;

    @TableField(exist = false)
    Map<String, Object> dimFieldContexts;

}
