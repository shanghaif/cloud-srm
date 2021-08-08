package com.midea.cloud.srm.model.pm.pr.requirement.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.util.List;

import com.midea.cloud.srm.model.base.organization.entity.Category;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
*  <pre>
 *  采购申请表（隆基采购申请同步） 模型
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-01 09:45:43
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_pr_requisition")
public class Requisition extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 业务实体ID
     */
    @TableField("OPERATION_UNIT_ID")
    private Long operationUnitId;

    /**
     * 业务实体名称
     */
    @TableField("OPERATION_NAME")
    private String operationName;

    /**
     * 申请ID
     */
    @TableId("REQUEST_HEADER_ID")
    private Long requestHeaderId;

    /**
     * 申请编号
     */
    @TableField("REQUEST_NUMBER")
    private String requestNumber;

    /**
     * 启用标志
     */
    @TableField("ENABLE_FLAG")
    private String enableFlag;

    /**
     * 起始日期
     */
    @TableField("START_DATE")
    private String startDate;

    /**
     * 截止日期
     */
    @TableField("END_DATE")
    private String endDate;

    /**
     * 状态（传送已审批、已取消）
     */
    @TableField("AUTH_STATUS")
    private String authStatus;

    /**
     * 申请类型
     */
    @TableField("DOCUMENT_TYPE")
    private String documentType;

    /**
     * 项目编号
     */
    @TableField("PROJECT_NUMBER")
    private String projectNumber;

    /**
     * 项目名称
     */
    @TableField("PROJECT_NAME")
    private String projectName;

    /**
     * 审批日期
     */
    @TableField("APPROVE_DATE")
    private String approveDate;

    /**
     * 说明
     */
    @TableField("DESCRIPTION")
    private String description;

    /**
     * 接口状态(NEW-新增，UPDATE-更新)
     */
    @TableField("ITF_STATUS")
    private String itfStatus;

    /**
     * 备用字段1
     */
    @TableField("ATTR1")
    private String attr1;

    /**
     * 备用字段2
     */
    @TableField("ATTR2")
    private String attr2;

    /**
     * 备用字段3
     */
    @TableField("ATTR3")
    private String attr3;

    /**
     * 备用字段4
     */
    @TableField("ATTR4")
    private String attr4;

    /**
     * 备用字段5
     */
    @TableField("ATTR5")
    private String attr5;

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
     * 最后更新人
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

    /**
     * 主键ID
     */
    @TableField("REQUIREMENT_HEAD_ID")
    private Long requirementHeadId;

    /**
     * 采购申请头导入状态(1:已导入业务表,0:未导入业务表)
     */
    @TableField("HEAD_IMPORT_STATUS")
    private int headImportStatus;

}
