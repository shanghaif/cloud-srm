package com.midea.cloud.srm.supcooperate.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.entry.AppointDeliveryNote;
import com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.entry.DeliveryAppoint;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <pre>
 *  送货预约送货单明细表 Mapper 接口
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
public interface AppointDeliveryNoteMapper extends BaseMapper<AppointDeliveryNote> {
    List<AppointDeliveryNote> getByDeliveryAppointId(@Param("deliveryAppointId") Long deliveryAppointId);
}
