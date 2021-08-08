package com.midea.cloud.srm.model.log.biz.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author tanjl11@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/4 14:26
 *  修改内容:
 * </pre>
 */
@Data
@TableName("scc_log_business_operation")
public class BizOperateLogInfo extends BaseEntity {

    /**
     * 日志操作id
     */
    @TableId("BUSINESS_OPERATE_ID")
    @JSONField(serialize = false)
    private Long businnessOperateId;

    /**
     * 操作人
     */
    @TableField("NICKNAME")
    private String nickname;

    /**
     * 操作账号
     */
    @TableField("USERNAME")
    private String username;
    /**
     * 用户类型
     */
    @TableField("USER_TYPE")
    private String userType;
    /**
     * 单据id
     */
    @TableField("BUSINESS_ID")
    private String businessId;

    /**
     * 单据号
     */
    @TableField("BUSINESS_NO")
    private String businessNo;
    /**
     * 操作类型
     */
    @TableField("OPERATE_TYPE")
    private String operateType;
    /**
     * 操作说明
     */
    @TableField("OPERATE_INFO")
    private String operateInfo;
    /**
     * 操作人ip
     */
    @TableField("OPERATE_IP")
    private String operateIp;

    /**
     * 所属模块
     */
    @TableField("MODEL")
    private String model;
    /**
     * 对应前端的菜单
     */
    @TableField("TAB")
    private String businessTab;

    @TableField
    private LocalDateTime operateTime;
    /**
     * 创建人ID
     */
    @TableField(value = "CREATED_ID", fill = FieldFill.INSERT)
    @JSONField(serialize = false)
    private Long createdId;

    /**
     * 创建人
     */
    @TableField(value = "CREATED_BY", fill = FieldFill.INSERT)
    @JSONField(serialize = false)
    private String createdBy;

    /**
     * 创建时间
     */
    @TableField(value = "CREATION_DATE", fill = FieldFill.INSERT)
    @JSONField(serialize = false)
    private Date creationDate;

    /**
     * 创建人IP
     */
    @TableField(value = "CREATED_BY_IP", fill = FieldFill.INSERT)
    @JSONField(serialize = false)
    private String createdByIp;

    /**
     * 最后更新人ID
     */
    @TableField(value = "LAST_UPDATED_ID", fill = FieldFill.UPDATE)
    @JSONField(serialize = false)
    private Long lastUpdatedId;

    /**
     * 更新人
     */
    @TableField(value = "LAST_UPDATED_BY", fill = FieldFill.UPDATE)
    @JSONField(serialize = false)
    private String lastUpdatedBy;

    /**
     * 最后更新时间
     */
    @TableField(value = "LAST_UPDATED_DATE", fill = FieldFill.INSERT_UPDATE)
    @JSONField(serialize = false)
    private Date lastUpdateDate;

    /**
     * 最后更新人IP
     */
    @TableField(value = "LAST_UPDATED_BY_IP", fill = FieldFill.UPDATE)
    @JSONField(serialize = false)
    private String lastUpdatedByIp;

    /**
     * 租户ID
     */
    @TableField("TENANT_ID")
    @JSONField(serialize = false)
    private String tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    @JSONField(serialize = false)
    private Long version;
}
