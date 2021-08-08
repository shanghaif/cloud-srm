package com.midea.cloud.srm.model.supplier.change.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
*  <pre>
 *  文件详情记录变更 模型
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-04-20 10:43:38
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sup_fileupload_change")
public class FileuploadChange extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 文件详情变更ID
     */
    @TableId("FILEUPLOAD_CHANGE_ID")
    private Long fileuploadChangeId;

    /**
     * 变更ID
     */
    @TableField("CHANGE_ID")
    private Long changeId;

    /**
     * ID
     */
    @TableField("FILEUPLOAD_ID")
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
    private Long sceneFileuploadId;

    /**
     * 场景附件管理ID
     */
    @TableField("SCENE_ATTACHMENT_ID")
    private Long sceneAttachmentId;

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
     * 更新人
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

    /**
     * 操作类型
     */
    @TableField("OP_TYPE")
    private String opType;


}
