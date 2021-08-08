package com.midea.cloud.srm.model.base.formula.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
*  <pre>
 *  基本材料价格表 模型
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
@TableName("ceea_bid_base_material_price")
public class BaseMaterialPrice extends BaseEntity {

    private static final long serialVersionUID = 4729532977736106063L;
    /**
     * 基本材料价格ID
     */
    @TableId("BASE_MATERIAL_PRICE_ID")
    private Long baseMaterialPriceId;

    /**
     *DRAFT新建,INVAILD失效，ACTIVE生效
     */
    @TableField("BASE_MATERIAL_PRICE_STATUS")
    private String baseMaterialPriceStatus;


    /**
     * 基价类型
     '基价类型 DAY_PRICE当日价格 WEEK_PRICE周均价 MONTH_PRICE月均价',
     */
    @TableField("BASE_MATERIAL_PRICE_TYPE")
    private String baseMaterialPriceType;

    /**
     * 有效期起始日期
     */
    @TableField("ACTIVE_DATE_FROM")
    private Date activeDateFrom;

    /**
     * 有效期结束日期
     */
    @TableField("ACTIVE_DATE_TO")
    private Date activeDateTo;

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
     * 基材ID
     */
    @TableField("BASE_MATERIAL_ID")
    private Long baseMaterialId;

    /**
     * 单位
     */
    @TableField("BASE_MATERIAL_UNIT")
    private String baseMaterialUnit;

    /**
     * 币种
     */
    @TableField("CURRENCY_TYPE")
    private String currencyType;

    /**
     * 价格
     */
    @TableField("BASE_MATERIAL_PRICE")
    private BigDecimal baseMaterialPrice;

    /**
     * 数据来源
     */
    @TableField("PRICE_FROM")
    private String priceFrom;

    /**
     * 数据采集开始时间
     */
    @TableField("COLLECT_START_DATE")
    private Date collectStartDate;

    /**
     * 数据采集结束时间
     */
    @TableField("COLLECT_END_DATE")
    private Date collectEndDate;

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
