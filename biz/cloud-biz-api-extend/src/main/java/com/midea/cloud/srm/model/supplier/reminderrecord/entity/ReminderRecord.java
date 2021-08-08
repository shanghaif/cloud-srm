package com.midea.cloud.srm.model.supplier.reminderrecord.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sup_reminder_record")
public class ReminderRecord extends BaseEntity {

    private static final long serialVersionUID = 931910L;

    /**
     * ID
     */
    @TableField("REMINDER_RECORD_ID")
    private Long reminderRecordId;

    /**
     * 其他证件ID
     */
    @TableField("MIX_ID")
    private Long mixId;

    /**
     * 供应商编码
     */
    @TableField("VENDOR_CODE")
    private String vendorCode;

    /**
     * 供应商名称名称
     */
    @TableField("VENDOR_NAME")
    private String vendorName;

    /**
     * 来源类型
     */
    @TableField("FORM_TYPE")
    private String formType;

    /**
     * 来源单据
     */
    @TableField("DATA_SOURCES")
    private String dataSources;

    /**
     * 到期时间
     */
    @TableField("END_DATE")
    private LocalDate endDate;

    /**
     * 到期时间 旧
     */
    @TableField("END_DATE_OLD")
    private LocalDate endDateOld;

    /**
     * 文件id
     */
    @TableField("FILEUPLOAD_ID")
    private Long fileUploadId;

    /**
     * 认证文件
     */
    @TableField("AUTH_TYPE")
    private String authType;

    /**
     * 文件id 旧
     */
    @TableField("FILEUPLOAD_ID_OLD")
    private Long fileUploadIdOld;

    /**
     * 认证文件 旧
     */
    @TableField("AUTH_TYPE_OLD")
    private String authTypeOld;

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
