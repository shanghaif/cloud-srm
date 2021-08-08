package com.midea.cloud.srm.model.base.sceneattachment.entity;

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
 * 功能名称
 * </pre>
 *
 * @author yourname@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 * 修改记录
 * 修改后版本:
 * 修改人:
 * 修改日期: 2020-9-24 11:30
 * 修改内容:
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_base_scene_attachment")
public class SceneAttachment extends BaseEntity {
    @TableId("SCENE_ATTACHMENT_ID")
    private Long sceneAttachmentId;
    @TableField("FILEUPLOAD_ID")
    private Long fileuploadId;
    @TableField("SENCE_CODE")
    private String senceCode;
    @TableField("SENCE_NAME")
    private String senceName;
    @TableField("ENABLED")
    private String enabled;
    @TableField("REQUIRED")
    private String required;
    @TableField("ATTACHMENT_NAME")
    private String attachmentName;
    @TableField("FILE_SOURCE_NAME")
    private String fileSourceName;
    @TableField("ORG_IDS")
    private String orgIds;
    @TableField("CATEGORY_IDS")
    private String categoryIds;
    @TableField("REMARK")
    private String remark;
    @TableField("START_DATE")
    private LocalDate startDate;
    @TableField("END_DATE")
    private LocalDate endDate;
    @TableField(value = "CREATED_BY", fill = FieldFill.INSERT)
    private String createdBy;
    @TableField(value = "CREATION_DATE", fill = FieldFill.INSERT)
    private Date creationDate;
    @TableField(value = "CREATED_BY_IP", fill = FieldFill.INSERT)
    private String createdByIp;
    @TableField(value = "LAST_UPDATED_BY", fill = FieldFill.UPDATE)
    private String lastUpdatedBy;
    @TableField(value = "LAST_UPDATE_DATE", fill = FieldFill.INSERT_UPDATE)
    private Date lastUpdateDate;
    @TableField("LAST_UPDATED_IP")
    private String lastUpdatedIp;
    @TableField("VERSION")
    private Long version;
    @TableField("TENANT_ID")
    private Long tenantId;
    @TableField("CEEA_SMALL_MODULE")
    private String ceeaSmallModule;//功能场景下的小模块名称
}
