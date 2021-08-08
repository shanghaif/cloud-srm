package com.midea.cloud.srm.model.api.interfacelog.entity;

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
 *  接口日志表 模型
 * </pre>
*
* @author kuangzm@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-28 10:58:43
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_api_interface_log")
public class InterfaceLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 日志ID
     */
    @TableId("LOG_ID")
    private Long logId;

    /**
     * 接口名称
     */
    @TableField("SERVICE_NAME")
    private String serviceName;

    /**
     * 接口类型(HTTP,WEBSERVICE)
     */
    @TableField("SERVICE_TYPE")
    private String serviceType;

    /**
     * 传输类型(RECEIVE:接收，SEND:发送)
     */
    @TableField("TYPE")
    private String type;

    /**
     * 状态(SUCCESS:成功，FAIL:失败)
     */
    @TableField("STATUS")
    private String status;

    /**
     * 业务单据ID
     */
    @TableField("BILL_ID")
    private String billId;

    /**
     * 单据类型
     */
    @TableField("BILL_TYPE")
    private String billType;

    /**
     * 推送次数
     */
    @TableField("DEAL_TIME")
    private Long dealTime;

    /**
     * 完成时间
     */
    @TableField("FINISH_DATE")
    private Date finishDate;
    
    /**
     * 目标系统
     */
    @TableField("TARGET_SYS")
    private String targetSys;
    
    /**
     * 访问地址
     */
    @TableField("URL")
    private String url;

    /**
     * 最新行ID
     */
    @TableField("LINE_LOG_ID")
    private Long lineLogId;

    /**
     * 传输内容
     */
    @TableField("SERVICE_INFO")
    private String serviceInfo;

    /**
     * 返回信息
     */
    @TableField("RETURN_INFO")
    private String returnInfo;

    /**
     * 报错信息
     */
    @TableField("ERROR_INFO")
    private String errorInfo;

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
    @TableField(value = "LAST_UPDATE_DATE", fill = FieldFill.UPDATE)
    private Date lastUpdateDate;

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
     * 最后更新人IP
     */
    @TableField(value = "LAST_UPDATED_BY_IP", fill = FieldFill.UPDATE)
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
