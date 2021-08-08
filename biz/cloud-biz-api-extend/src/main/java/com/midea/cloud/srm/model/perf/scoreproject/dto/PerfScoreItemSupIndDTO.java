package com.midea.cloud.srm.model.perf.scoreproject.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseDTO;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 *  <pre>
 *  绩效评分项目供应商-绩效表DTO
 * </pre>
 *
 * @author wuwl18@meiCloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-06-06
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class PerfScoreItemSupIndDTO extends BaseDTO {

    private static final long serialVersionUID = -1518317099801769151L;

    /**
     * 绩效评分项目供应商-绩效表ID
     */
    private Long scoreItemManSupIndId;

    /**
     * 绩效评分项目评分人ID
     */
    private Long scoreItemsManId;

    /**
     * 关联绩效评分项目主表ID
     */
    private Long scoreItemsId;

    /**
     * 关联绩效评分项目供应商表ID
     */
    private Long scoreItemsSupId;

    /**
     * 供应商ID
     */
    private Long companyId;

    /**
     * 供应商编码
     */
    private String companyCode;

    /**
     * 供应商名称
     */
    private String companyName;

    /**
     * 供应商名称(英文)
     */
    private String companyEnName;


    /**
     * 绩效指标行ID
     */
    private Long templateLineId;

    /**
     * 绩效模型指标维度表ID
     */
    private Long templateDimWeightId;

    /**
     * 指标名称
     */
    private String indicatorName;

    /**
     * 指标维度(QUALITY-品质,SERVICE-服务,DELIVER-交付,COST-成本,TECHNOLOGY-技术)
     */
    private String indicatorDimension;

    /**
     * 维度权重(百分比)
     */
    private String dimensionWeight;

    /**
     * 指标类型(INDICATOR-绩效,ASSESSMENT-考核)
     */
    private String indicatorType;

    /**
     * 是否删除(Y-是/N-否)
     */
    private String deleteFlag;

    /**
     * 指标逻辑
     */
    private String indicatorLogic;

    /**
     * 评价方式(SCORING_SYSTEM_VALUE:评分-系统取值,DEDUCTION_SYSTEM_VALUE:扣分-系统取值,SCORING_MANUAL:评分-手工,DEDUCTION_MANUAL:扣分-手工)
     */
    private String evaluation;

    /**
     * 扣分上限
     */
    private BigDecimal markLimit;

    /**
     * 指标行类型(TEXT-文本，NUMBER-数子，PERCENTAGE-百分比)
     */
    private String indicatorLineType;

    /**
     * 折算方式(DIRECT_QUOTE-直接取值，TEXT_CONVERSION-按文本折算，INTERVAL_CONVERSION-按区间折算)
     */
    private String quoteMode;

    /**
     * 允许评分(Y-允许评分/N禁止评分)
     */
    private String enableFlag;

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


}
