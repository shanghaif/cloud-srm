package com.midea.cloud.srm.model.perf.template.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.util.List;

import com.midea.cloud.srm.model.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 *  <pre>
 *  绩效模型指标信息表 模型
 * </pre>
 *
 * @author wuwl18@meiCloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-30 10:17:50
 *  修改内容:
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_perf_template_line")
public class PerfTemplateLine extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**绩效模型指标行表集合(勿删：PerfTemplateLineMapper.xml文件里面使用)*/
    @TableField(exist = false)
    private List<PerfTemplateIndsLine> perfTemplateIndsLineList;

    /**
     * 主键ID
     */
    @TableId("TEMPLATE_LINE_ID")
    private Long templateLineId;

    /**
     * 绩效模型指标维度表ID
     */
    @TableField("TEMPLATE_DIM_WEIGHT_ID")
    private Long templateDimWeightId;

    /**
     * 指标名称
     */
    @TableField("INDICATOR_NAME")
    private String indicatorName;

    /**
     * 指标维度(QUALITY-品质,SERVICE-服务,DELIVER-交付,COST-成本,TECHNOLOGY-技术)
     */
    @TableField("INDICATOR_DIMENSION")
    private String indicatorDimension;

    /**
     * 维度权重(百分比)
     */
    @TableField("DIMENSION_WEIGHT")
    private String dimensionWeight;

    /**
     * 指标类型(INDICATOR-绩效,ASSESSMENT-考核)
     */
    @TableField("INDICATOR_TYPE")
    private String indicatorType;

    /**
     * 是否删除(Y-是/N-否)
     */
    @TableField("DELETE_FLAG")
    private String deleteFlag;

    /**
     * 指标逻辑
     */
    @TableField("INDICATOR_LOGIC")
    private String indicatorLogic;

    /**
     * 评价方式(SCORING_SYSTEM_VALUE:评分-系统取值,DEDUCTION_SYSTEM_VALUE:扣分-系统取值,SCORING_MANUAL:评分-手工,DEDUCTION_MANUAL:扣分-手工)
     */
    @TableField("EVALUATION")
    private String evaluation;

    /**
     * 扣分上限
     */
    @TableField("MARK_LIMIT")
    private BigDecimal markLimit;

    /**
     * 指标行类型(TEXT-文本，NUMBER-数子，PERCENTAGE-百分比)
     */
    @TableField("INDICATOR_LINE_TYPE")
    private String indicatorLineType;

    /**
     * 折算方式(DIRECT_QUOTE-直接取值，TEXT_CONVERSION-按文本折算，INTERVAL_CONVERSION-按区间折算)
     */
    @TableField("QUOTE_MODE")
    private String quoteMode;

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


}
