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

import javax.xml.bind.annotation.XmlElement;

/**
*  <pre>
 *  汇率表（隆基汇率同步） 模型
 * </pre>
*
* @author xiexh12@midea.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-25 11:00:26
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_base_gidaily_rate")
public class GidailyRate extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键,汇率主键ID
     */
    @TableId("ID")
    private Long id;

    /**
     * 源币种
     */
    @TableField("FROM_CURRENCY")
    private String fromCurrency;

    /**
     * 目的币种
     */
    @TableField("TO_CURRENCY")
    private String toCurrency;

    /**
     * 日期
     */
    @TableField("CONVERSION_DATE")
    private String conversionDate;

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
     * 创建时间（ERP）
     */
    @TableField("ERP_CREATION_DATE")
    private String erpCreationDate;

    /**
     * 创建人（ERP）
     */
    @TableField("ERP_CREATED_BY")
    private BigDecimal erpCreatedBy;

    /**
     * 最后更新时间（ERP）
     */
    @TableField("ERP_LAST_UPDATE_DATE")
    private String erpLastUpdateDate;

    /**
     * 最后更新人（ERP）
     */
    @TableField("ERP_LAST_UPDATED_BY")
    private BigDecimal erpLastUpdatedBy;

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
