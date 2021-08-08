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
 *  准入附件模板配置 模型
 * </pre>
 *
 * @author kuangzm
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Jan 25, 2021 10:56:39 PM
 *  修改内容:
 *          </pre>
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sup_entry_file_config")
public class EntryFileConfig extends BaseEntity {
	private static final long serialVersionUID = 909422L;
	/**
	 * 附件配置ID
	 */
	@TableId("FILE_CONFIG_ID")
	private Long fileConfigId;
	/**
	 * 准入流程头表ID
	 */
	@TableField("ENTRY_CONFIG_ID")
	private Long entryConfigId;
	/**
	 * 模板描述
	 */
	@TableField("TEMPLATE_DESC")
	private String templateDesc;
	/**
	 * 附件模板ID
	 */
	@TableField("TEMPLATE_FILE_ID")
	private Long templateFileId;
	/**
	 * 附件模板名称
	 */
	@TableField("TEMPLATE_FILE_NAME")
	private String templateFileName;
	/**
	 * 附件是否必填
	 */
	@TableField("IF_REQUIRED")
	private String ifRequired;
	/**
	 * 有效期止是否必填
	 */
	@TableField("IF_VALID_DATE")
	private String ifValidDate;
	/**
	 * 附件类型
	 */
	@TableField("TYPE")
	private String type;
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