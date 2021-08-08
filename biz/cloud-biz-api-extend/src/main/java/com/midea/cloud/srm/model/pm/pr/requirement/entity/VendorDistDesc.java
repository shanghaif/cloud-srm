package com.midea.cloud.srm.model.pm.pr.requirement.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
*  <pre>
 *  供应商分配明细表 (PM模块)
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-12-07 17:01:39
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_base_vendor_dist_desc")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VendorDistDesc extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 供应商分配明细Id
     */
    @TableId("VENDOR_DIST_DESC_ID")
    private Long vendorDistDescId;

    /**
     * 采购需求头ID
     */
    @TableField("REQUIREMENT_HEAD_ID")
    private Long requirementHeadId;

    /**
     * 采购需求行idID
     */
    @TableId("REQUIREMENT_LINE_ID")
    private Long requirementLineId;

    /**
     * 供应商ID
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
     * 配额比(%)
     */
    @TableField("QUOTA")
    private BigDecimal quota;

    /**
     * 计划分配数量
     */
    @TableField("PLAN_AMOUNT")
    private BigDecimal planAmount;

    /**
     * 实际分配数量
     */
    @TableField("ACTUAL_AMOUNT")
    private BigDecimal actualAmount;

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

    /**
     * 配额行Id
     */
    @TableId("QUOTA_LINE_ID")
    private Long quotaLineId;

    /**
     * 配额头Id
     */
    @TableField("QUOTA_HEAD_ID")
    private Long quotaHeadId;
}
