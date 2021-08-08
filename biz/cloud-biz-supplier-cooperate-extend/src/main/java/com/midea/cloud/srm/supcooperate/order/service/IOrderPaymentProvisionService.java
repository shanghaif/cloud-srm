package com.midea.cloud.srm.supcooperate.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderSaveRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.OrderPaymentProvision;

import java.util.List;

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
 *  修改日期: 2020/8/13 15:04
 *  修改内容:
 * </pre>
 */
public interface IOrderPaymentProvisionService extends IService<OrderPaymentProvision> {
    List<OrderPaymentProvision> getByOrderId(Long orderId);

    void savePaymentProvision(OrderSaveRequestDTO param);
}
