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
 *  维度表 模型
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-02-29 15:17:35
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_sup_dim")
public class Dim extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId("DIM_ID")
    private Long dimId;

    /**
     * 租户ID
     */
    @TableField("TENANT_ID")
    private Long tenantId;

    /**
     * 属性维度
     */
    @TableField("DIM_NAME")
    private String dimName;

    /**
     * 属性维度编码
     */
    @TableField("DIM_CODE")
    private String dimCode;

    /**
     * 是否启用(可Y,不可N)
     */
    @TableField("IS_USE")
    private String isUse;

    /**
     * 是否注册填写(可Y,不可N)
     */
    @TableField("IS_REGIST")
    private String isRegist;

    /**
     * 是否由供应商变更(可Y,不可N)
     */
    @TableField("IS_SUPPLY")
    private String isSupply;

    /**
     * 是否由采购商变更(可Y,不可N)
     */
    @TableField("IS_BUYER")
    private String isBuyer;

    /**
     * 是否由流程变更(可Y,不可N)
     */
    @TableField("IS_FLOW")
    private String isFlow;

    /**
     * 是否资质审查(可Y,不可N)
     */
    @TableField("IS_AUDIT")
    private String isAudit;

    /**
     * 是否已建表(有Y,没N)
     */
    @TableField("IS_TABLE")
    private String isTable;


    /**
     * 管控维度类型
     */
    @TableField("DIM_TYPE")
    private String dimType;

    /**
     * 管控维度类型名称
     */
    @TableField("DIM_TYPE_NAME")
    private String dimTypeName;

    /**
     * 顺序
     */
    @TableField("ORDER_NUM")
    private Integer orderNum;

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
