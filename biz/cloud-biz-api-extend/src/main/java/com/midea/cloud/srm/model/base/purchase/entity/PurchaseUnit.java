package com.midea.cloud.srm.model.base.purchase.entity;

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
 *  单位设置 模型
 * </pre>
 *
 * @author chensl26@meicloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-02-28 11:40:17
 *  修改内容:
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_base_purchase_unit")
public class PurchaseUnit extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 表ID，主键，供其他表做外键
     */
    @TableId("UNIT_ID")
    private Long unitId;

    /**
     * 单位编码
     */
    @TableField("UNIT_CODE")
    private String unitCode;

    /**
     * 单位名称
     */
    @TableField("UNIT_NAME")
    private String unitName;

    /**
     * 语言
     */
    @TableField("LANGUAGE")
    private String language;


    /**
     * Y:有效/N:无效
     */
    @TableField("ENABLED")
    private String enabled;

    /**
     * 失效日期
     */
    @TableField("DISABLE_DATE")
    private Date disableDate;

    /**
     * 是否默认选择,Y:是,N:否
     */
    @TableField("DEFAULT_SHOW")
    private String defaultShow;

    /**
     * 序号
     */
    @TableField("UNIT_SORT")
    private Integer unitSort;

    /**
     * 来源系统
     */
    @TableField("SOURCE_SYSTEM")
    private String sourceSystem;

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
     * 版本号
     */
    @TableField("VERSION")
    private Long version;

    /**
     * 租户ID
     */
    @TableField("TENANT_ID")
    private Long tenantId;


}
