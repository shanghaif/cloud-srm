package com.midea.cloud.srm.model.perf.inditors.entity;

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
 *  指标库行表 模型
 * </pre>
*
* @author wuwl18@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-26 11:35:37
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_perf_indicators_line")
public class IndicatorsLine extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 表ID，主键，供其他表做外键
     */
    @TableId("INDICATOR_LINE_ID")
    private Long indicatorLineId;

    /**
     * 头表ID
     */
    @TableField("INDICATOR_HEAD_ID")
    private Long indicatorHeadId;

    /**
     * 指标行行说明
     */
    @TableField("INDICATOR_LINE_DES")
    private String indicatorLineDes;

    /**
     * 绩效评分
     */
    @TableField("PEF_SCORE")
    private BigDecimal pefScore;

    /**
     * 考核罚款
     */
    @TableField("ASSESSMENT_PENALTY")
    private BigDecimal assessmentPenalty;

    /**
     * 评分值开始
     */
    @TableField("SCORE_START")
    private BigDecimal scoreStart;

    /**
     * 评分值结束
     */
    @TableField("SCORE_END")
    private BigDecimal scoreEnd;

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
     * 扩展字段1
     */
    @TableField("ATTRIBUTE1")
    private String attribute1;

    /**
     * 扩展字段2
     */
    @TableField("ATTRIBUTE2")
    private String attribute2;

    /**
     * 扩展字段3
     */
    @TableField("ATTRIBUTE3")
    private String attribute3;

    /**
     * 扩展字段4
     */
    @TableField("ATTRIBUTE4")
    private String attribute4;


}
