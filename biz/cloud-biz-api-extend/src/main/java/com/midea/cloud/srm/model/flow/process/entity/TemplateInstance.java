package com.midea.cloud.srm.model.flow.process.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
*  <pre>
 *  流程实例表
 * </pre>
*
* @author wuwl18@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-17 14:05:14
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("srm_cbpm_template_instance")
public class TemplateInstance extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "INSTANCE_ID")
    private Long instanceId;

    /**
     * 业务审批ID-businessId
     */
    @TableField("BUSINESS_ID")
    private Long businessId;

    /**
     * 模板编码-businessKey
     */
    @TableField("TEMPLATE_CODE")
    private String templateCode;

    /**
     * 外部CBPM实例ID-fdId
     */
    @TableField("CBPM_INSTANCE_ID")
    private Long cbpmInstanceId;

    /**
     * CBPM流程模板ID-templateId
     */
    @TableField("MODEL_ID")
    private Long modelId;

    /**
     * 姓名
     */
    @TableField("LOGIN_NAME")
    private String loginName;

    /**
     * 状态
     */
    @TableField("FLOW_STATUS")
    private String flowStatus;

    /**创建人ID*/
    @TableField("CREATED_ID")
    private Long createdId;

    /**创建人IP*/
    @TableField("CREATED_BY_IP")
    private String createdByIp;

    /**最后修改人ID*/
    @TableField("LAST_UPDATED_ID")
    private Long lastUpdatedId;

    /**最后修改人IP*/
    @TableField("LAST_UPDATED_BY_IP")
    private String lastUpdatedByIp;

    /**租户ID*/
    @TableField("TENANT_ID")
    private String tenantId;

    /**
     * 创建人账号
     */
    @TableField("CREATED_BY")
    private String createdBy;

    /**
     * 创建时间
     */
    @TableField("CREATION_DATE")
    private Date creationDate;

    /**
     * 最后更新时间
     */
    @TableField("LAST_UPDATED_BY")
    private String lastUpdatedBy;

    /**
     * 最后更新时间
     */
    @TableField("LAST_UPDATE_DATE")
    private Date lastUpdateDate;

    /**
     * 创建人姓名
     */
    @TableField("CREATED_FULL_NAME")
    private String createdFullName;

    /**
     * 最后更新人姓名
     */
    @TableField("LAST_UPDATED_FULL_NAME")
    private String lastUpdatedFullName;

    /**
     * 是否删除 0不删除 1删除
     */
    @TableField("DELETE_FLAG")
    private Integer deleteFlag;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;
    /**属性类别*/
    @TableField("ATTRIBUTE_CATEGORY")
    private String attributeCategory;
    /**备用字段1*/
    @TableField("ATTRIBUTE1")
    private String attribute1;

    /**
     * 数据来源（数据迁移用）
     */
    @TableField("DATA_SOURCES")
    private String dataSources;

    /**
     * 数据来源主键ID（数据迁移用）
     */
    @TableField("DATA_SOURCES_ID")
    private Long dataSourcesId;

    /**
     * 表单ID（数据迁移用）
     */
    @TableField("FORM_ID")
    private Long formId;

    /**
     * 表单实例ID（数据迁移用）
     */
    @TableField("SOURCE_TABLE_ID")
    private Long sourceTableId;

    /**
     * 表单实例表名（数据迁移用）
     */
    @TableField("SOURCE_TABLE")
    private String sourceTable;

}
