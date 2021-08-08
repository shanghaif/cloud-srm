package com.midea.cloud.srm.model.base.ou.entity;

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
*  <pre>
 *  ou组详细信息表 模型
 * </pre>
*
* @author tanjl11@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-04 19:53:34
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_base_ou_detail")
public class BaseOuDetail extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * OU组详情ID
     */
    @TableId("OU_DETAIL_ID")
    private Long ouDetailId;

    /**
     * OU组ID
     */
    @TableField("OU_GROUP_ID")
    private Long ouGroupId;

    /**
     * 业务实体ID
     */
    @TableField("OU_ID")
    private Long ouId;

    /**
     * 业务实体编码
     */
    @TableField("OU_CODE")
    private String ouCode;

    /**
     * 业务实体名称
     */
    @TableField("OU_NAME")
    private String ouName;

    /**
     * 库存组织ID
     */
    @TableField("INV_ID")
    private Long invId;

    /**
     * 库存组织编码
     */
    @TableField("INV_CODE")
    private String invCode;

    /**
     * 库存组织名称
     */
    @TableField("INV_NAME")
    private String invName;

    /**
     * 事业部名称
     */
    @TableField("BU_NAME")
    private String buName;

    /**
     * 事业部id
     */
    @TableField("BU_ID")
    private String buId;

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
