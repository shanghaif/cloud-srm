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
 *  公式要素表 模型
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
@TableName("ceea_bid_essential_factor")
public class EssentialFactor extends BaseEntity {

    private static final long serialVersionUID = -8941744658908340816L;
    /**
     * 要素id,对应公式的参数
     */
    @TableId("ESSENTIAL_FACTOR_ID")
    private Long essentialFactorId;

    /**
     * 要素名称
     */
    @TableField("ESSENTIAL_FACTOR_NAME")
    private String essentialFactorName;

    /**
     * 要素描述
     */
    @TableField("ESSENTIAL_FACTOR_DESC")
    private String essentialFactorDesc;

    /**
     * 来源 1物料主数据，2基价，3报价
     */
    @TableField("ESSENTIAL_FACTOR_FROM")
    private String essentialFactorFrom;

    /**
     * 要素状态
     */
    @TableField("ESSENTIAL_FACTOR_STATUS")
    private String essentialFactorStatus;

    /**
     * 基材id
     */
    @TableField("BASE_MATERIAL_ID")
    private Long baseMaterialId;

    /**
     * 基材编码
     */
    @TableField("BASE_MATERIAL_CODE")
    private String baseMaterialCode;

    /**
     * 物料主属性id
     */
    @TableField("MATERIAL_ATTRIBUTE_ID")
    private Long materialAttributeId;

    /**
     * 物料主属性名
     */
    @TableField("MATERIAL_ATTRIBUTE_NAME")
    private String materialAttributeName;

    /**
     * DAY_PRICE("DAY_PRICE", "当日价格"),
     * WEEK_PRICE("WEEK_PRICE", "周均价"),
     * MONTH_PRICE("MONTH_PRICE", "月均价");
     */
    @TableField("PRICE_TYPE")
    private String priceType;

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
     * 基价名称
     */
    @TableField(value = "BASE_MATERIAL_NAME")
    private String baseMaterialName;
}
