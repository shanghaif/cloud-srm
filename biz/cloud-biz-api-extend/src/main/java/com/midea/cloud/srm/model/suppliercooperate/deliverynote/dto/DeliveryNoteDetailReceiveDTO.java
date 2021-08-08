package com.midea.cloud.srm.model.suppliercooperate.deliverynote.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.entry.DeliveryNoteDetail;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <pre>
 *  收货明细 数据返回传输对象
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/31 16:35
 *  修改内容:
 * </pre>
 */
@Data
public class DeliveryNoteDetailReceiveDTO extends DeliveryNoteDetail {

    private static final long serialVersionUID = 1L;
    //送货单属性 >>>>>

    /**
     * 送货单号
     */
    private String deliveryNumber;

    /**
     * 采购组织ID
     */
    private Long organizationId;

    /**
     * 组织全路径虚拟ID
     */
    private String fullPathId;

    /**
     * 组织编号
     */
    private String organizationCode;

    /**
     * 组织名称
     */
    private String organizationName;

    //送货单属性 <<<<<

    //旧送货单属性 >>>>>

    /**
     * 旧送货单号
     */
    private String oldDeliveryNumber;

    //旧送货单属性 <<<<<

    //订单属性 >>>>>

    /**
     * 订单号
     */
    private String orderNumber;

    //订单属性 <<<<<

    //订单明细属性 >>>>>

    /**
     * 订单数量
     */
    private BigDecimal orderNum;

    /**
     * 订单行号
     */
    private Integer orderLineNum;

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

    //订单明细属性 <<<<<

}
