package com.midea.cloud.srm.model.file.upload.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <pre>
 *  文件详情记录 模型
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-04 13:39:58
 *  修改内容:
 *          </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_file_fileupload")
public class Fileupload extends BaseEntity {

	private static final long serialVersionUID = 6390591027354729861L;
	/**
	 * ID
	 */
	@TableId("FILEUPLOAD_ID")
	@NotNull(message = "文件ID不能为空", groups = { Default.class })
	private Long fileuploadId;

	/**
	 * 文件指纹
	 */
	@TableField("FINGERPRINT")
	private String fingerprint;

	/**
	 * 上传介质类型
	 */
	@TableField("UPLOAD_TYPE")
	private String uploadType;

	/**
	 * 业务ID，用于业务单据关联
	 */
	@TableField("BUSINESS_ID")
	private Long businessId;

	/**
	 * 文件所属模块
	 */
	@TableField("FILE_MODULAR")
	private String fileModular;

	/**
	 * 文件所属功能
	 */
	@TableField("FILE_FUNCTION")
	private String fileFunction;

	/**
	 * 文件所属类型
	 */
	@TableField("FILE_TYPE")
	private String fileType;

	/**
	 * 文件全路径名
	 */
	@TableField("FILE_FULLNAME")
	private String fileFullname;

	/**
	 * 文件扩展名
	 */
	@TableField("FILE_EXTEND_TYPE")
	private String fileExtendType;

	/**
	 * 原始文件名
	 */
	@TableField("FILE_SOURCE_NAME")
	private String fileSourceName;

	/**
	 * 文件大小
	 */
	@TableField("FILE_SIZE")
	private BigDecimal fileSize;

	/**
	 * 文件路径
	 */
	@TableField("FILE_PATH")
	private String filePath;

	/**
	 * 纯文件名
	 */
	@TableField("FILE_PURE_NAME")
	private String filePureName;

	/**
	 * 来源类型
	 */
	@TableField("SOURCE_TYPE")
	private String sourceType;

	/**
	 * 临时文件过期时间
	 */
	@TableField("EXPIRE_TIME")
	private Date expireTime;

	/**
	 * 场景附件模板原始名称
	 */
	@TableField("SCENE_FILE_SOURCE_NAME")
	private String sceneFileSourceName;

	/**
	 * 场景附件模板文件ID
	 */
	@TableField("SCENE_FILEUPLOAD_ID")
	private Long sceneFileUploadId;

	/**
	 * 场景附件管理ID
	 */
	@TableField("SCENE_ATTACHMENT_ID")
	private Long sceneAttachmentId;

	/**
	 * 备注
	 */
	@TableField("COMMENT")
	private String comment;

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
	 * 版本号
	 */
	@TableField("VERSION")
	private Long version;

	/**
	 * 租户ID
	 */
	@TableField("TENANT_ID")
	private Long tenantId;

	/**
	 * 文件base64序列
	 */
	@TableField(exist = false)
	private String base64;
	
	@TableField(exist = false)
	private Date creationDateBegin;
	
	@TableField(exist = false)
	private Date creationDateEnd;

}
