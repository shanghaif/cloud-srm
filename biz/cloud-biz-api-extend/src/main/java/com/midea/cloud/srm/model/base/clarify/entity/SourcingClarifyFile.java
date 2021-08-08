package com.midea.cloud.srm.model.base.clarify.entity;

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
 * <pre>
 *  澄清附件 模型
 * </pre>
 *
 * @author tanjl11@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-10-07 13:47:41
 *  修改内容:
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_sourcing_clarify_file")
public class SourcingClarifyFile extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 澄清公告的文件id
     */
    @TableId("CLARIFY_FILE_ID")
    private Long clarifyFileId;

    /**
     * 澄清类型 发布/回复
     */
    @TableField("CLARIFY_TYPE")
    private String clarifyType;

    /**
     * 文件中心的id
     */
    @TableField("FILE_UPLOAD_ID")
    private Long fileUploadId;

    /**
     * 文件名
     */
    @TableField("CLARIFY_FILE_NAME")
    private String clarifyFileName;

    /**
     * 文件备注
     */
    @TableField("COMMENTS")
    private String comments;

    /**
     * 澄清id
     */
    @TableField("CLARIFY_ID")
    private Long clarifyId;

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
     * 最后更新时间
     */
    @TableField(value = "LAST_UPDATE_DATE", fill = FieldFill.INSERT_UPDATE)
    private Date lastUpdateDate;

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
     * 最后更新人IP
     */
    @TableField(value = "LAST_UPDATED_BY_IP", fill = FieldFill.INSERT_UPDATE)
    private String lastUpdatedByIp;

    /**
     * 租户
     */
    @TableField("TENANT_ID")
    private Long tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;


}
