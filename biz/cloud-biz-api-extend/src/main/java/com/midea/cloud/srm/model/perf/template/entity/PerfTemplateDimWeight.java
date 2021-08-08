package com.midea.cloud.srm.model.perf.template.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 *  <pre>
 *  绩效模型指标维度表 模型
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
@TableName("scc_perf_template_dim_weight")
public class PerfTemplateDimWeight extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**绩效模型绩效指标表集合(勿删：PerfTemplateDimWeightMapper.xml文件里面使用)**/
    @TableField(exist = false)
    private List<PerfTemplateLine> perfTemplateLineList;

    /**
     * 主键ID
     */
    @TableId("DIM_WEIGHT_ID")
    private Long dimWeightId;

    /**
     * 关联绩效模型头表行ID
     */
    @TableField("TEMPLATE_HEAD_ID")
    private Long templateHeadId;

    /**
     * 来源指标库头表-指标维度(QUALITY-品质,SERVICE-服务,DELIVER-交付,COST-成本,TECHNOLOGY-技术)
     */
    @TableField("INDICATOR_DIMENSION_TYPE")
    private String indicatorDimensionType;

    /**
     * 百分比
     */
    @TableField("INDICATOR_DIMENSION_WEIGHT")
    private String indicatorDimensionWeight;

    /**
     * 冗余指标类型(PERFORMANCE-绩效,ASSESSMENT-考核)
     */
    @TableField("INDICATOR_TYPE")
    private String indicatorType;

    /**
     * 是否删除(Y-是/N-否)
     */
    @TableField("DELETE_FLAG")
    private String deleteFlag;

    /**
     * 生效开始日期
     */
    @TableField("START_DATE")
    private Date startDate;

    /**
     * 生效结束日期
     */
    @TableField("END_DATE")
    private Date endDate;

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


}
