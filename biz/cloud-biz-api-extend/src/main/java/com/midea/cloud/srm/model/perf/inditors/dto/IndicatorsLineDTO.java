package com.midea.cloud.srm.model.perf.inditors.dto;

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
 *  修改日期: 2020-05-26
 *  修改内容:
 * </pre>
*/
@Data
public class IndicatorsLineDTO extends BaseDTO {

    private static final long serialVersionUID = 3137578128391087698L;

    /**
     * 表ID，主键，供其他表做外键
     */
    private Long indicatorLineId;

    /**
     * 头表ID
     */
    private Long indicatorHeadId;

    /**
     * 指标行行说明
     */
    private String indicatorLineDes;

    /**
     * 绩效评分
     */
    private BigDecimal pefScore;

    /**
     * 考核罚款
     */
    private BigDecimal assessmentPenalty;

    /**
     * 评分值开始
     */
    private BigDecimal scoreStart;

    /**
     * 评分值结束
     */
    private BigDecimal scoreEnd;

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

    /**
     * 扩展字段4
     */
    private String attribute4;

}
