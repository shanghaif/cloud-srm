package com.midea.cloud.srm.model.base.organization.entity;

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
 *  地址接口表 模型
 * </pre>
*
* @author wuwl18@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-17 19:44:45
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_base_location")
public class Location extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId("ID")
    private Long id;

    /**
     * 地点Id
     */
    @TableField("LOCATION_ID")
    private Long locationId;

    /**
     * 地点名称
     */
    @TableField("LOCATION_NAME")
    private String locationName;

    /**
     * 地点说明
     */
    @TableField("LOCATION_DESCR")
    private String locationDescr;

    /**
     * 法定地点
     */
    @TableField("LEGAL_FLAG")
    private String legalFlag;

    /**
     * 地址
     */
    @TableField("ADDR_DETAIL")
    private String addrDetail;

    /**
     * 时区
     */
    @TableField("TIME_ZONE")
    private String timeZone;

    /**
     * 联系人
     */
    @TableField("CONTRACTOR")
    private String contractor;

    /**
     * 收货地点Id
     */
    @TableField("SHIP_TO_LOCATION_ID")
    private Long shipToLocationId;

    /**
     * 收货地点名称
     */
    @TableField("SHIP_TO_LOCATION_NAME")
    private String shipToLocationName;

    /**
     * 收货地点
     */
    @TableField("SHIP_TO_FLAG")
    private String shipToFlag;

    /**
     * 接收地点
     */
    @TableField("RECEIVE_FLAG")
    private String receiveFlag;

    /**
     * 办公地点
     */
    @TableField("OFFICIAL_FLAG")
    private String officialFlag;

    /**
     * 收单地点
     */
    @TableField("BILL_FALG")
    private String billFalg;

    /**
     * 内部地点
     */
    @TableField("INTERNAL_FLAG")
    private String internalFlag;

    /**
     * 库存组织Id
     */
    @TableField("ORGANIZATION_ID")
    private Long organizationId;

    /**
     * 库存组织代码
     */
    @TableField("ORGANIZATION_CODE")
    private String organizationCode;

    /**
     * 接口状态(NEW-数据在接口表里,没同步到业务表,UPDATE-数据更新到接口表里,SUCCESS-同步到业务表,ERROR-数据同步到业务表出错)
     */
    @TableField("ITF_STATUS")
    private String itfStatus;

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
     * 备用字段5
     */
    @TableField("ATTR5")
    private String attr5;

    /**
     * 备用字段6
     */
    @TableField("ATTR6")
    private String attr6;

    /**
     * 备用字段7
     */
    @TableField("ATTR7")
    private String attr7;

    /**
     * 备用字段8
     */
    @TableField("ATTR8")
    private String attr8;

    /**
     * 完成标识
     */
    @TableField("FINISHED_FLAG")
    private String finishedFlag;

    /**
     * 出错标识
     */
    @TableField("ERROR_FLAG")
    private String errorFlag;

    /**
     * 消息文本
     */
    @TableField("MESSAGE_TEXT")
    private String messageText;

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
     * 最后更新
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
