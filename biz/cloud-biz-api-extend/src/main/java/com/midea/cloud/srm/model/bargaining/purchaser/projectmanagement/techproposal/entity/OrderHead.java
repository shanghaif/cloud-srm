package com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techproposal.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.enums.RequirementPricingType;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <pre>
 * 供应商投标头表
 * </pre>
 *
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020年3月21日 下午2:28:11
 *  修改内容:
 *          </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_brg_order_head")
public class OrderHead extends BaseEntity {

	private static final long serialVersionUID = 1128575809614689464L;

	/**
	 * 主键ID
	 */
	@TableId("ORDER_HEAD_ID")
	private Long orderHeadId;

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
	 * 轮次表ID
	 */
	@TableField("BID_ROUND_ID")
	private Long bidRoundId;

	/**
	 * 投标单号
	 */
	@TableField("BID_ORDER_NUM")
	private String bidOrderNum;

	/**
	 * 轮次
	 */
	@TableField("ROUND")
	private Integer round;

	/**
	 * 投标状态
	 */
	@TableField("ORDER_STATUS")
	private String orderStatus;

	/**
	 * 提交时间
	 */
	@TableField("SUBMIT_TIME")
	private Date submitTime;

	/**
	 * 撤回原因
	 */
	@TableField("WITH_DRAW_REASON")
	private String withDrawReason;

	/**
	 * 撤回时间
	 */
	@TableField("WITH_DRAW_TIME")
	private Date withDrawTime;

	/**
	 * 作废原因
	 */
	@TableField("REJECT_REASON")
	private String rejectReason;

	/**
	 * 作废时间
	 */
	@TableField("REJECT_TIME")
	private Date rejectTime;

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
	@TableField(value = "LAST_UPDATED_ID", fill = FieldFill.INSERT_UPDATE)
	private Long lastUpdatedId;

	/**
	 * 最后更新人
	 */
	@TableField(value = "LAST_UPDATED_BY", fill = FieldFill.INSERT_UPDATE)
	private String lastUpdatedBy;

	/**
	 * 最后更新时间
	 */
	@TableField(value = "LAST_UPDATE_DATE", fill = FieldFill.INSERT_UPDATE)
	private Date lastUpdateDate;

	/**
	 * 最后更新人IP
	 */
	@TableField(value = "LAST_UPDATED_BY_IP", fill = FieldFill.INSERT_UPDATE)
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


	/* ===================== 定制化字段 ===================== */

	/**
	 * 是否代理报价（Y / N）
	 */
	@TableField("CEEA_IS_PROXY_BIDDING")
	private String isProxyBidding;

	/**
	 * 报价类型 {@link RequirementPricingType}.
	 */
	@TableField("CEEA_PRICING_TYPE")
	private String  pricingType;

}
