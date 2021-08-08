package com.midea.cloud.srm.model.logistics.po.order.entity;

import java.math.BigDecimal;
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
 *  物流采购订单费用项表 模型
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-12-05 18:52:09
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("scc_lgt_order_line_fee")
public class OrderLineFee extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId("ORDER_LINE_FEE_ID")
    private Long orderLineFeeId;

    /**
     * 采购订单头表ID
     */
    @TableField("ORDER_HEAD_ID")
    private Long orderHeadId;

    /**
     * 采购订单编号(订单编号)
     */
    @TableField("ORDER_HEAD_NUM")
    private String orderHeadNum;

    /**
     * 采购订单行表ID
     */
    @TableField("ORDER_LINE_ID")
    private Long orderLineId;

    /**
     * 订单行号
     */
    @TableField("ROW_NUM")
    private Integer rowNum;

    /**
     * LEG
     */
    @TableField("LEG")
    private String leg;

    /**
     * 费项
     */
    @TableField("EXPENSE_ITEM")
    private String expenseItem;

    /**
     * 计费方式
     */
    @TableField("CHARGE_METHOD")
    private String chargeMethod;

    /**
     * 计费单位
     */
    @TableField("CHARGE_UNIT")
    private String chargeUnit;

    /**
     * 最大收费
     */
    @TableField("MAX_COST")
    private String maxCost;

    /**
     * 最小收费
     */
    @TableField("MIN_COST")
    private String minCost;

    /**
     * 费用
     */
    @TableField("EXPENSE")
    private BigDecimal expense;

    /**
     * 币制
     */
    @TableField("CURRENCY")
    private String currency;

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
     * 备用字段9
     */
    @TableField("ATTR9")
    private String attr9;

    /**
     * 备用字段10
     */
    @TableField("ATTR10")
    private String attr10;

    /**
     * 备用字段11
     */
    @TableField("ATTR11")
    private String attr11;

    /**
     * 备用字段12
     */
    @TableField("ATTR12")
    private String attr12;

    /**
     * 备用字段13
     */
    @TableField("ATTR13")
    private String attr13;

    /**
     * 备用字段14
     */
    @TableField("ATTR14")
    private String attr14;

    /**
     * 备用字段15
     */
    @TableField("ATTR15")
    private String attr15;

    /**
     * 来源系统（如：erp系统）
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
    private String tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;


}
