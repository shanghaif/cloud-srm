package com.midea.cloud.srm.supcooperate.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.dto.DeliveryAppointDetailDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.dto.DeliveryAppointRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.dto.DeliveryAppointSaveRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.dto.DeliveryAppointVO;
import com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.entry.AppointDeliveryNote;
import com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.entry.DeliveryAppoint;
import com.midea.cloud.srm.model.suppliercooperate.deliveryappoint.entry.DeliveryAppointVisitor;

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
public interface IDeliveryAppointService extends IService<DeliveryAppoint> {

    void saveOrUpdate(String opt,DeliveryAppoint deliveryAppoint,List<DeliveryAppointVisitor> deliveryAppointVisitors,List<AppointDeliveryNote> appointDeliveryNotes);

    List<DeliveryAppoint> listPage(DeliveryAppointRequestDTO deliveryAppointRequestDTO);

    DeliveryAppointVO getDeliveryAppointById(Long deliveryAppointId);

    void submitBatch(List<Long> ids);

    void confirmBatch(List<Long> ids);

    void refuseBatch(DeliveryAppointRequestDTO requestDTO);

    Long temporarySave(DeliveryAppointSaveRequestDTO requestDTO);

    Long submit(DeliveryAppointSaveRequestDTO requestDTO);

    void batchDelete(List<Long> ids);
}
