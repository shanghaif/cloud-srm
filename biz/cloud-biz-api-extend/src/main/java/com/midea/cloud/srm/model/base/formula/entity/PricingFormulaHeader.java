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
*  <pre>
 *  计价公式头表 模型
 * </pre>
*
* @author tanjl11@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-26 16:27:13
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ceea_bid_pricing_formula_header")
public class PricingFormulaHeader extends BaseEntity {

    private static final long serialVersionUID = -7825715212509058039L;
    /**
     * 公式头表ID
     */
    @TableId("PRICING_FORMULA_HEADER_ID")
    private Long pricingFormulaHeaderId;

    /**
     * 公式名称
     */
    @TableField("PRICING_FORMULA_NAME")
    private String pricingFormulaName;

    /**
     * 公式描述
     */
    @TableField("PRICING_FORMULA_DESC")
    private String pricingFormulaDesc;

    /**
     * 公式值
     */
    @TableField("PRICING_FORMULA_VALUE")
    private String pricingFormulaValue;

    /**
     * INVAILD("INVAILD", "失效"),
     *     DRAFT("DRAFT", "新建"),
     *     ACTIVE("ACTIVE", "生效");
     */
    @TableField("PRICING_FORMULA_STATUS")
    private String pricingFormulaStatus;



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
     * 是否海鲜价公式
     */
    @TableField(value = "IS_SEA_FOOD_FORMULA")
    private String isSeaFoodFormula;

}
