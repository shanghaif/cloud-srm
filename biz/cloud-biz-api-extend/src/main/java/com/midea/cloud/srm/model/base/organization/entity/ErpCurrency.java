package com.midea.cloud.srm.model.base.organization.entity;

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
 *  币种表（隆基币种同步） 模型
 * </pre>
*
* @author xiexh12@midea.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-25 14:16:59
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_base_currency")
public class ErpCurrency extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键,币种主键ID
     */
    @TableId("ID")
    private Long id;

    /**
     * 币种代码
     */
    @TableField("CURRENCY_CODE")
    private String currencyCode;

    /**
     * 币种名称中文
     */
    @TableField("CURRENCY_NAME_ZHS")
    private String currencyNameZhs;

    /**
     * 币种名称英文
     */
    @TableField("CURRENCY_NAME_US")
    private String currencyNameUs;

    /**
     * 中文说明
     */
    @TableField("DESCRIPTION_ZHS")
    private String descriptionZhs;

    /**
     * 英文说明
     */
    @TableField("DESCRIPTION_US")
    private String descriptionUs;

    /**
     * 启用标记
     */
    @TableField("ENABLED_FLAG")
    private String enabledFlag;

    /**
     * 发行地区代码
     */
    @TableField("ISSUING_TERRITORY_CODE")
    private String issuingTerritoryCode;

    /**
     * 符号
     */
    @TableField("SYMBOL")
    private String symbol;

    /**
     * 精确度
     */
    @TableField("GI_PRECISION")
    private BigDecimal giPrecision;

    /**
     * 扩展精确度
     */
    @TableField("EXTENDED_PRECISION")
    private BigDecimal extendedPrecision;

    /**
     * 最小可记账单位
     */
    @TableField("MINIMUM_ACCOUNTABLE_UNIT")
    private BigDecimal minimumAccountableUnit;

    /**
     * 起始日期
     */
    @TableField("START_DATE")
    private String startDate;

    /**
     * 终止日期
     */
    @TableField("END_DATE")
    private String endDate;

    /**
     * 接口状态(NEW-数据在接口表里,没同步到业务表,UPDATE-数据更新到接口表里,SUCCESS-同步到业务表,ERROR-数据同步到业务表出错)
     */
    @TableField("ITF_STATUS")
    private String itfStatus;

    /**
     * 备用字段1
     */
    @TableField("ATTR1")
    private String attr1;

    /**
     * 备用字段2
     */
    @TableField("ATTR2")
    private String attr2;

    /**
     * 备用字段3
     */
    @TableField("ATTR3")
    private String attr3;

    /**
     * 备用字段4
     */
    @TableField("ATTR4")
    private String attr4;

    /**
     * 备用字段5
     */
    @TableField("ATTR5")
    private String attr5;

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


}
