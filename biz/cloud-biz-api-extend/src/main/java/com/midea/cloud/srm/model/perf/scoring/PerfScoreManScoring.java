package com.midea.cloud.srm.model.perf.scoring;

import com.baomidou.mybatisplus.annotation.*;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.midea.cloud.srm.model.perf.template.entity.PerfTemplateIndsLine;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 *  <pre>
 *  评分人绩效评分表 模型
 * </pre>
 *
 * @author wuwl18@meiCloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-06-11 14:29:16
 *  修改内容:
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_perf_score_man_scoring")
public class PerfScoreManScoring extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**绩效模型指标行List(quoteMode值为按文本折算时才有值)*/
    @TableField(exist = false)
    private List<PerfTemplateIndsLine> templateIndsLineList;

    /**
     * 主键ID
     */
    @TableId("SCORE_MAN_SCORING_ID")
    private Long scoreManScoringId;

    /**
     * 状态(项目状态(SCORE_DRAFT:拟定,SCORE_CALCULATED:已计算评分))
     */
    @TableId("STATUS")
    private String status;

    /**
     * 绩效评分项目表ID
     */
    @TableField("SCORE_ITEMS_ID")
    private Long scoreItemsId;

    /**
     * 绩效模型头表ID
     */
    @TableField("TEMPLATE_HEAD_ID")
    private Long templateHeadId;

    /**
     * 冗余绩效模型头-模型名称
     */
    @TableField("TEMPLATE_NAME")
    private String templateName;

    /**
     * 绩效评分项目表-项目名称
     */
    @TableField("PROJECT_NAME")
    private String projectName;

    /**
     * 绩效评分项目表-评价期间(MONTHLY-月度,QUARTER季度,HALF_YEAR-半年度,YEAR-年度;评价区间是从绩效模型那边带过来，可修改)
     */
    @TableField("EVALUATION_PERIOD")
    private String evaluationPeriod;

    /**
     * 绩效评分项目表-绩效开始月份(2020-01)
     */
    @TableField("PER_START_MONTH")
    private LocalDate perStartMonth;

    /**
     * 绩效评分项目表-绩效结束月份(2020-02)
     */
    @TableField("PER_END_MONTH")
    private LocalDate perEndMonth;

    /**
     * 绩效评分项目表-采购组织ID
     */
    @TableField("ORGANIZATION_ID")
    private Long organizationId;

    /**
     * 组织全路径虚拟ID
     */
    @TableField("FULL_PATH_ID")
    private String fullPathId;

    /**
     * 绩效评分项目表-采购组织名称
     */
    @TableField("ORGANIZATION_NAME")
    private String organizationName;

    /**
     * 绩效模型采购分类表-采购分类ID
     */
    @TableField("CATEGORY_ID")
    private Long categoryId;

    /**
     * 绩效模型采购分类表-采购分类CODE
     */
    @TableField("CATEGORY_CODE")
    private String categoryCode;

    /**
     * 绩效模型采购分类表-采购分类名称
     */
    @TableField("CATEGORY_NAME")
    private String categoryName;

    /**
     * 绩效模型采购分类表-采购分类全名
     */
    @TableField("CATEGORY_FULL_NAME")
    private String categoryFullName;

    /**
     * 绩效评分项目供应商表-供应商ID
     */
    @TableField("COMPANY_ID")
    private Long companyId;

    /**
     * 绩效评分项目供应商表-供应商编码
     */
    @TableField("COMPANY_CODE")
    private String companyCode;

    /**
     * 绩效评分项目供应商表-供应商名称
     */
    @TableField("COMPANY_NAME")
    private String companyName;

    /**
     * 绩效评分项目供应商表-供应商名称(英文)
     */
    @TableField("COMPANY_EN_NAME")
    private String companyEnName;

    /**
     * 绩效模型指标维度表-ID
     */
    @TableField("DIM_WEIGHT_ID")
    private Long dimWeightId;


    /**
     * 绩效模型指标维度表-指标类型(PERFORMANCE-绩效,ASSESSMENT-考核)
     */
    @TableField("INDICATOR_TYPE")
    private String indicatorType;
    /**
     * 绩效模型指标维度表-指标维度(QUALITY-品质,SERVICE-服务,DELIVER-交付,COST-成本,TECHNOLOGY-技术)
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
     * 绩效模型指标表-指标行类型(TEXT-文本，NUMBER-数子，PERCENTAGE-百分比)
     */
    @TableField("INDICATOR_LINE_TYPE")
    private String indicatorLineType;

    /**
     * 绩效模型指标表-折算方式(DIRECT_QUOTE-直接取值，TEXT_CONVERSION-按文本折算，INTERVAL_CONVERSION-按区间折算)
     */
    @TableField("QUOTE_MODE")
    private String quoteMode;

    /**
     * 绩效模型指标表-维度权重(百分比)
     */
    @TableField("DIMENSION_WEIGHT")
    private BigDecimal dimensionWeight;

    /**
     * 绩效模型指标行表-ID
     */
    @TableField("TEMPLATE_INDS_LINE_ID")
    private Long templateIndsLineId;

    /**
     * 绩效模型指标行表-指标行说明(保存操作取值方式值‘按文本折算’时必填)
     */
    @TableField("INDICATOR_LINE_DES")
    private String indicatorLineDes;

    /**
     * 绩效模型指标行表-取值方式值(保存操作‘直接取值’和‘按区间折算’时必填)
     */
    @TableField("PEF_SCORE")
    private BigDecimal pefScore;

    /**
     * 根据选择的绩效模型指标行表-计算出来的绩效得分(保存操作时必填)
     */
    @TableField("SCORE")
    private BigDecimal score;

    /**
     * 评分人是否评分（默认N）
     */
    @TableField("IF_SCORED")
    private String ifScored;

    /**
     * 绩效评分项目表-评分人账号
     */
    @TableField("SCORE_USER_NAME")
    private String scoreUserName;

    /**
     * 绩效评分项目表-评分人名称
     */
    @TableField("SCORE_NICK_NAME")
    private String scoreNickName;

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

    /**
     * 附件ID
     */
    @TableField(value = "SCORE_MAN_SCORING_FILE_ID", updateStrategy = FieldStrategy.IGNORED)
    private Long scoreManScoringFileId;

    /**
     * 附件名称
     */
    @TableField(value = "SCORE_MAN_SCORING_FILE_NAME", updateStrategy = FieldStrategy.IGNORED)
    private String scoreManScoringFileName;

    /**
     * 附件url
     */
    @TableField("SCORE_MAN_SCORING_FILE_URL")
    private String scoreManScoringFileUrl;


}
