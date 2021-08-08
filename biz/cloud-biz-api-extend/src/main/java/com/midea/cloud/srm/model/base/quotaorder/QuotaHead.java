package com.midea.cloud.srm.model.base.quotaorder;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
*  <pre>
 *  配额比例头表 模型
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-12-07 17:00:12
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_base_quota_head")
public class QuotaHead extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 配额头Id
     */
    @TableId("QUOTA_HEAD_ID")
    private Long quotaHeadId;

    /**
     * 组织ID
     */
    @TableField("ORGANIZATION_ID")
    private Long organizationId;

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
     * 物料ID
     */
    @TableField("MATERIAL_ID")
    private Long materialId;

    /**
     * 物料编码
     */
    @TableField("MATERIAL_CODE")
    private String materialCode;

    /**
     * 物料名称
     */
    @TableField("MATERIAL_NAME")
    private String materialName;

    /**
     * 采购分类ID
     */
    @TableField("CATEGORY_ID")
    private Long categoryId;

    /**
     * 分类编码
     */
    @TableField("CATEGORY_CODE")
    private String categoryCode;

    /**
     * 分类名称
     */
    @TableField("CATEGORY_NAME")
    private String categoryName;

    /**
     * 分类结构
     */
    @TableField("STRUCT")
    private String struct;

    /**
     * 单位
     */
    @TableField("UNIT")
    private String unit;

    /**
     * 单位名称
     */
    @TableField("UNIT_NAME")
    private String unitName;

    /**
     * 有效期起
     */
    @TableField("START_DATE")
    private LocalDate startDate;

    /**
     * 有效期止
     */
    @TableField("END_DATE")
    private LocalDate endDate;

    /**
     * 备注
     */
    @TableField("REMARK")
    private String remark;

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
     * 配额管理类型
     * @enum com.midea.cloud.common.enums.base.QuotaManagementType
     */
    @TableField("QUOTA_MANAGEMENT_TYPE")
    private String quotaManagementType;

    /**
     * 最少拆单量
     */
    @TableField("MINI_SPLIT")
    private BigDecimal miniSplit;

    /**
     * 最大分配量
     */
    @TableField("MAX_ALLOCATION")
    private BigDecimal maxAllocation;

    /**
     * 配额明细行
     */
    @TableField(exist = false)
    private List<QuotaLine> quotaLineList;

    /**
     * 全路径id
     */
    @TableField("FULL_PATH_ID")
    private String fullPathId;
}
