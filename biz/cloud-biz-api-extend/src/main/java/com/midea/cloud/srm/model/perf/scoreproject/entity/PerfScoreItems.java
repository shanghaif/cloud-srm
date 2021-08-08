package com.midea.cloud.srm.model.perf.scoreproject.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.Date;

/**
 *  <pre>
 *  绩效评分项目主信息表 模型
 * </pre>
 *
 * @author wuwl18@meiCloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-06-05 17:33:40
 *  修改内容:
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_perf_score_items")
public class PerfScoreItems extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId("SCORE_ITEMS_ID")
    private Long scoreItemsId;

    /**
     * 项目名称
     */
    @TableField("PROJECT_NAME")
    private String projectName;

    /**
     * 绩效模型头ID
     */
    @TableField("TEMPLATE_HEAD_ID")
    private Long templateHeadId;

    /**
     * 冗余绩效模型头-模型名称
     */
    @TableField("TEMPLATE_NAME")
    private String templateName;

    /**
     * 采购组织ID
     */
    @TableField("ORGANIZATION_ID")
    private Long organizationId;

    /**
     * 组织全路径虚拟ID
     */
    @TableField("FULL_PATH_ID")
    private String fullPathId;

    /**
     * 采购组织名称
     */
    @TableField("ORGANIZATION_NAME")
    private String organizationName;

    /**
     * 评价期间(MONTHLY-月度,QUARTER季度,HALF_YEAR-半年度,YEAR-年度;评价区间是从绩效模型那边带过来，可修改)
     */
    @TableField("EVALUATION_PERIOD")
    private String evaluationPeriod;

    /**
     * 状态(Y-启动/N-禁用,默认Y)
     */
    @TableField("STATUS")
    private String status;

    /**
     * 绩效开始月份(2020-01)
     */
    @TableField(value = "PER_START_MONTH", updateStrategy = FieldStrategy.IGNORED)
    private LocalDate perStartMonth;

    /**
     * 绩效结束月份(2020-02)
     */
    @TableField(value = "PER_END_MONTH", updateStrategy = FieldStrategy.IGNORED)
    private LocalDate perEndMonth;

    /**
     * 评分开始时间
     */
    @TableField(value = "SCORE_START_TIME", updateStrategy = FieldStrategy.IGNORED)
    private LocalDate scoreStartTime;

    /**
     * 评分结束时间
     */
    @TableField(value = "SCORE_END_TIME", updateStrategy = FieldStrategy.IGNORED)
    private LocalDate scoreEndTime;

    /**
     * 项目状态(SCORE_DRAFT:拟定,SCORE_NOTIFIED:已通知评分,SCORE_CALCULATED:已计算评分,RESULT_NO_PUBLISHED:结果未发布,RESULT_PUBLISHED:结果已发布,OBSOLETE:已废弃)
     */
    @TableField("PROJECT_STATUS")
    private String projectStatus;

    /**
     * 审批状态(DRAFT:拟定，SUBMITTED:已提交,REJECTED:已驳回,APPROVED:已批准)
     */
    @TableField("APPROVE_STATUS")
    private String approveStatus;

    /**
     * 已评分人数(默认0)
     */
    @TableField("SCORE_PEOPLE")
    private Long scorePeople;

    /**
     * 评分总人数
     */
    @TableField("SCORE_PEOPLE_COUNT")
    private Long scorePeopleCount;

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

    @TableField("CBPM_INSTANCE_ID")
    private String cbpmInstaceId;

    @TableField(exist=false)
    private Long menuId;

}
