package com.midea.cloud.srm.model.perf.inditors.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.midea.cloud.srm.model.common.BaseDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
*  <pre>
 *  指标库头表DTO
 * </pre>
*
* @author wuwl18@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-26
 *  修改内容:
 * </pre>
*/
@Data
public class IndicatorsHeaderDTO extends BaseDTO {

    private static final long serialVersionUID = -7633928559780974067L;

    /**指标库头行表信息*/
    private List<IndicatorsLineDTO> indicatorsLineList;

    /**
     * 表ID，主键，供其他表做外键
     */
    private Long indicatorHeadId;
    /**
     * 指标名称
     */
    private String indicatorName;

    /**
     * 指标维度(QUALITY-品质,SERVICE-服务,DELIVER-交付,COST-成本,TECHNOLOGY-技术)
     *
     */
    private String indicatorDimension;

    /**
     * 指标类型(INDICATOR-绩效,ASSESSMENT-考核)
     */
    private String indicatorType;

    /**
     * 数据状态(Y-启用/N-禁用)
     */
    private String enableFlag;

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

    /**取值方式(DIRECT_QUOTE-直接取值，TEXT_CONVERSION-按文本折算，INTERVAL_CONVERSION-按区间折算)*/
    private String quoteMode;
    
    private String indicatorTypeDimension;
    
    private String indicatorTypeName;
    
    private String indicatorDimensionName;

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

    /**
     * 扩展字段1
     */
    private String attribute1;

    /**
     * 扩展字段2
     */
    private String attribute2;

    /**
     * 扩展字段3
     */
    private String attribute3;


}
