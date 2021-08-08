package com.midea.cloud.srm.model.suppliercooperate.deliverynote.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.entry.DeliveryNoteDetail;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <pre>
 *  送货单明细 数据返回传输对象
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/21 15:44
 *  修改内容:
 * </pre>
 */
@Data
public class DeliveryNoteDetailDTO extends DeliveryNoteDetail {
    private static final long serialVersionUID = 1L;
    // 入库单 >>>>>

    /**
     * 入库数量
     */
    private BigDecimal warehouseReceiptQuantity;

    // 入库数量 <<<<<

    // 送货单 >>>>>
    /**
     * 送货单号
     */
    private String deliveryNumber;
    /**
     * 送货日期
     */
    private Date deliveryDate;

    /**
     * 供应商id
     */
    private Long vendorId;

    /**
     * 供应商编码
     */
    private String vendorCode;
    /**
     * 供应商名称
     */
    private String vendorName;

    /**
     * 业务实体id
     */
    private Long orgId;

    /**
     * 业务实体名称
     */
    private String orgName;

    /**
     * 业务实体编码
     */
    private String orgCode;

    /**
     * 库存组织
     */
    private String organizationName;
    /**
     * 交货地点
     */
    private String ceeaDeliveryPlace;

    /**
     * 单据状态
     */
    private String deliveryNoteStatus;
    /**
     * 送货单备注
     */
    private String deliveryNoteComments;

    // 送货单 <<<<<
    //订单 >>>>>

    /**
     * 采购员名称
     */
    private String buyerName;

    /**
     * 采购订单号
     */
    private String orderNumber;

    /**
     * 采购订单行号
     */
    private Integer orderLineNum;

    //订单 <<<<<


    //订单明细 >>>>>

    /**
     * 订单数量
     */
    private BigDecimal orderNum;

    /**
     * 收货地点
     */
    private String receiptPlace;

    /**
     * 订单明细备注
     */
    private String orderDetailComments;

    /**
     * 物料编码
     */
    private String materialId;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 物料名称
     */
    private String materialName;

    /**
     * 规格型号
     */
    private String specification;

    /**
     * 采购分类id
     */
    private Long categoryId;

    /**
     * 采购分类全名
     */
    private String categoryName;

    /**
     * 采购分类编码
     */
    private String categoryCode;

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

    /**
     * JIT订单
     */
    private String jitOrder;

    /**
     * 订单累计收货量
     */
    private BigDecimal receiveSum;
    /**
     * 订单剩余未送货数量
     */
    private BigDecimal numberRemaining;

    /**
     * 需求日期
     */
    private Date requirementDate;
    /**
     * 承诺到货日期
     */
    private Date ceeaPromiseReceiveDate;

    /**
     * 到货计划号
     */
    @TableField("DELIVER_PLAN_NUM")
    private String deliverPlanNum;

    /**
     * 到货计划剩余未送货数量
     */
    private String planNumberRemaining;
    /**
     * 计划交货数量
     */
    @TableField("PLAN_RECEIVE_NUM")
    private BigDecimal planReceiveNum;

}
