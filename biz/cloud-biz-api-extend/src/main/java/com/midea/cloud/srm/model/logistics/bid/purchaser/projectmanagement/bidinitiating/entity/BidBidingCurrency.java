package com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.entity;

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
 * <pre>
 *  投标可用的外币币种 模型
 * </pre>
 *
 * @author tanjl11@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-19 17:16:37
 *  修改内容:
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_lgt_biding_currency")
public class BidBidingCurrency extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 币种id
     */
    @TableId("CURRENCY_ID")
    private Long currencyId;

    /**
     * 币种名
     */
    @TableField("CURRENCY_NAME")
    private String currencyName;
    /**
     * 币种code
     */
    @TableField("CURRENCY_CODE")
    private String currencyCode;

    /**
     * 描述
     */
    @TableField("CURRENCY_DESC")
    private String currencyDesc;

    /**
     * 汇率
     */
    @TableField("PRICE_TAX")
    private BigDecimal priceTax;

    /**
     * 价格精确度
     */
    @TableField("PRICE_PRECISION")
    private Integer pricePrecision;
    /**
     * 招标单id
     */
    @TableField("BIDING_ID")
    private Long bidingId;
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
