package com.midea.cloud.srm.model.base.businesstype.entity;

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
 *  业务类型配置表 模型
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-10-21 14:57:34
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_base_bussiness_type")
public class BussinessType extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 业务类型配置ID
     */
    @TableId("BUSSINESS_TYPE_ID")
    private Long bussinessTypeId;

    /**
     * 地点名称
     */
    @TableField("VENDOR_SITE_CODE")
    private String vendorSiteCode;

    /**
     * 事业部名称
     */
    @TableField("DIVISION")
    private String division;

    /**
     * 事业部ID
     */
    @TableField("DIVISION_ID")
    private String divisionId;

    /**
     * 业务类型编码
     */
    @TableField("BUSINESS_TYPE")
    private String businessType;

    /**
     * 业务类型名称
     */
    @TableField("BUSINESS_TYPE_NAME")
    private String businessTypeName;

    /**
     * 结算单据类型 字典码:PAYMENT_DOCUMENT_TYPE
     */
    @TableField("PAYMENT_DOCUMENT_TYPE")
    private String paymentDocumentType;

    /**
     * 生效日期(YYYY-MM-DD)
     */
    @TableField("START_DATE")
    private LocalDate startDate;

    /**
     * 失效日期(YYYY-MM-DD)
     */
    @TableField("END_DATE")
    private LocalDate endDate;

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
     * 是否代付
     */
    @TableField("IF_PAY_AGENT")
    private String ifPayAgent;

    /**
     * 备注
     */
    @TableField("REMARKS")
    private String remarks;

    /**
     * 是否启用
     */
    @TableField("ENABLED")
    private String enabled;

}
