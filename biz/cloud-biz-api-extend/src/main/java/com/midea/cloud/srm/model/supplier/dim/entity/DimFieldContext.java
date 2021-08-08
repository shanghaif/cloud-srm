package com.midea.cloud.srm.model.supplier.dim.entity;

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
 *  维度字段内容表 模型
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-12 10:58:28
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sup_dim_field_context")
public class DimFieldContext extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 属性内容ID
     */
    @TableId("FIELD_CONTEXT_ID")
    private Long fieldContextId;

    /**
     * 字段ID
     */
    @TableField("FIELD_CONFIG_ID")
    private Long fieldConfigId;

    /**
     * 租户ID
     */
    @TableField("TENANT_ID")
    private Long tenantId;

    /**
     * 供应商ID
     */
    @TableField("COMPANY_ID")
    private Long companyId;

    /**
     * 字段所属的维度
     */
    @TableField("CONTEXT_ORDER_ID")
    private String contextOrderId;

    /**
     * 属性
     */
    @TableField("FIELD_NAME")
    private String fieldName;

    /**
     * 属性编码
     */
    @TableField("FIELD_CODE")
    private String fieldCode;

    /**
     * 属性值
     */
    @TableField("FIELD_VALUE")
    private String fieldValue;

    /**
     * 版本号
     */
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
     * 字段类型
     */
    @TableField("FIELD_TYPE_CODE")
    private String fieldTypeCode;

    /**
     * 字段类型名称
     */
    @TableField("FIELD_TYPE_NAME")
    private String fieldTypeName;

    /**
     * 长度
     */
    @TableField("FILE_SIZE")
    private String fileSize;

    /**
     * 文件类型
     */
    @TableField("FILE_TYPE")
    private String fileType;

    /**
     * 字段长度
     */
    @TableField("FIELD_LENGTH")
    private Integer fieldLength;


}
