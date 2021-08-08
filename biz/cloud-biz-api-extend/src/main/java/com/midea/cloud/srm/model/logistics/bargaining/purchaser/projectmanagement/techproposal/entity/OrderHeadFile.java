package com.midea.cloud.srm.model.logistics.bargaining.purchaser.projectmanagement.techproposal.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_order_head_file")
public class OrderHeadFile extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 投标头附件id
     */
    @TableId("ORDER_HEAD_FILE_ID")
    private Long orderHeadFileId;
    /**
     * 投标头id
     */
    @TableField("ORDER_HEAD_ID")
    private Long orderHeadId;
    /**
     * 附件类型,商务标、技术标
     */
    @TableField("FILE_TYPE")
    private String vendorReferenceFileType;

    /**
     * 文件大小
     */
    @TableField("FILE_SIZE")
    private String fileSize;

    /**
     * 文档中心id
     */
    @TableField("DOC_ID")
    private Long vendorReferenceFileId;

    /**
     * 文件名
     */
    @TableField("FILE_NAME")
    private String vendorReferenceFileName;
    /**
     * 备注
     */
    @TableField("COMMENTS")
    private String comments;
    /**
     * 配置文件Id
     */
    @TableField("CONFIG_FILE_ID")
    private Long configFileId;
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
     * 更新人
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
    private String tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;


}