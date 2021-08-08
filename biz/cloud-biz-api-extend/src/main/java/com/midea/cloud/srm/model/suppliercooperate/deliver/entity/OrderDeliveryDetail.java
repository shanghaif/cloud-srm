package com.midea.cloud.srm.model.suppliercooperate.deliver.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
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
 *  到货计划明细表 模型
 * </pre>
*
* @author yourname@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-28 13:59:09
 *  修改内容:
 * </pre>
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ceea_sc_order_delivery_detail")
public class OrderDeliveryDetail extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 到货计划明细ID
     */
    @TableId(value = "ORDER_DELIVERY_DETAIL_ID", type = IdType.AUTO)
    private Long orderDeliveryDetailId;

    /**
     * 到货计划详情ID
     */
    @TableField("DELIVER_PLAN_DETAIL_ID")
    private Long deliverPlanDetailId;

    /**
     * 到货计划表ID
     */
    @TableField("DELIVER_PLAN_ID")
    private Long deliverPlanId;

    /**
     * 采购订单ID
     */
    @TableField("ORDER_ID")
    private Long orderId;
    /**
     * 采购订单明细ID
     */
    @TableId("ORDER_DETAIL_ID")
    private Long orderDetailId;
    /**
     * 订单号
     */
    @TableField("ORDER_NUMBER")
    private String orderNumber;

    /**
     * 订单日期
     */
    @TableField("ORDER_DATE")
    private LocalDate orderDate;

    /**
     * 业务实体ID
     */
    @TableField("ORG_ID")
    private Long orgId;

    /**
     * 业务实体编码
     */
    @TableField("ORG_CODE")
    private String orgCode;

    /**
     * 业务实体名称
     */
    @TableField("ORG_NAME")
    private String orgName;

    /**
     * 库存组织ID
     */
    @TableField("ORGANIZATION_ID")
    private Long organizationId;

    /**
     * 库存组织编码
     */
    @TableField("ORGANIZATION_CODE")
    private String organizationCode;

    /**
     * 库存组织名称
     */
    @TableField("ORGANIZATION_NAME")
    private String organizationName;

    /**
     * 供应商ID
     */
    @TableField("VENDOR_ID")
    private Long vendorId;

    /**
     * 供应商名称
     */
    @TableField("VENDOR_NAME")
    private String vendorName;

    /**
     * 供应商编码
     */
    @TableField("VENDOR_CODE")
    private String vendorCode;

    /**
     * 采购员ID
     */
    @TableField("BUYER_ID")
    private Long buyerId;

    /**
     * 采购员账号
     */
    @TableField("BUYER")
    private String buyer;

    /**
     * 采购员名称
     */
    @TableField("BUYER_NAME")
    private String buyerName;

    /**
     * 物料小类ID
     */
    @TableField("CATEGORY_ID")
    private Long categoryId;

    /**
     * 物料小类名称
     */
    @TableField("CATEGORY_NAME")
    private String categoryName;

    /**
     * 物料小类编码
     */
    @TableField("CATEGORY_CODE")
    private String categoryCode;

    /**
     * 物料ID
     */
    @TableField("MATERIAL_ID")
    private Long materialId;

    /**
     * 物料编码
     */
    @TableField("MATERIAL_CODE")
    private String materialCode;

    /**
     * 物料名称
     */
    @TableField("MATERIAL_NAME")
    private String materialName;

    /**
     * 订单数量
     */
    @TableField("ORDER_QUANTITY")
    private BigDecimal orderQuantity;

    /**
     * 需求时间
     */
    @TableField("REQUIREMENT_DATE")
    private Date requirementDate;

    /**
     * 计划交货数量
     */
    @TableField("PLAN_RECEIVE_NUM")
    private BigDecimal planReceiveNum;
    /**
     * 送货数量(本次送货数量)
     */
    @TableField("DELIVERY_QUANTITY")
    private BigDecimal deliveryQuantity;

    /**
     * 计划到货日期
     */
    @TableField("PLAN_RECEIVE_DATE")
    private LocalDate planReceiveDate;
    /**
     * 到货计划号
     */
    @TableField("DELIVER_PLAN_NUM")
    private String deliverPlanNum;

    /**
     * 员工工号（隆基新增）
     */
    @TableField("CEEA_EMP_NO")
    private String ceeaEmpNo;

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
     * 状态
     */
    @TableField("ORDER_DELIVERY_DETAIL_STATE")
    private String OrderDeliveryDetailState;

}
