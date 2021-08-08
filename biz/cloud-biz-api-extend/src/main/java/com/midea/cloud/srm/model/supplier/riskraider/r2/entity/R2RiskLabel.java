package com.midea.cloud.srm.model.supplier.riskraider.r2.entity;

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
 *  风险标签数据表 模型
 * </pre>
*
* @author chenwt24@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-01-18 10:19:50
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sup_r2_risk_label")
public class R2RiskLabel extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 风险标签ID
     */
    @TableId("RISK_LABEL_ID")
    private Long riskLabelId;

    /**
     * 风险信息ID
     */
    @TableField("RISK_INFO_ID")
    private Long riskInfoId;

    /**
     * 工商基本信息风险
     */
    @TableField("BUSINESS_RISK_ANALYSIS")
    private String businessRiskAnalysis;

    /**
     * 司法信息风险标签
     */
    @TableField("JUDICIAL_RISK_ANALYSIS")
    private String judicialRiskAnalysis;

    /**
     * 经营分析风险标签
     */
    @TableField("FINANCE_RISK_ANALYSIS")
    private String financeRiskAnalysis;

    /**
     * 舆情分析风险标签
     */
    @TableField("NEWS_RISK_ANALYSIS")
    private String newsRiskAnalysis;

    /**
     * 失信分析风险标签
     */
    @TableField("BLACKLIST_RISK_ANALYSIS")
    private String blacklistRiskAnalysis;

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
