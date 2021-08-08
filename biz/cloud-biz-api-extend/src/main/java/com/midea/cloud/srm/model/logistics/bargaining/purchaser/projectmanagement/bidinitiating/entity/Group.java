package com.midea.cloud.srm.model.logistics.bargaining.purchaser.projectmanagement.bidinitiating.entity;

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
 *  招标工作小组表 模型
 * </pre>
 *
 * @author fengdc3@meiCloud.com
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-20 13:59:12
 *  修改内容:
 *          </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_brg_group")
public class Group extends BaseEntity {

	private static final long serialVersionUID = 3558701435108499276L;

	/**
	 * 主键ID
	 */
	@TableId("GROUP_ID")
	private Long groupId;

	/**
	 * 招标ID
	 */
	@TableField("BIDING_ID")
	private Long bidingId;

	/**
	 * 成员账号
	 */
	@TableField("USER_NAME")
	private String userName;

	/**
	 * 成员ID
	 */
	@TableField("USER_ID")
	private Long userId;

	/**
	 * 成员姓名
	 */
	@TableField("FULL_NAME")
	private String fullName;

	/**
	 * 电话
	 */
	@TableField("PHONE")
	private String phone;

	/**
	 * 邮箱
	 */
	@TableField("EMAIL")
	private String email;

	/**
	 * 岗位
	 */
	@TableField("POSITION")
	private String position;

	/**
	 * 是否成为评委
	 */
	@TableField("JUDGE_FLAG")
	private String judgeFlag;

	/**
	 * 是否已确认
	 */
	@TableField("CONFIRMED_FLAG")
	private String confirmedFlag;

	/**
	 * 确认时间
	 */
	@TableField("CONFIRME_DATETIME")
	private Date confirmeDatetime;

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
	@TableField(value = "LAST_UPDATED_BY_IP", fill = FieldFill.UPDATE)
	private String lastUpdatedByIp;

	/**
	 * 拥有者/租户/所属组等
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
	 * 最高可评分数
	 */
	@TableField("CEEA_MAX_EVALUATE_SCORE")
	private Long  	maxEvaluateScore;

	/**
	 * 是否第一责任人（Y/N）
	 */
	@TableField("CEEA_IS_FIRST_RESPONSE")
	private String	isFirstResponse;
}
