package com.midea.cloud.srm.model.base.formula.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author tanjl11@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/2 9:39
 *  修改内容:
 * </pre>
 */
@Data
@TableName("ceea_material_formula_relate")
public class MaterialFormulaRelate extends BaseEntity {
    private static final long serialVersionUID = -7825715212509021311L;

    /**
     * 关联id
     */
    @TableId(value = "RELATE_ID")
    private Long relateId;

    /**
     * 公式值
     */
    @TableField("FORMULA_VALUE")
    private String pricingFormulaValue;
    /**
     * 公式id
     */
    @TableField("FORMULA_ID")
    private Long pricingFormulaHeaderId;
    /**
     * 公式名
     */
    @TableField("FORMULA_NAME")
    private String pricingFormulaName;
    /**
     * 公式描述
     */
    @TableField("FORMULA_DESC")
    private String pricingFormulaDesc;
    /**
     * 公式状态
     */
    @TableField("FORMULA_STATUS")
    private String pricingFormulaStatus;
    /**
     * 物料id
     */
    @TableField("MATERIAL_ID")
    private Long materialId;
    /**
     * 物料code
     */
    @TableField("MATERIAL_CODE")
    private String materialCode;
    /**
     * 物料名称
     */
    @TableField("MATERIAL_NAME")
    private String materialName;
    /**
     * 规格型号
     */
    @TableField("SPECIFICATION")
    private String specification;
    /**
     * 品类id
     */
    @TableField("CATEGORY_ID")
    private Long categoryId;
    /**
     * 品类名
     */
    @TableField("CATEGORY_NAME")
    private String categoryName;
    /**
     * 单位
     */
    @TableField("UNIT")
    private String unit;
    /**
     * 单位名
     */
    @TableField("UNIT_NAME")
    private String unitName;
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
