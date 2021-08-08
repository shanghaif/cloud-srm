package com.midea.cloud.srm.model.flow.process.entity;

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
 *  流程事件表 模型
 * </pre>
*
* @author wuwl18@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-18 17:59:53
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("srm_cbpm_flowinstance_event")
public class FlowinstanceEvent extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId("EVENT_ID")
    private Long eventId;

    /**
     * 流程实例ID
     */
    @TableField("INSTANCE_ID")
    private Long instanceId;

    /**
     * CBPM流程模板ID
     */
    @TableField("MODEL_ID")
    private Long modelId;

    /**
     * 业务审批ID
     */
    @TableField("BUSINESS_ID")
    private Long businessId;

    /**
     * 模板编码
     */
    @TableField("TEMPLATE_CODE")
    private String templateCode;
    /**事件参数*/
    @TableField("EVENT_DATA")
    private String eventData;
    /**事件状态*/
    @TableField("EVENT_STATUS")
    private String eventStatus;
    /**事件类型*/
    @TableField("EVENT_TYPE")
    private String eventType;

    /**
     * 错误信息
     */
    @TableField("ERROR_MESSAGE")
    private String errorMessage;

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

    @TableField("ATTRIBUTE_CATEGORY")
    private String attributeCategory;

    @TableField("ATTRIBUTE1")
    private String attribute1;

}
