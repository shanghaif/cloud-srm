package com.midea.cloud.srm.model.perf.scoring;

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
import java.util.Date;

/**
 *  <pre>
 *  指标维度绩效得分明细表 模型
 * </pre>
 *
 * @author wuwl18@meiCloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-06-16 11:50:39
 *  修改内容:
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_perf_ind_dim_score_detail")
public class PerfIndDimScoreDetail extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**根据指标指标维度分数排名*/
    @TableField(exist = false)
    private Long rank;

    /**
     * 主键ID
     */
    @TableId("IND_DIM_SCORE_DETAIL_ID")
    private Long indDimScoreDetailId;

    /**
     * 评分人绩效评分表-ID
     */
    @TableField("SCORE_MAN_SCORING_ID")
    private Long scoreManScoringId;

    /**
     * 评分人绩效评分表-绩效评分项目表ID
     */
    @TableField("SCORE_ITEMS_ID")
    private Long scoreItemsId;

    /**
     * 评分人绩效评分表-绩效模型头表ID
     */
    @TableField("TEMPLATE_HEAD_ID")
    private Long templateHeadId;

    /**
     * 指标维度绩效得分表ID INDICATOR_DIM_SCORE_ID
     */
    @TableField("INDICATOR_DIM_SCORE_ID")
    private Long indicatorDimScoreId;

    /**
     * 评分人绩效评分表-项目名称
     */
    @TableField("PROJECT_NAME")
    private String projectName;

    /**
     * 评分人绩效评分表-评价期间(MONTHLY-月度,QUARTER季度,HALF_YEAR-半年度,YEAR-年度;评价区间是从绩效模型那边带过来，可修改)
     */
    @TableField("EVALUATION_PERIOD")
    private String evaluationPeriod;

    /**
     * 评分人绩效评分表-绩效开始月份(2020-01)
     */
    @TableField("PER_START_MONTH")
    private LocalDate perStartMonth;

    /**
     * 评分人绩效评分表-绩效结束月份(2020-02)
     */
    @TableField("PER_END_MONTH")
    private LocalDate perEndMonth;

    /**
     * 评分人绩效评分表-采购组织ID
     */
    @TableField("ORGANIZATION_ID")
    private Long organizationId;

    /**
     * 组织全路径虚拟ID
     */
    @TableField("FULL_PATH_ID")
    private String fullPathId;

    /**
     * 评分人绩效评分表-采购组织名称
     */
    @TableField("ORGANIZATION_NAME")
    private String organizationName;

    /**
     * 评分人绩效评分表-采购分类ID
     */
    @TableField("CATEGORY_ID")
    private Long categoryId;

    /**
     * 评分人绩效评分表-采购分类CODE
     */
    @TableField("CATEGORY_CODE")
    private String categoryCode;

    /**
     * 评分人绩效评分表-采购分类名称
     */
    @TableField("CATEGORY_NAME")
    private String categoryName;

    /**
     * 评分人绩效评分表-采购分类全名
     */
    @TableField("CATEGORY_FULL_NAME")
    private String categoryFullName;

    /**
     * 评分人绩效评分表-供应商ID
     */
    @TableField("COMPANY_ID")
    private Long companyId;

    /**
     * 评分人绩效评分表-供应商编码
     */
    @TableField("COMPANY_CODE")
    private String companyCode;

    /**
     * 评分人绩效评分表-供应商名称
     */
    @TableField("COMPANY_NAME")
    private String companyName;

    /**
     * 评分人绩效评分表-供应商名称(英文)
     */
    @TableField("COMPANY_EN_NAME")
    private String companyEnName;

    /**
     * 评分人绩效评分表-指标维度ID
     */
    @TableField("DIM_WEIGHT_ID")
    private Long dimWeightId;

    /**
     * 评分人绩效评分表-指标类型(PERFORMANCE-绩效,ASSESSMENT-考核)
     */
    @TableField("INDICATOR_TYPE")
    private String indicatorType;

    /**
     * 评分人绩效评分表-指标维度(QUALITY-品质,SERVICE-服务,DELIVER-交付,COST-成本,TECHNOLOGY-技术))
     */
    @TableField("INDICATOR_DIMENSION_TYPE")
    private String indicatorDimensionType;

    /**
     * 绩效模型指标维度表-维度权重(百分比)
     */
    @TableField("INDICATOR_DIMENSION_WEIGHT")
    private String indicatorDimensionWeight;

    /**
     * 绩效模型指标表-ID
     */
    @TableField("TEMPLATE_LINE_ID")
    private Long templateLineId;

    /**
     * 绩效模型指标表-指标名称
     */
    @TableField("INDICATOR_NAME")
    private String indicatorName;

    /**
     * 绩效模型指标表-维度权重(百分比)
     */
    @TableField("DIMENSION_WEIGHT")
    private BigDecimal dimensionWeight;

    /**
     * 计算出来的绩效得分
     */
    @TableField("SCORE")
    private BigDecimal score;

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
     * 创建人姓名
     */
    @TableField(value = "CREATED_FULL_NAME", fill = FieldFill.INSERT)
    private String createdFullName;

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
