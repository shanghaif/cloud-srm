package com.midea.cloud.srm.model.flow.process.entity;

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
 *  工作流接口访问配置表 模型
 * </pre>
*
* @author wuwl18@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-04-26 19:28:59
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("srm_flow_interface_set")
public class FlowInterfaceSet extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId("INTERFACE_SET_ID")
    private Long interfaceSetId;

    /**
     * 流程模板头表ID
     */
    @TableField("APP_ID")
    private String appId;

    /**
     * 事件类型(业务单据类型)
     */
    @TableField("SALT")
    private String salt;

    /**
     * 业务系统ID
     */
    @TableField("SYS_ID")
    private String sysId;

    /**
     * 系统URL
     */
    @TableField("URL")
    private String url;

    /**
     * 调用地址
     */
    @TableField("SOURCE_ADDR")
    private String sourceAddr;

    /**
     * 跳转地址
     */
    @TableField("FORWARD_TO")
    private String forwardTo;

    /**
     * 服务名称前缀
     */
    @TableField("SERVICE_NAME")
    private String serviceName;

    /**
     * 门户系统cbpm工作流accessKeyId
     */
    @TableField("CBPM_ACCESS_KEY_ID")
    private String cbpmAccessKeyId;

    /**
     * 门户系统cbpm工作流accessKeySecret
     */
    @TableField("CBPM_ACCESS_KEY_SECRET")
    private String cbpmAccessKeySecret;

    /**
     * 创建人账号
     */
    @TableField(value = "CREATED_BY", fill = FieldFill.INSERT)
    private String createdBy;

    /**
     * 创建时间
     */
    @TableField(value = "CREATION_DATE", fill = FieldFill.INSERT)
    private Date creationDate;

    /**
     * 创建人ID
     */
    @TableField(value = "CREATED_ID", fill = FieldFill.INSERT)
    private Long createdId;

    /**
     * 创建人IP
     */
    @TableField(value = "CREATED_BY_IP", fill = FieldFill.INSERT)
    private String createdByIp;

    /**
     * 最后更新时间
     */
    @TableField(value = "LAST_UPDATED_BY", fill = FieldFill.UPDATE)
    private String lastUpdatedBy;

    /**
     * 最后更新人ID
     */
    @TableField(value = "LAST_UPDATED_ID", fill = FieldFill.UPDATE)
    private Long lastUpdatedId;

    /**
     * 最后更新人IP
     */
    @TableField(value = "LAST_UPDATED_BY_IP", fill = FieldFill.UPDATE)
    private String lastUpdatedByIp;

    /**
     * 最后更新时间
     */
    @TableField(value = "LAST_UPDATE_DATE", fill = FieldFill.INSERT_UPDATE)
    private Date lastUpdateDate;

    /**
     * 创建人姓名
     */
    @TableField(value = "CREATED_FULL_NAME", fill = FieldFill.INSERT)
    private String createdFullName;

    /**
     * 最后更新人姓名
     */
    @TableField(value = "LAST_UPDATED_FULL_NAME", fill = FieldFill.UPDATE)
    private String lastUpdatedFullName;

    /**
     * 租户ID
     */
    @TableField("TENANT_ID")
    private String tenantId;

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


}
