package com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techscore.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.util.Date;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <pre>
 * 技术评分行表
 * </pre>
 *
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-3-19 17:53:34
 *  修改内容:
 *          </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_brg_tech_score_line")
public class TechScoreLine extends BaseEntity {

	private static final long serialVersionUID = -8540073965730918467L;

	/**
	 * 主键ID
	 */
	@TableId("TECH_SCORE_LINE_ID")
	private Long techScoreLineId;

	/**
	 * 招标单ID
	 */
	@TableField("BIDING_ID")
	private Long bidingId;

	/**
	 * 招标评分规则明细表ID,scc_brg_score_rule_line
	 */
	@TableField("RULE_LINE_ID")
	private Long ruleLineId;

	/**
	 * 技术评分头表ID,bid_biding_tech_score_head
	 */
	@TableField("TECH_SCORE_HEAD_ID")
	private Long techScoreHeadId;

	/**
	 * 评分
	 */
	@TableField("SCORE")
	private BigDecimal score;

	/**
	 * 备注
	 */
	@TableField("COMMENTS")
	private String comments;

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
	 * 创建日期
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
	 * 租户ID
	 */
	@TableField("TENANT_ID")
	private Long tenantId;

	/**
	 * 版本号
	 */
	@TableField("VERSION")
	private Long version;

}
