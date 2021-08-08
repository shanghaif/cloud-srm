package com.midea.cloud.srm.model.base.projecttask.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.time.LocalDate;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
*  <pre>
 *  项目任务配置表 模型
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-12-06 17:57:38
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_base_project_task")
public class ProjectTask extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 项目任务配置ID
     */
    @TableId("PROJECT_TASK_ID")
    private Long projectTaskId;

    /**
     * ouID
     */
    @TableField("ORG_ID")
    private Long orgId;

    /**
     * ou编码
     */
    @TableField("ORG_CODE")
    private String orgCode;

    /**
     * ou名称
     */
    @TableField("ORG_NAME")
    private String orgName;

    /**
     * 项目ID
     */
    @TableField("PROJECT_ID")
    private String projectId;

    /**
     * 项目编号
     */
    @TableField("PROJECT_NUM")
    private String projectNum;

    /**
     * 项目名称
     */
    @TableField("PROJECT_NAME")
    private String projectName;

    /**
     * 任务ID
     */
    @TableField("TASK_ID")
    private String taskId;

    /**
     * 任务编号
     */
    @TableField("TASK_NUM")
    private String taskNum;

    /**
     * 任务名称
     */
    @TableField("TASK_NAME")
    private String taskName;

    /**
     * 生效日期(YYYY-MM-DD)
     */
    @TableField("START_DATE")
    private LocalDate startDate;

    /**
     * 失效日期(YYYY-MM-DD)
     */
    @TableField("END_DATE")
    private LocalDate endDate;

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
     * 来源系统（如：erp系统）
     */
    @TableField("SOURCE_SYSTEM")
    private String sourceSystem;

    /**
     * 项目类型名称
     */
    @TableField("PROJECT_TYPE_NAME")
    private String projectTypeName;

    /**
     * 项目全称
     */
    @TableField("PROJECT_LONG_NAME")
    private String projectLongName;

    /**
     * 项目状态编码
     */
    @TableField("PROJECT_STATUS_CODE")
    private String projectStatusCode;

    /**
     * 项目状态名称
     */
    @TableField("PROJECT_STATUS_NAME")
    private String projectStatusName;

    /**
     * OA项目编号
     */
    @TableField("PROJECT_OA_NUMBER")
    private String projectOaNumber;


}
