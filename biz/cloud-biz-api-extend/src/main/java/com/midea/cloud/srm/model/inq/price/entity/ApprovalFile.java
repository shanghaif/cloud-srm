package com.midea.cloud.srm.model.inq.price.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 *  <pre>
 *  价格审批单附件 模型
 * </pre>
 *
 * @author chensl26@meiCloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-04-23 17:40:03
 *  修改内容:
 * </pre>
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_price_approval_file")
public class ApprovalFile extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 审批单附件id
     */
    @TableId("APPROVAL_FILE_ID")
    private Long approvalFileId;

    /**
     * 审批单头id
     */
    @TableField("APPROVAL_HEADER_ID")
    private Long approvalHeaderId;

    /**
     * 文件关联id(关联文件中心)
     */
    @TableField("FILE_RELATION_ID")
    private Long fileRelationId;

    /**
     * 文件名
     */
    @TableField("FILE_NAME")
    private String fileName;

    /**
     * 说明
     */
    @TableField("REMARK")
    private String remark;

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
    private String tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;
}
