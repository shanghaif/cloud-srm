package com.midea.cloud.srm.model.logistics.bid.entity;

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
 *  物流招标需求明细模板表 模型
 * </pre>
*
* @author wangpr@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-01-06 09:08:34
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_lgt_bid_template")
public class LgtBidTemplate extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 物流招标需求明细模板ID
     */
    @TableId("BID_TEMPLATE_ID")
    private Long bidTemplateId;

    /**
     * 物流招标头ID
     */
    @TableField("BIDING_ID")
    private Long bidingId;

    /**
     * 顺序号
     */
    @TableField("ROW_NUM")
    private Integer rowNum;

    /**
     * 字段编码
     */
    @TableField("FIELD_CODE")
    private String fieldCode;

    /**
     * 字段名称
     */
    @TableField("FIELD_NAME")
    private String fieldName;

    /**
     * 申请可操作
     */
    @TableField("APPLY_OPERATE_FLAG")
    private String applyOperateFlag;

    /**
     * 申请必填
     */
    @TableField("APPLY_NOT_EMPTY_FLAG")
    private String applyNotEmptyFlag;

    /**
     * 采购可操作
     */
    @TableField("PURCHASE_OPERATE_FLAG")
    private String purchaseOperateFlag;

    /**
     * 采购必填
     */
    @TableField("PURCHASE_NOT_EMPTY_FLAG")
    private String purchaseNotEmptyFlag;

    /**
     * 供应商可操作
     */
    @TableField("VENDOR_OPERATE_FLAG")
    private String vendorOperateFlag;

    /**
     * 供应商必填
     */
    @TableField("VENDOR_NOT_EMPTY_FLAG")
    private String vendorNotEmptyFlag;

    /**
     * 备注
     */
    @TableField("COMMENTS")
    private String comments;

//    /**
//     * 备用字段1
//     */
//    @TableField("ATTR1")
//    private String attr1;
//
//    /**
//     * 备用字段2
//     */
//    @TableField("ATTR2")
//    private String attr2;
//
//    /**
//     * 备用字段3
//     */
//    @TableField("ATTR3")
//    private String attr3;
//
//    /**
//     * 备用字段4
//     */
//    @TableField("ATTR4")
//    private String attr4;
//
//    /**
//     * 备用字段5
//     */
//    @TableField("ATTR5")
//    private String attr5;
//
//    /**
//     * 备用字段6
//     */
//    @TableField("ATTR6")
//    private String attr6;
//
//    /**
//     * 备用字段7
//     */
//    @TableField("ATTR7")
//    private String attr7;
//
//    /**
//     * 备用字段8
//     */
//    @TableField("ATTR8")
//    private String attr8;
//
//    /**
//     * 备用字段9
//     */
//    @TableField("ATTR9")
//    private String attr9;
//
//    /**
//     * 备用字段10
//     */
//    @TableField("ATTR10")
//    private String attr10;
//
//    /**
//     * 备用字段12
//     */
//    @TableField("ATTR12")
//    private String attr12;
//
//    /**
//     * 备用字段13
//     */
//    @TableField("ATTR13")
//    private String attr13;
//
//    /**
//     * 备用字段14
//     */
//    @TableField("ATTR14")
//    private String attr14;
//
//    /**
//     * 备用字段11
//     */
//    @TableField("ATTR11")
//    private String attr11;
//
//    /**
//     * 备用字段15
//     */
//    @TableField("ATTR15")
//    private String attr15;
//
//    /**
//     * 备用字段16
//     */
//    @TableField("ATTR16")
//    private String attr16;
//
//    /**
//     * 备用字段17
//     */
//    @TableField("ATTR17")
//    private String attr17;
//
//    /**
//     * 备用字段18
//     */
//    @TableField("ATTR18")
//    private String attr18;
//
//    /**
//     * 备用字段19
//     */
//    @TableField("ATTR19")
//    private String attr19;
//
//    /**
//     * 备用字段20
//     */
//    @TableField("ATTR20")
//    private String attr20;

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

    /**
     * 申请可显示
     */
    @TableField("APPLY_VISIBLE_FLAG")
    private String applyVisibleFlag;

    /**
     * 采购可显示
     */
    @TableField("PURCHASE_VISIBLE_FLAG")
    private String purchaseVisibleFlag;

    /**
     * 供应商可显示
     */
    @TableField("VENDOR_VISIBLE_FLAG")
    private String vendorVisibleFlag;


}
