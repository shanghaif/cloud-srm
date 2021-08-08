package com.midea.cloud.srm.supcooperate.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.entry.AppointDeliveryNote;
import com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.entry.DeliveryAppoint;
import com.midea.cloud.srm.supcooperate.order.mapper.AppointDeliveryNoteMapper;
import com.midea.cloud.srm.supcooperate.order.service.IAppointDeliveryNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <pre>
 *  预约送货单明细表 服务实现类
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/28 17:09
 *  修改内容:
 * </pre>
 */
@Service
public class AppointDeliveryNoteServiceImpl extends ServiceImpl<AppointDeliveryNoteMapper, AppointDeliveryNote> implements IAppointDeliveryNoteService {

    @Autowired
    private AppointDeliveryNoteMapper appointDeliveryNoteMapper;

    @Override
    public List<AppointDeliveryNote> getByDeliveryAppointId(Long deliveryAppointId) {
        return appointDeliveryNoteMapper.getByDeliveryAppointId(deliveryAppointId);
    }
}
