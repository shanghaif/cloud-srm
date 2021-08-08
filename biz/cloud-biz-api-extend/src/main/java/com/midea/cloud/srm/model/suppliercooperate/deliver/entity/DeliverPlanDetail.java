package com.midea.cloud.srm.model.suppliercooperate.deliver.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.time.LocalDate;
import com.midea.cloud.srm.model.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
*  <pre>
 *  到货计划详情表 模型
 * </pre>
*
* @author yourname@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-27 14:42:31
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_sc_deliver_plan_detail")
public class DeliverPlanDetail extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 到货计划表明细ID
     */
    @TableId("DELIVER_PLAN_DETAIL_ID")
    private Long deliverPlanDetailId;

    /**
     * 到货计划表ID
     */
    @TableField("DELIVER_PLAN_ID")
    private Long deliverPlanId;

    /**
     * 到货计划号
     */
    @TableField("DELIVER_PLAN_NUM")
    private String deliverPlanNum;

    /**
     * 计划到货日期
     */
    @TableField("SCH_MONTHLY_DATE")
    private LocalDate schMonthlyDate;

    /**
     * 需求数量
     */
    @TableField("REQUIREMENT_QUANTITY")
    private BigDecimal requirementQuantity;

    /**
     * 承诺供应数量
     */
    @TableField("QUANTITY_PROMISED")
    private BigDecimal quantityPromised;
    /**
     * 送货数量(本次送货数量)
     */
    @TableField("DELIVERY_QUANTITY")
    private BigDecimal deliveryQuantity;

    /**
     * 采购订单ID
     */
    @TableField("ORDER_ID")
    private Long orderId;

    /**
     * 已匹配订单数量
     */
    @TableField("ORDER_QUANTITY_MATCHED")
    private BigDecimal orderQuantityMatched;

    /**
     * 是否锁定（1是，2否）
     */
    @TableField("DELIVER_PLAN_LOCK")
    private String deliverPlanLock;

    /**
     * 行状态
     */
    @TableField("DELIVER_PLAN_STATUS")
    private String deliverPlanStatus;

    /**
     * 订单交货明细ID
     */
    @TableField("DELIVERY_ORDER_DETAILS_ID")
    private Long deliveryOrderDetailsId;

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
     * 租户
     */
    @TableField("TENANT_ID")
    private Long tenantId;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;

    /**
     * MPR送货明细号
     */
    @TableField(value = "NOTIFY_NUMBER", exist = false)
    private  String notifyNumber;
    /**
     * 送货数量
     */
    @TableField(exist = false)
    private  String quantity;
    /**
     * 送货通知类型(DN送货通知，DP送货预示)
     */
    @TableField(exist = false)
    private  String notifyType;

}
