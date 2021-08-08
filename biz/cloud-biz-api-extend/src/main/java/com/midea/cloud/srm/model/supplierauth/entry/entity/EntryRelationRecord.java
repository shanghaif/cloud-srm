package com.midea.cloud.srm.model.supplierauth.entry.entity;

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
 *  准入组织品类关系记录表 模型
 * </pre>
 *
 * @author scc_sup_entry_relation_record
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Jan 26, 2021 1:47:56 PM
 *  修改内容:
 *          </pre>
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sup_entry_relation_record")
public class EntryRelationRecord extends BaseEntity {
	private static final long serialVersionUID = 502844L;
	/**
	 * 记录id
	 */
	@TableId("RELATION_ID")
	private Long relationId;
	/**
	 * 记录id
	 */
	@TableField("RECORD_ID")
	private Long recordId;
	/**
	 * 是否需要供应商评审
	 */
	@TableField("IF_AUTH")
	private String ifAuth;
	/**
	 * 是否需要样品确认
	 */
	@TableField("IF_AUTH_SAMPLE")
	private String ifAuthSample;
	/**
	 * 是否需要物料试用
	 */
	@TableField("IF_MATERIAL")
	private String ifMaterial;
	/**
	 * 组织ID
	 */
	@TableField("ORGANIZATION_ID")
	private Long organizationId;
	/**
	 * 组织名称
	 */
	@TableField("ORGANIZATION_NAME")
	private String organizationName;
	/**
	 * 品类ID
	 */
	@TableField("CATEGORY_ID")
	private Long categoryId;
	/**
	 * 品类名称
	 */
	@TableField("CATEGORY_NAME")
	private String categoryName;
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
}