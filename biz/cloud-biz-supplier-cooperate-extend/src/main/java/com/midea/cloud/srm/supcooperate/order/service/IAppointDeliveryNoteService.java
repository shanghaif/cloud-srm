package com.midea.cloud.srm.supcooperate.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.entry.AppointDeliveryNote;
import com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.entry.DeliveryAppoint;

import java.util.List;

/**
 * <pre>
 *  预约送货单明细表 服务类
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/28 17:07
 *  修改内容:
 * </pre>
 */
public interface IAppointDeliveryNoteService extends IService<AppointDeliveryNote>{
    List<AppointDeliveryNote> getByDeliveryAppointId(Long deliveryAppointId);
}
