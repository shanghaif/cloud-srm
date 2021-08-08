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
 *  维度字段表 模型
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-02-29 15:28:51
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sup_dim_field")
public class DimField extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 属性ID
     */
    @TableId("FIELD_ID")
    private Long fieldId;

    /**
     * 维度ID
     */
    @TableField("DIM_ID")
    private Long dimId;

    /**
     * 租户ID
     */
    @TableField("TENANT_ID")
    private Long tenantId;

    /**
     * 属性
     */
    @TableField("FIELD_NAME")
    private String fieldName;

    /**
     * 类型编码
     */
    @TableField("FIELD_TYPE_CODE")
    private String fieldTypeCode;

    /**
     * 类型名称
     */
    @TableField("FIELD_TYPE_NAME")
    private String fieldTypeName;

    /**
     * 属性编码
     */
    @TableField("FIELD_CODE")
    private String fieldCode;

    /**
     * 是否拓展字段(是Y,否N)
     */
    @TableField("IS_FIELD")
    private String isField;

    /**
     * 附件大小限制
     */
    @TableField("FILE_SIZE")
    private String fileSize;

    /**
     * 附件格式限制
     */
    @TableField("FILE_TYPE")
    private String fileType;

    /**
     * 长度限制
     */
    @TableField("FIELD_LENGTH")
    private Integer fieldLength;

    /**
     * 顺序
     */
    @TableField("FIELD_ORDER_NUM")
    private Integer fieldOrderNum;

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


}
