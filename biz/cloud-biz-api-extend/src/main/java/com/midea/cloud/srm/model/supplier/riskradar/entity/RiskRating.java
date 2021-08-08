package com.midea.cloud.srm.model.supplier.riskradar.entity;

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
 *  风险评级表 模型
 * </pre>
*
* @author chensl26@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-05 14:14:17
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sup_risk_rating")
public class RiskRating extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 风险评级ID
     */
    @TableId("RISK_RATING_ID")
    private Long riskRatingId;

    /**
     * 供应商ID
     */
    @TableField("VENDOR_ID")
    private Long vendorId;

    /**
     * 供应编码
     */
    @TableField("VENDOR_CODE")
    private String vendorCode;

    /**
     * 供应商名称
     */
    @TableField("VENDOR_NAME")
    private String vendorName;

    /**
     * 风险标签
     */
    @TableField("LABEL")
    private String label;

    /**
     * 风险状况
     */
    @TableField("SITUATION")
    private String situation;

    /**
     * 工商
     */
    @TableField("BUSINESS")
    private String business;

    /**
     * 涉诉
     */
    @TableField("INVOLVED")
    private String involved;

    /**
     * 舆情
     */
    @TableField("OPINION")
    private String opinion;

    /**
     * 经营
     */
    @TableField("OPERATING")
    private String operating;

    /**
     * 税务
     */
    @TableField("TAXATION")
    private String taxation;

    /**
     * 违规
     */
    @TableField("VIOLATION")
    private String violation;

    /**
     * 投融资
     */
    @TableField("FINANCING")
    private String financing;

    /**
     * 失信系统记录
     */
    @TableField("DISH_SYS_LOG")
    private String dishSysLog;

    /**
     * 自定义失信记录
     */
    @TableField("cus_DISH_LOG")
    private String cusDishLog;

    /**
     * 风险认定及原因
     */
    @TableField("RISK_REC_REASON")
    private String riskRecReason;

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
    @TableField(value = "LAST_UPDATED_ID", fill = FieldFill.UPDATE)
    private Long lastUpdatedId;

    /**
     * 更新人
     */
    @TableField(value = "LAST_UPDATED_BY", fill = FieldFill.UPDATE)
    private String lastUpdatedBy;

    /**
     * 最后更新时间
     */
    @TableField(value = "LAST_UPDATE_DATE", fill = FieldFill.INSERT_UPDATE)
    private Date lastUpdateDate;

    /**
     * 最后更新人IP
     */
    @TableField(value = "LAST_UPDATED_BY_IP", fill = FieldFill.UPDATE)
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
