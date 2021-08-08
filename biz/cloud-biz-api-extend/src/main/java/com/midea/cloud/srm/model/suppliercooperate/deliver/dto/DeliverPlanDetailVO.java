package com.midea.cloud.srm.model.suppliercooperate.deliver.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.midea.cloud.srm.model.suppliercooperate.deliver.entity.DeliverPlan;
import com.midea.cloud.srm.model.suppliercooperate.deliver.entity.DeliverPlanDetail;
import com.midea.cloud.srm.model.suppliercooperate.deliver.entity.OrderAppoint;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderDetailDTO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@Accessors(chain = true)
public class DeliverPlanDetailVO extends DeliverPlanDetail {
    /**
     * 到货计划月度
     */
    @TableField("MONTHLY_SCH_DATE")
    private String monthlySchDate;

    /**
     * 计划状态
     */
    @TableField("DELIVER_PLAN_STATUS")
    private String deliverPlanStatus;

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
     * 交货地点
     */
    @TableField("DELIVERY_ADDRESS")
    private String deliveryAddress;

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
     * 主键ID
     */
    @TableField("ORDER_DETAIL_ID")
    private Long orderDetailId;

    /**
     * 订单ID
     */
    @TableField("ORDER_ID")
    private Long orderId;
    /**
     * 采购订单号
     */
    @TableField("ORDER_NUMBER")
    private String orderNumber;
    /**
     * 订单日期
     */
    @TableField("CEEA_PURCHASE_ORDER_DATE")
    private Date ceeaPurchaseOrderDate;
    /**
     * 采购员名称
     */
    @TableField("BUYER_NAME")
    private String buyerName;
    /**
     * 订单数量
     */
    @TableField("ORDER_NUM")
    private BigDecimal orderNum;
    /**
     * 需求日期
     */
    @TableField("REQUIREMENT_DATE")
    private LocalDate requirementDate;
    /**
     * 计划到货日期
     */
    @TableField("PLAN_RECEIVE_DATE")
    private LocalDate planReceiveDate;
    /**
     * 订单行号
     */
    @TableField("LINE_NUM")
    private Integer lineNum;
    /**
     * 单位
     */
    @TableField("UNIT")
    private String unit;
    /**
     * 剩余未送货数量
     */
    private String numberRemaining;
}