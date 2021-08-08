package com.midea.cloud.srm.model.supplier.info.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
*  <pre>
 *  地点信息（供应商） 模型
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-20 11:19:01
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sup_site_info")
public class SiteInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID（供应商地点信息主键ID）
     */
    @TableId("SITE_INFO_ID")
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
     * 地点名称
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
     * ERP业务实体ID
     */
    @TableField("BELONG_OPR_ID")
    private String belongOprId;

    /**
     * 企业名称(暂时冗余)
     */
    @TableField("COMPANY_NAME")
    private String companyName;

    /**
     * ERP供应商Code（隆基新增，NSrm推送Erp成功后，Erp回传的erpVendorCode）(暂时冗余)
     */
    @TableField("ERP_VENDOR_CODE")
    private String erpVendorCode;

    /**
     * 是否推送erp
     */
    @TableField("IF_PUSH_ERP")
    private String ifPushErp;

    /**
     * erp地点id是否接收成功(erp地点回写srm标识, Y:回写成功, N:回写失败, 默认N)
     */
    @TableField("IF_RECEIVE_FROM_ERP")
    private String ifReceiveFromErp;

}
