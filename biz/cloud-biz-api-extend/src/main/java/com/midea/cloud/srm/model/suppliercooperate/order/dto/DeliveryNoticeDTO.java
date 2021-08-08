package com.midea.cloud.srm.model.suppliercooperate.order.dto;

import com.midea.cloud.srm.model.suppliercooperate.order.entry.DeliveryNotice;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <pre>
 *  送货通知单 数据返回传输对象
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/21 14:45
 *  修改内容:
 *          </pre>
 */

@Data
public class DeliveryNoticeDTO extends DeliveryNotice {
    private static final long serialVersionUID = 1L;

    // 订单 >>>>>

    /**
     * 订单号
     */
    private String orderNumber;

    /**
     * 供应商ID
     */
    private Long vendorId;

    /**
     * 供应商编号
     */
    private String vendorCode;

    /**
     * 供应商名称
     */
    private String vendorName;

    /**
     * 采购组织ID
     */
    private Long organizationId;

    /**
     * 组织全路径虚拟ID
     */
    private String fullPathId;

    /**
     * 组织编码
     */
    private String organizationCode;

    /**
     * 组织名称
     */
    private String organizationName;

    // 订单 <<<<<

     // 订单明细 >>>>>

    /**
     * 订单明细订单数量
     */
    private BigDecimal orderNum;

    /**
     * 剩余数量
     */
    private BigDecimal surplusSum;


    /**
     * 收货数量
     */
    private BigDecimal orderReceiveSum;

    /**
     * 物料ID
     */
    private Long materialId;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 物料名称
     */
    private String materialName;

    /**
     * 需求日期
     */
    private Date requirementDate;

    /**
     * 单位
     */
    private String unit;

    /**
     * 收货工厂
     */
    private String receivedFactory;

    /**
     * 库存地点
     */
    private String inventoryPlace;

    // 订单明细 >>>>>

    // 送货单 >>>>>
    /**
     * 送货单号
     */
    private String deliveryNumber;
    // 送货单 <<<<<

    // 送货单明细 >>>>>
    /**
     * 行号
     */
    private Integer deliveryLineNum;

    // 送货单明细 <<<<<
    /**
     * 业务实体id
     */
    private Long ceeaOrgId;

    /**
     * 业务实体名称
     */
    private String ceeaOrgName;

    /**
     * 业务实体编码
     */
    private String ceeaOrgCode;

    /**
     * 收货地址
     */
    private String ceeaReceiveAddress;

    /**
     * 收单地址
     */
    private String ceeaReceiveOrderAddress;


    /**
     * 分类id
     */
    private Long categoryId;

    /**
     * 分类编码
     */
    private String categoryCode;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 收货地点
     */
    private String receiptPlace;

    /**
     * 送货通知引用数量
     */
    private BigDecimal deliveryNoticeQuantity;

    /**
     * 本次可通知送货数量
     */
    private BigDecimal surplusDeliveryQuantity;

}
