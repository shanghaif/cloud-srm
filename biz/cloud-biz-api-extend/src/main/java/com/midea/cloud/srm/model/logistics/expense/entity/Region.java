package com.midea.cloud.srm.model.logistics.expense.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
*  <pre>
 *  行政区域维护表 模型
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-11-26 12:03:51
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_lgt_region")
public class Region extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 行政区域ID
     */
    @TableId("REGION_ID")
    private Long regionId;

    /**
     * 行政区域编码
     */
    @TableField("REGION_CODE")
    private String regionCode;

    /**
     * 行政区域名称
     */
    @TableField("REGION_NAME")
    private String regionName;

    /**
     * 行政区域名称(英文)
     */
    @TableField("REGION_NAME_EN")
    private String regionNameEn;

    /**
     * 区域层级编码
     */
    @TableField("REGION_LEVEL_CODE")
    private String regionLevelCode;

    /**
     * 上级区域ID
     */
    @TableField("PARENT_REGION_ID")
    private Long parentRegionId;

    /**
     * 上级区域编码
     */
    @TableField("PARENT_REGION_CODE")
    private String parentRegionCode;

    /**
     * 上级区域名称
     */
    @TableField("PARENT_REGION_NAME")
    private String parentRegionName;

    /**
     * 区域路径(id全路径)
     */
    @TableField("REGION_FULL_ID")
    private String regionFullId;

    /**
     * 区域路径(名称全路径)
     */
    @TableField("REGION_FULL_NAME")
    private String regionFullName;

    /**
     * 商城编码
     */
    @TableField("SHOPPING_CODE")
    private String shoppingCode;

    /**
     * 商城名称
     */
    @TableField("SHOPPING_NAME")
    private String shoppingName;

    /**
     * 是否基地
     */
    @TableField("IF_BASE")
    private String ifBase;

    /**
     * 是否按决标车型决标
     */
    @TableField("IF_BID_BY_VEHICLE")
    private String ifBidByVehicle;

    /**
     * 状态(字典编码LOGISTICS_STATUS)
     */
    @TableField("STATUS")
    private String status;

    /**
     * 备用字段1
     */
    @TableField("ATTR1")
    private String attr1;

    /**
     * 备用字段2
     */
    @TableField("ATTR2")
    private String attr2;

    /**
     * 备用字段3
     */
    @TableField("ATTR3")
    private String attr3;

    /**
     * 备用字段4
     */
    @TableField("ATTR4")
    private String attr4;

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
     * 更新人
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
    private Long tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;


}
