package com.midea.cloud.srm.model.logistics.bargaining.purchaser.projectmanagement.businessproposal.entity;

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
 * 
 * 
 * <pre>
 * 轮次表
 * </pre>
 * 
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 * 
 *          <pre>
 *  修改记录
 *  修改后版本: 
 *  修改人: 
 *  修改日期: 2020年3月25日 下午4:59:08  
 *  修改内容:
 *          </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_brg_round")
public class Round extends BaseEntity {

	private static final long serialVersionUID = -332418803361527835L;

	/**
	 * 主键ID
	 */
	@TableId("ROUND_ID")
	private Long roundId;

	/**
	 * 招标单ID
	 */
	@TableField("BIDING_ID")
	private Long bidingId;

	/**
	 * 当前轮次 -1为跟标轮次
	 */
	@TableField("ROUND")
	private Integer round;

	/**
	 * 投标/报价开始时间
	 */
	@TableField("START_TIME")
	private Date startTime;

	/**
	 * 投标/报价结束时间
	 */
	@TableField("END_TIME")
	private Date endTime;

	/**
	 * 商务开标
	 */
	@TableField("BUSINESS_OPEN_BID")
	private String businessOpenBid;
	
	/**
	 * 商务开标时间
	 */
	@TableField("BUSINESS_OPEN_BID_TIME")
	private Date businessOpenBidTime;

	/**
	 * 公示结果
	 */
	@TableField("PUBLIC_RESULT")
	private String publicResult;

	/**
	 * 公示时间
	 */
	@TableField("PUBLIC_RESULT_TIME")
	private Date publicResultTime;

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
