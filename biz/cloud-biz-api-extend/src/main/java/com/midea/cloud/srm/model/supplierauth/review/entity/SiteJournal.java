package com.midea.cloud.srm.model.supplierauth.review.entity;

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
 *  地点信息日志表 模型
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-23 16:58:56
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sup_auth_site_journal")
public class SiteJournal extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId("SITE_JOURNAL_ID")
    private Long siteJournalId;

    /**
     * 单据ID
1.FORM_TYPE为REVIEW_FORM则ID为资质审查单ID
2.FORM_TYPE为EFFECT_FORM则ID为供方生效单ID
     */
    @TableField("FORM_ID")
    private Long formId;

    /**
     * 单据类型
1.REVIEW_FORM  资质审查单
2.EFFECT_FORM   供方生效单
     */
    @TableField("FORM_TYPE")
    private String formType;

    /**
     * 供应商ID
     */
    @TableField("VENDOR_ID")
    private Long vendorId;

    /**
     * 地点名称
     */
    @TableField("ADDRESS_NAME")
    private String addressName;

    /**
     * 国家
     */
    @NotEmpty(message = "地点所属国家不能为空")
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
    @NotEmpty(message = "详细地址不能为空")
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
    @NotNull(message = "地点所属的业务实体id不能为空")
    @TableField("ORG_ID")
    private Long orgId;

    /**
     * 业务实体编码（所属业务实体编码）
     */
    @NotEmpty(message = "地点所属的业务实体编码不能为空")
    @TableField("ORG_CODE")
    private String orgCode;

    /**
     * 业务实体名称（所属业务实体名称）
     */
    @NotEmpty(message = "地点所属的业务实体名称不能为空")
    @TableField("ORG_NAME")
    private String orgName;

    /**
     * Erp业务实体Id
     */
//    @NotNull(message = "地点所属的erp业务实体Id不能为空")
    @TableField("ERP_ORG_ID")
    private Long erpOrgId;

    /**
     * Erp业务实体编码
     */
    @TableField("ERP_ORG_CODE")
    private String erpOrgCode;

    /**
     * Erp业务实体名称
     */
    @TableField("ERP_ORG_NAME")
    private String erpOrgName;

    /**
     * 地点名称
     */
    @TableField("VENDOR_SITE_CODE")
    @NotEmpty(message = "地点名称不能为空")
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
     * 生效日期(YYYY-MM-DD)
     */
    @TableField("START_DATE")
    private Date startDate;

    /**
     * 失效日期(YYYY-MM-DD)
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

    /**
     * 是否允许删除
     */
    @TableField("CEEA_ALLOW_DELETE")
    private String ceeaAllowDelete;

    /**
     * 地点信息Id
     */
    @TableField("CEEA_SITE_INFO_ID")
    private Long ceeaSiteInfoId;

}
