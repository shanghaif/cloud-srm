package com.midea.cloud.srm.model.base.repush.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <pre>
 *  重推记录类
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-10-6 14:23
 *  修改内容:
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_base_repush")
public class Repush extends BaseEntity {


    /**
     * 主键ID
     */
    @TableId("REPUSH_ID")
    private Long repushId;
    /**
     * 标题
     */
    @TableField("TITLE")
    private String title;
    /**
     * 来源单号
     */
    @TableField("SOURCE_NUMBER")
    private String sourceNumber;
    /**
     * 重推回调接口逻辑
     */
    @TableField("REPUSH_CALLBACK")
    private String repushCallback;
    /**
     * 重推回调接口参数
     */
    @TableField("REPUSH_CALLBACK_PARAM")
    private String repushCallbackParam;
    /**
     * 重推服务类
     */
    @TableField("CALL_SERVICE_CLASS_NAME")
    private String callServiceClassName;
    /**
     * 重推方法
     */
    @TableField("CALL_SERVICE_METHOD")
    private String callServiceMethod;
    /**
     * 重推参数
     */
    @TableField("CALL_PARAMETER_JSON")
    private String callParameterJson;
    /**
     * 当前重推次数
     */
    @TableField("CURRENT_RETRY_COUNT")
    private Integer currentRetryCount;
    /**
     * 最大重推次数
     */
    @TableField("MAX_RETRY_COUNT")
    private Integer maxRetryCount;
    /**
     * 推送状态 SUCCESS 成功 FAIL 失败
     */
    @TableField("PUSH_STATUS")
    private RepushStatus pushStatus;
    /**
     * 是否需要重推,0:不需重推,1:需要重推,默认0
     */
    @TableField("IF_REPUSH")
    private int ifRepush;
    /**
     * 推送结果
     */
    @TableField("RESULT")
    private String result;
    /**
     * 推送异常信息
     */
    @TableField("ERROR_INFO")
    private String errorInfo;
    /**
     * 模块
     */
    @TableField("MODULE")
    private String module;

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

}
