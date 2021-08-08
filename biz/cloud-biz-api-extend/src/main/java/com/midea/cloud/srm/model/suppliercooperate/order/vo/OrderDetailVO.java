package com.midea.cloud.srm.model.suppliercooperate.order.vo;

import com.midea.cloud.srm.model.suppliercooperate.order.entry.OrderDetail;
import lombok.Data;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author chenwj92@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人: chenwj92
 *  修改日期: 2021/2/2 10:34
 *  修改内容:
 * </pre>
 */
@Data
public class OrderDetailVO extends OrderDetail {
    private static final long serialVersionUID = 1L;

    /**
     * 供应商名称
     */
    private String vendorName;

    /**
     * 采购订单号
     */
    private String orderNumber;

    /**
     * 业务实体名称
     */
    private String ceeaOrgName;

    /**
     * 收货地址
     */
    private String ceeaReceiveAddress;

    /**
     * 收单地址
     */
    private String ceeaReceiveOrderAddress;
}
