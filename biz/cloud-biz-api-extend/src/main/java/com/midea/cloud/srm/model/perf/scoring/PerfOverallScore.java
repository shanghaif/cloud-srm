package com.midea.cloud.srm.model.perf.scoring;

import com.baomidou.mybatisplus.annotation.*;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 *  <pre>
 *  综合绩效得分主表 模型
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
@TableName("scc_perf_overall_score")
public class PerfOverallScore extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**综合绩效得分主集合*/
    @TableField(exist = false)
    private List<PerfIndicatorDimScore> perfIndicatorDimScoreList;

    /**
     * 主键ID
     */
    @TableId("OVERALL_SCORE_ID")
    private Long overallScoreId;

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
     * 冗余绩效模型头-模型名称
     */
    @TableField("TEMPLATE_NAME")
    private String templateName;

    /**
     * 评分人绩效评分表-项目名称
     */
    @TableField("PROJECT_NAME")
    private String projectName;

    /**状态(SCORE_DRAFT：拟定(初始化时)，RESULT_PUBLISHED:结果已发布(绩效项目-发布评分))*/
    @TableField("STATUS")
    private String status;

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
     * 评分人绩效评分表-指标类型(PERFORMANCE-绩效,ASSESSMENT-考核)
     */
    @TableField("INDICATOR_TYPE")
    private String indicatorType;

    /**
     * 计算出来的绩效综合得分
     */
    @TableField("SCORE")
    private BigDecimal score;

    /**
     * 绩效综合得分排名
     */
    @TableField("RANK")
    private Long rank;

    /**
     * 绩效排名总数
     */
    @TableField("INDICATOR_COUNT")
    private Integer indicatorCount;

    /**
     * 供应商反馈状态
     */
    @TableField("VENDOR_FEEDBACK_STATUS")
    private String vendorFeedbackStatus;

    /**
     * 附件ID
     */
    @TableField(value = "PERF_OVERALL_SCORE_FILE_ID", updateStrategy = FieldStrategy.IGNORED)
    private Long perfOverallScoreFileId;

    /**
     * 附件名称
     */
    @TableField(value = "PERF_OVERALL_SCORE_FILE_NAME", updateStrategy = FieldStrategy.IGNORED)
    private String perfOverallScoreFileName;

    /**
     * 附件url
     */
    @TableField("PERF_OVERALL_SCORE_FILE_URL")
    private String perfOverallScoreFileUrl;

    /**
     * 供应商反馈说明
     */
    @TableField("VENDOR_FEEDBACK_COMMENTS")
    private String vendorFeedbackComments;

    /**
     * 绩效等级ID
     */
    @TableField("LEVEL_ID")
    private Long levelId;


    /**
     * 绩效等级
     */
    @TableField("LEVEL_NAME")
    private String levelName;

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

    /**
     * 得分扩展字段1(SCORE_ATTRIBUTE1-品质得分,SCORE_ATTRIBUTE2-成本得分,SCORE_ATTRIBUTE3-交付得分,SCORE_ATTRIBUTE4-服务得分,SCORE_ATTRIBUTE5-技术得分)
     */
    @TableField("SCORE_ATTRIBUTE1")
    private BigDecimal scoreAttribute1;

    /**
     * 得分扩展字段2
     */
    @TableField("SCORE_ATTRIBUTE2")
    private BigDecimal scoreAttribute2;

    /**
     * 得分扩展字段3
     */
    @TableField("SCORE_ATTRIBUTE3")
    private BigDecimal scoreAttribute3;

    /**
     * 得分扩展字段4
     */
    @TableField("SCORE_ATTRIBUTE4")
    private BigDecimal scoreAttribute4;

    /**
     * 得分扩展字段5
     */
    @TableField("SCORE_ATTRIBUTE5")
    private BigDecimal scoreAttribute5;

    /**
     * 得分扩展字段6
     */
    @TableField("SCORE_ATTRIBUTE6")
    private BigDecimal scoreAttribute6;

    /**
     * 综合绩效查询 模板品类行
     */
    @TableField(exist = false)
    private String categoryNames;

}
