package com.midea.cloud.srm.supcooperate.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.dto.DeliveryNoteDetailDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.dto.DeliveryNoteDetailReceiveDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.dto.DeliveryNoteRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.dto.ReceiveRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.entry.DeliveryNoteDetail;

import java.util.List;

/**
 * <pre>
 *  送货单明细表 Mapper 接口
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
public interface DeliveryNoteDetailMapper  extends BaseMapper<DeliveryNoteDetail> {
    List<DeliveryNoteDetailDTO> findList(DeliveryNoteRequestDTO deliveryNoteRequestDTO);

    List<DeliveryNoteDetailReceiveDTO> findReceiveList(ReceiveRequestDTO receiveRequestDTO);

    Long findOrderDetailId(Long deliveryNoteDetailId);

    List<DeliveryNoteDetailDTO> findDeliveryNoteDetailList(DeliveryNoteRequestDTO deliveryNoteRequestDTO);

    List<DeliveryNoteDetailDTO> listInWarehouseReceipt(DeliveryNoteRequestDTO deliveryNoteRequestDTO);

    List<DeliveryNoteDetailDTO> listInReturnOrder(DeliveryNoteRequestDTO deliveryNoteRequestDTO);
}
