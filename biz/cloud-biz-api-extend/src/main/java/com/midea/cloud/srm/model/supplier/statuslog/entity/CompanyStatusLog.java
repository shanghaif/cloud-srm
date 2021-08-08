package com.midea.cloud.srm.model.supplier.statuslog.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
*  <pre>
 *  企业状态历史表 模型
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-13 17:43:49
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sup_company_status_log")
public class CompanyStatusLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("STATUS_LOG_ID")
    private Long statusLogId;

    /**
     * 公司ID
     */
    @TableField("COMPANY_ID")
    private Long companyId;

    /**
     * 操作人ID
     */
    @TableField("OPERATOR_ID")
    private Long operatorId;

    /**
     * 操作人名字
     */
    @TableField("OPERATOR_NAME")
    private String operatorName;

    /**
     * 操作人类型 供应商或者采购商
     */
    @TableField("OPERATOR_TYPE")
    private String operatorType;

    /**
     * 操作设置状态
     */
    @TableField("OPERATION_STATUS")
    private String operationStatus;

    /**
     * 处理类型
     */
    @TableField("OPERATION_TYPE")
    private String operationType;

    /**
     * 历史说明
     */
    @TableField("OPERATION_MEMO")
    private String operationMemo;

    /**
     * 处理时间
     */
    @TableField("OPERATION_DATE")
    private Date operationDate;

    @TableField("VERSION")
    private Long version;

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
     * 最后更新人
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
    private Integer tenantId;


}
