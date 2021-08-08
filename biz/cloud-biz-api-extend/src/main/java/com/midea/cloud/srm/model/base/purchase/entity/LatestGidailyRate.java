package com.midea.cloud.srm.model.base.purchase.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
*  <pre>
 *  汇率表（最新的汇率，持续更新） 模型
 * </pre>
*
* @author xiexh12@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-27 16:29:44
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_base_latest_gidaily_rate")
public class LatestGidailyRate extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键,汇率主键ID
     */
    @TableId("ID")
    private Long id;

    /**
     * 源币种编码
     */
    @TableField("FROM_CURRENCY_CODE")
    private String fromCurrencyCode;

    /**
     * 源币种
     */
    @TableField("FROM_CURRENCY")
    private String fromCurrency;

    /**
     * 目标币种编码
     */
    @TableField("TO_CURRENCY_CODE")
    private String toCurrencyCode;

    /**
     * 目标币种
     */
    @TableField("TO_CURRENCY")
    private String toCurrency;

    /**
     * 汇率类型
     */
    @TableField("CONVERSION_TYPE")
    private String conversionType;

    /**
     * 汇率
     */
    @TableField("CONVERSION_RATE")
    private BigDecimal conversionRate;

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
     *最小浮动汇率（自定义的规则）
     */
    @TableField(exist = false)
    private BigDecimal minConversionRate;
    /**
     * 最大浮动汇率（自定义规则）
     */
    @TableField(exist = false)
    private BigDecimal maxConversionRate;
}
