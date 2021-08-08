package com.midea.cloud.srm.model.base.formula.entity;

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
 * <pre>
 *  基本材料表 模型
 * </pre>
 *
 * @author tanjl11@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-26 16:27:13
 *  修改内容:
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ceea_bid_base_material")
public class BaseMaterial extends BaseEntity {

    private static final long serialVersionUID = -6439962707275174186L;
    /**
     * 基材ID
     */
    @TableId("BASE_MATERIAL_ID")
    private Long baseMaterialId;

    /**
     * 基材编码
     */
    @TableField("BASE_MATERIAL_CODE")
    private String baseMaterialCode;

    /**
     * 基材名称
     */
    @TableField("BASE_MATERIAL_NAME")
    private String baseMaterialName;

    /**
     * 基材类型
     */
    @TableField("BASE_MATERIAL_TYPE")
    private String baseMaterialType;

    /**
     * 基材状态
     */
    @TableField("BASE_MATERIAL_STATUS")
    private String baseMaterialStatus;

    /**
     * 计算方式
     */
    @TableField("BASE_MATERIAL_CALCULATE_TYPE")
    private String baseMaterialCalculateType;

    /**
     * 基材单位
     */
    @TableField("BASE_MATERIAL_UNIT")
    private String baseMaterialUnit;
    /**
     * 是否海鲜价
     */
    @TableField("IS_SEA_FOOD_PRICE")
    private String seaFoodPrice;

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
