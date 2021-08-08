package com.midea.cloud.srm.model.perf.scoreproject.dto;

import com.midea.cloud.srm.model.common.BaseDTO;
import com.midea.cloud.srm.model.perf.scoreproject.entity.PerfScoreItemsMan;
import com.midea.cloud.srm.model.perf.scoreproject.entity.PerfScoreItemsSup;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 *  <pre>
 *  绩效评分项目主信息表DTO
 * </pre>
 *
 * @author wuwl18@meiCloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-06-08
 *  修改内容:
 * </pre>
 */
@Data
public class PerfScoreItemsDTO extends BaseDTO {

    /**绩效评分项目供应商集合*/
    private List<PerfScoreItemsSup> perfScoreItemsSupList;

    /**绩效评分项目评分人集合*/
    private List<PerfScoreItemsMan> perfScoreItemsManList;

    /**
     * 主键ID
     */
    private Long scoreItemsId;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 绩效模型头ID
     */
    private Long templateHeadId;

    /**
     * 冗余绩效模型头-模型名称
     */
    private String templateName;

    /**
     * 采购组织ID
     */
    private Long organizationId;

    /**
     * 组织全路径虚拟ID
     */
    private String fullPathId;

    /**
     * 采购组织名称
     */
    private String organizationName;

    /**
     * 评价期间(MONTHLY-月度,QUARTER季度,HALF_YEAR-半年度,YEAR-年度;评价区间是从绩效模型那边带过来，可修改)
     */
    private String evaluationPeriod;

    /**
     * 状态(Y-启动/N-禁用,默认Y)
     */
    private String status;

    /**
     * 绩效开始月份(2020-01)
     */
    private LocalDate perStartMonth;

    /**
     * 绩效结束月份(2020-02)
     */
    private LocalDate perEndMonth;

    /**
     * 评分开始时间
     */
    private LocalDate scoreStartTime;

    /**
     * 评分结束时间
     */
    private LocalDate scoreEndTime;

    /**
     * 项目状态(SCORE_DRAFT:拟定,SCORE_NOTIFIED:已通知评分,SCORE_CALCULATED:已计算评分,RESULT_NO_PUBLISHED:结果未发布,RESULT_PUBLISHED:结果已发布,OBSOLETE:已废弃)
     */
    private String projectStatus;

    /**
     * 审批状态(DRAFT:拟定，SUBMITTED:已提交,REJECTED:已驳回,APPROVED:已批准)
     */
    private String approveStatus;

    /**
     * 已评分人数(默认0)
     */
    private Long scorePeople;

    /**
     * 评分总人数
     */
    private Long scorePeopleCount;

    /**
     * 创建人ID
     */
    private Long createdId;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private Date creationDate;

    /**
     * 创建人IP
     */
    private String createdByIp;

    /**
     * 创建人姓名
     */
    private String createdFullName;

    /**
     * 最后更新人ID
     */
    private Long lastUpdatedId;

    /**
     * 更新人
     */
    private String lastUpdatedBy;

    /**
     * 最后更新时间
     */
    private Date lastUpdateDate;

    /**
     * 最后更新人IP
     */
    private String lastUpdatedByIp;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 版本号
     */
    private Long version;

    private String cbpmInstanceId;

}
