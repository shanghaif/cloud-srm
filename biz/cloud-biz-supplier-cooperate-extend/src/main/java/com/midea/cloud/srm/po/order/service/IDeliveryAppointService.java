package com.midea.cloud.srm.po.order.service;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.dto.DeliveryAppointRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.entry.DeliveryAppoint;

import java.util.List;

/**
 * <pre>
 *  送货预约表 服务类
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/28 11:56
 *  修改内容:
 * </pre>
 */
public interface IDeliveryAppointService{

    void confirmBatch(List<Long> ids);

    void refuseBatch(DeliveryAppointRequestDTO requestDTO);

    PageInfo<DeliveryAppoint> listPage(DeliveryAppointRequestDTO deliveryAppointRequestDTO);
}
