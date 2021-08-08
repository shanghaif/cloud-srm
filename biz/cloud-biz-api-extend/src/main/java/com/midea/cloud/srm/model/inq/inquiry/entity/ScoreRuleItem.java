package com.midea.cloud.srm.model.inq.inquiry.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
*  <pre>
 *  招标评分规则表 模型
 * </pre>
*
* @author zhongbh
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-12 18:46:55
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_inquiry_score_rule_item")
public class ScoreRuleItem extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("SCORE_RULE_ITEM_ID")
    private Long scoreRuleItemId;

    @TableId("SCORE_RULE_ID")
    private Long scoreRuleId;

    /**
     * 维度
     */
    @TableField("DIMENSION")
    private String dimension;

    /**
     * 模板状态
     */
    @TableField("STATUS")
    private String status;

    /**
     * 评分项
     */
    @TableField("SCORE_ITEM")
    private String scoreItem;

    /**
     * 评分规则
     */
    @TableField("SCORE_RULE")
    private String scoreRule;

    /**
     * 取值来源
     */
    @TableField("SCORE_Source")
    private String scoreSource;

    /**
     * 权重
     */
    @TableField("SCORE_WEIGHT")
    private BigDecimal scoreWeight;


    /**
     * 满分值
     */
    @TableField("FULL_SCORE")
    private BigDecimal fullScore;

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
     * 最后更新人
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
     * 版本号
     */
    @TableField("VERSION")
    private Long version;

    /**
     * 租户ID
     */
    @TableField("TENANT_ID")
    private Long tenantId;


}
