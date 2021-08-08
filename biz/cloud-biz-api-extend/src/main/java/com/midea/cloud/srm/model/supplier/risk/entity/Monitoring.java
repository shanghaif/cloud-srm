package com.midea.cloud.srm.model.supplier.risk.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

/**
*  <pre>
 *  供应商风险监控 模型
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-01 13:46:12
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sup_risk_monitoring")
public class Monitoring extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 风险监控Id
     */
    @TableId("RISK_MONITORING_ID")
    private Long riskMonitoringId;

    /**
     * 风险编号
     */
    @TableField("RISK_CODE")
    private String riskCode;

    /**
     * 状态(字典:RISK_MONITORING_STATUS)
     */
    @TableField("STATUS")
    private String status;

    /**
     * 创建人名字
     */
    @TableField("CREATED_NAME")
    private String createdName;

    /**
     * 部门
     */
    @TableField("DEPARTMENT")
    private String department;

    /**
     * 采购分类ID
     */
    @TableField("CATEGORY_ID")
    private Long categoryId;

    /**
     * 采购分类编码
     */
    @TableField("CATEGORY_CODE")
    private String categoryCode;

    /**
     * 采购分类名称
     */
    @TableField("CATEGORY_NAME")
    private String categoryName;

    /**
     * 供应商ID
     */
    @TableField("VENDOR_ID")
    private Long vendorId;

    /**
     * 供应商编码
     */
    @TableField("VENDOR_CODE")
    private String vendorCode;

    /**
     * 供应商名称
     */
    @TableField("VENDOR_NAME")
    private String vendorName;

    /**
     * 风险描述
     */
    @TableField("RISK_DESCRIPTION")
    private String riskDescription;

    /**
     * 风险影响类型(字典: RISK_TYPE )
     */
    @TableField("RISK_TYPE")
    private String riskType;

    /**
     * 风险发生可能性(字典:RISK_POSSIBILITY)
     */
    @TableField("RISK_POSSIBILITY")
    private String riskPossibility;

    /**
     * 风险影响程度评分
     */
    @TableField("RISK_SCORE")
    private String riskScore;

    /**
     * 风险系数
     */
    @TableField("RISK_COEFFICIENT")
    private BigDecimal riskCoefficient;

    /**
     * 风险等级(字典: RISK_LEVEL)
     */
    @TableField("RISK_LEVEL")
    private String riskLevel;

    /**
     * 风险发生后影响描述
     */
    @TableField("RISK_INFLUENCES_DESCRIPTION")
    private String riskInfluencesDescription;

    /**
     * 风险关闭时间
     */
    @TableField("CLOSE_DATE")
    private LocalDate closeDate;

    /**
     * 风险应对施实施情况
     */
    @TableField("RISK_IMPLEMENT_DESC")
    private String riskImplementDesc;

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

    /**
     * 系统业务流程控制
     */
    @TableField(exist = false)
    private List<ProcessControl> processControls;

    /**
     * 风险应对措施
     */
    @TableField(exist = false)
    private List<Responses> responses;

    /**
     * 开始时间
     */
    @TableField(exist = false)
    private Date startDate;

    /**
     * 结束时间
     */
    @TableField(exist = false)
    private Date endDate;
}
