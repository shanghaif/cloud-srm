package com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.techscore.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <pre>
 * 技术评分头表
 * </pre>
 *
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-3-19 17:49:37
 *  修改内容:
 *          </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_lgt_tech_score_head")
public class TechScoreHead extends BaseEntity {

	private static final long serialVersionUID = 6093227893135184017L;

	/**
	 * 主键ID
	 */
	@TableId("TECH_SCORE_HEAD_ID")
	private Long techScoreHeadId;

	/**
	 * 招标单ID
	 */
	@TableField("BIDING_ID")
	private Long bidingId;

	/**
	 * 供应商ID
	 */
	@TableField("BID_VENDOR_ID")
	private Long bidVendorId;

	/**
	 * 评分状态
	 */
	@TableField("SCORE_STATUS")
	private String scoreStatus;

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
	@TableField(value = "CREATED_ID")
	private Long createdId;

	/**
	 * 创建人
	 */
	@TableField(value = "CREATED_BY")
	private String createdBy;

	/**
	 * 创建日期
	 */
	@TableField(value = "CREATION_DATE")
	private Date creationDate;

	/**
	 * 创建人IP
	 */
	@TableField(value = "CREATED_BY_IP")
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
	@TableField(value = "LAST_UPDATE_DATE", fill = FieldFill.UPDATE)
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

	/**
	 * 技术评分意见
	 */
	@TableField("TECH_COMMENTS")
	private String techComments;
}
