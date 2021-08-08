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
 *  维度字段配置表 模型
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-02 10:04:17
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sup_dim_field_config")
public class DimFieldConfig extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 属性ID
     */
    @TableId("FIELD_CONFIG_ID")
    private Long fieldConfigId;

    /**
     * 维度ID
     */
    @TableField("DIM_CONFIG_ID")
    private Long dimConfigId;

    /**
     * 租户ID
     */
    @TableField("TENANT_ID")
    private Long tenantId;

    /**
     * 字段ID
     */
    @TableField("FIELD_ID")
    private Long fieldId;

    /**
     * 字段别名
     */
    @TableField("FIELD_NICK_NAME")
    private String fieldNickName;

    /**
     * 是否显示(是Y,否N)
     */
    @TableField("IS_USE")
    private String isUse;

    /**
     * 是否校验(是Y,否N)
     */
    @TableField("IS_CHECK")
    private String isCheck;

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


}
