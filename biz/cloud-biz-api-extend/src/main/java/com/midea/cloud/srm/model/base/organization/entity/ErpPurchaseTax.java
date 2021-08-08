package com.midea.cloud.srm.model.base.organization.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;
import java.util.Objects;

import com.midea.cloud.srm.model.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <pre>
 *  税率表（隆基税率数据同步） 模型
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-12 18:13:35
 *  修改内容:
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_base_erp_purchase_tax")
public class ErpPurchaseTax extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 税率主键ID
     */
    @TableId("TAX_ID")
    private Long taxId;

    /**
     * 税率ID（隆基erp字段）
     */
    @TableField("TAX_RATE_ID")
    private String taxRateId;

    /**
     * 税种编码（隆基erp字段）
     */
    @TableField("TAX_REGIME_CODE")
    private String taxRegimeCode;

    /**
     * 税码（隆基erp字段）
     */
    @TableField("TAX_RATE_CODE")
    private String taxRateCode;

    /**
     * 税率额（隆基erp字段）
     */
    @TableField("PERCENTAGE_RATE")
    private BigDecimal percentageRate;

    /**
     * 有效日期从（隆基erp字段）
     */
    @TableField("EFFECTIVE_FROM")
    private String effectiveFrom;

    /**
     * 有效日期至（隆基erp字段）
     */
    @TableField("EFFECTIVE_TO")
    private String effectiveTo;

    /**
     * 接口状态（NEW：新增，UPDATE：更新）
     */
    @TableField("ITF_STATUS")
    private String itfStatus;

    /**
     * 来源系统
     */
    @TableField("SOURCE_SYSTEM")
    private String sourceSystem;

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
     * 备用字段6
     */
    @TableField("ATTR6")
    private String attr6;

    /**
     * 备用字段7
     */
    @TableField("ATTR7")
    private String attr7;

    /**
     * 备用字段8
     */
    @TableField("ATTR8")
    private String attr8;

    /**
     * 备用字段9
     */
    @TableField("ATTR9")
    private String attr9;

    /**
     * 备用字段10
     */
    @TableField("ATTR10")
    private String attr10;

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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ErpPurchaseTax erpPurchaseTax = (ErpPurchaseTax) o;
        return taxRateId.equals(erpPurchaseTax.getTaxRateId()) && taxRateCode.equals(erpPurchaseTax.getTaxRateCode());
    }

    @Override
    public int hashCode(){
        return Objects.hash(taxRateId, taxRateCode);
    }

}
