package com.midea.cloud.srm.model.bid.purchaser.projectmanagement.evaluation.entity;

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
 *  招标评分规则模板表 模型
 * </pre>
*
* @author fengdc3@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-29 09:39:06
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_bid_score_rule_config")
public class ScoreRuleConfig extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 模板主键ID
     */
    @TableId("RULE_CONFIG_ID")
    private Long ruleConfigId;

    /**
     * 评分规则名称
     */
    @TableField("RULE_CONFIG_NAME")
    private String ruleConfigName;

    /**
     * 评分规则编码
     */
    @TableField("RULE_CONFIG_CODE")
    private String ruleConfigCode;

    /**
     * 总分值
     */
    @TableField("TOTAL_SCORE")
    private BigDecimal totalScore;

    /**
     * 寻源方式
     */
    @TableField("SOURCING_WAY")
    private String sourcingWay;

    /**
     * 备注
     */
    @TableField("COMMENTS")
    private String comments;

    /**
     * 是否有效  Y:有效  N:无效
     */
    @TableField("ENABLE_FLAG")
    private String enableFlag;

    /**
     * 企业编码
     */
    @TableField("COMPANY_CODE")
    private String companyCode;

    /**
     * 组织编码
     */
    @TableField("ORGANIZATION_CODE")
    private String organizationCode;

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
     * 拥有者/租户/所属组等
     */
    @TableField("TENANT_ID")
    private String tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;


    /* ===================== 定制化字段 ===================== */

    /**
     * 评选方法 - {@link EvaluateMethodEnum}.
     */
    @TableField("CEEA_EVALUATE_METHOD")
    private String evaluateMethod;
}
