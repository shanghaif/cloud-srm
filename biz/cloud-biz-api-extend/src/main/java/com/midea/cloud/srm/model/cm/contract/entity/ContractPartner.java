package com.midea.cloud.srm.model.cm.contract.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.Date;

/**
 * <pre>
 *  合作伙伴 模型
 * </pre>
 *
 * @author haiping2.li@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-8-4 17:22
 *  修改内容:
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_contract_partner")
public class ContractPartner extends BaseEntity {
	private static final long serialVersionUID = -6375081050799046846L;

	@TableId("PARTNER_ID")
	private Long partnerId; //主键ID

	@TableField("CONTRACT_HEAD_ID")
	private Long contractHeadId; // 合同头信息ID

	@TableField("PARTNER_TYPE")
	private String partnerType; //伙伴类型

	@TableField("PARTNER_NAME")
	private String partnerName; //伙伴名称

	@TableField("CONTACT_NAME")
	private String contactName; //代表人

	@TableField("PHONE")
	private String phone; //联系电话

	@TableField("ADDRESS")
	private String address; //地址信息

	@TableField("BANK_NAME")
	private String bankName; //开户银行

	@TableField("BANK_ACCOUNT")
	private String bankAccount; //银行账号

	@TableField("POST_CODE")
	private String postCode; //邮编

	@TableField("TAX_NUMBER")
	private String taxNumber; //税号

	/**
	 * 生效日期(YYYY-MM-DD)
	 */
	@TableField("START_DATE")
	private LocalDate startDate;

	/**
	 * 失效日期(YYYY-MM-DD)
	 */
	@TableField("END_DATE")
	private LocalDate endDate;

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

	/**
	 * 变更字段
	 */
	@TableField("CHANGE_FIELD")
	private String changeField;

	/**
	 * 原合同合同物料ID
	 */
	@TableField("SOURCE_ID")
	private Long sourceId;

	/**
	 * 是否激活
	 */
	@TableField("ENABLE")
	private String enable;

	/**
	 * ouId
	 */
	@TableField("CEEA_OU_ID")
	private Long ouId;

	/**
	 * 物料ID
	 */
	@TableField("MATERIAL_ID")
	private Long materialId;

	/**
	 * 业务实体编码
	 */
	@TableField("CEEA_OU_CODE")
	private String ouCode;

	/**
	 * 业务实体名称
	 */
	@TableField("CEEA_OU_NAME")
	private String ouName;
}
