package com.midea.cloud.srm.model.suppliercooperate.deliverynote.dto;

import com.midea.cloud.srm.model.common.BaseEntity;
import lombok.Data;

/**
 * <pre>
 *  收货明细 数据请求传输对象
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/31 16:23
 *  修改内容:
 * </pre>
 */
@Data
public class ReceiveRequestDTO extends BaseEntity {

    private static final long serialVersionUID = 1L;
    //送货单 >>>>>

    /**
     * 采购组织ID
     */
    private Long organizationId;

    /**
     * 组织全路径虚拟ID
     */
    private String fullPathId;

    /**
     * 送货单号
     */
    private String deliveryNumber;

    /**
     * 是否含旧送货单号
     */
    private Boolean containOld;

    //送货单 <<<<<

    //采购单 >>>>>

    /**
     * 订单号
     */
    private String orderNumber;

    //采购单 <<<<<

    //采购订单明细 >>>>>

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

    //采购订单明细 <<<<<

    //送货单明细 >>>>>

    /**
     * 开始收货日期
     */
    private String startReceivedDate;

    /**
     * 截止收货时间
     */
    private String endReceivedDate;

    //送货单明细 <<<<<


    /**
     * 供应商ID
     */
    private Long vendorId;

}
