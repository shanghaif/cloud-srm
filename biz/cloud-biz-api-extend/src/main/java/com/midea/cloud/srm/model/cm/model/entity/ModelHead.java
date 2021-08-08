package com.midea.cloud.srm.model.cm.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.Date;

/**
*  <pre>
 *  合同模板头表 模型
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-06-24 09:24:06
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_contract_model_head")
public class ModelHead extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID,合同模板头ID
     */
    @TableId("MODEL_HEAD_ID")
    private Long modelHeadId;

    /**
     * 模板编码(唯一)
     */
    @TableField("MODEL_CODE")
    private String modelCode;

    /**
     * 模板名称
     */
    @TableField("MODEL_NAME")
    private String modelName;

    /**
     * 模板类型
     */
    @TableField("MODEL_TYPE")
    private String modelType;

    /**
     * 生效日期
     */
    @TableField("START_DATE")
    private LocalDate startDate;

    /**
     * 失效日期
     */
    @TableField(value = "END_DATE")
    private LocalDate endDate;

    /**
     * 内容
     */
    @TableField("CONTENT")
    private String content;

    /**
     * 状态,DRAFT-拟定,VALID-生效,INVALID-失效
     */
    @TableField("STATUS")
    private String status;

    /**
     * 是否可修改全部内容(Y-是,N-否)
     */
    @TableField("ENABLE")
    private String enable;

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

    /**
     * 管控方式(MANAGEMENT_CONTROL_MODEL)
     * {@link com.midea.cloud.common.enums.contract.ManagementControlModel}
     */
    @TableField("CEEA_CONTROL_METHOD")
    private String ceeaControlMethod;

}
