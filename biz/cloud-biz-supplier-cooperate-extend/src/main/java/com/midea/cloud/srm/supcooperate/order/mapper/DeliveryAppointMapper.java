package com.midea.cloud.srm.supcooperate.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.dto.DeliveryAppointDetailDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.dto.DeliveryAppointRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.entry.DeliveryAppoint;

import java.util.List;
/**
 * <pre>
 *  送货预约单表 Mapper 接口
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/19 19:15
 *  修改内容:
 * </pre>
 */
public interface DeliveryAppointMapper extends BaseMapper<DeliveryAppoint> {

    List<DeliveryAppoint> findList(DeliveryAppointRequestDTO deliveryAppointRequestDTO);

    DeliveryAppointDetailDTO getDeliveryAppointById(DeliveryAppoint deliveryAppoint);
}