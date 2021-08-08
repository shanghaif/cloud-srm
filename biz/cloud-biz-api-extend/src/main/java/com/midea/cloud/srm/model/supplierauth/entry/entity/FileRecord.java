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
 *  准入附件记录 模型
 * </pre>
 *
 * @author kuangzm
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Jan 25, 2021 11:15:34 PM
 *  修改内容:
 *          </pre>
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sup_file_record")
public class FileRecord extends BaseEntity {
	private static final long serialVersionUID = 931910L;
	/**
	 * ID
	 */
	@TableId("FILE_RECORD_ID")
	private Long fileRecordId;
	/**
	 * 附件模板配置ID
	 */
	@TableField("FILE_CONFIG_ID")
	private Long fileConfigId;
	/**
	 * 现场评审单ID
	 */
	@TableField("FORM_ID")
	private Long formId;
	/**
	 * 单据类型
	 */
	@TableField("FORM_TYPE")
	private String formType;
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
	 * 文件上传ID
	 */
	@TableField("FILE_ID")
	private Long fileId;
	/**
	 * 原始文件名
	 */
	@TableField("FILE_NAME")
	private String fileName;
	/**
	 * 附件是否必填
	 */
	@TableField("IF_REQUIRED")
	private String ifRequired;
	/**
	 * 附件有效期z止是否必填
	 */
	@TableField("IF_VALID_DATE")
	private String ifValidDate;
	/**
	 * 文件有效期止
	 */
	@TableField("FILE_VALID_DATE")
	private Date fileValidDate;
	/**
	 * 评审人员
	 */
	@TableField("REVIEW_PEOPLE")
	private String reviewPeople;
	/**
	 * 供方陪审员
	 */
	@TableField("VENDOR_ASSESSOR")
	private String vendorAssessor;
	/**
	 * 评审日期
	 */
	@TableField("REVIEW_DATE")
	private Date reviewDate;
	/**
	 * 得分
	 */
	@TableField("SCORE")
	private Double score;
	/**
	 * 认证结果
	 */
	@TableField("AUTH_RESULT")
	private String authResult;
	/**
	 * 备注
	 */
	@TableField("REMARK")
	private String remark;
	/**
	 * 生效日期(YYYY-MM-DD)
	 */
	@TableField("START_DATE")
	private Date startDate;
	/**
	 * 失效日期(YYYY-MM-DD)
	 */
	@TableField("END_DATE")
	private Date endDate;
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
	 * 是否启用提醒
	 */
	@TableField("IS_USE_REMINDER")
	private String isUseReminder;

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