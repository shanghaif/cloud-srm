package com.midea.cloud.srm.supcooperate.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.dto.DeliveryNoteDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.dto.DeliveryNoteRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.entry.DeliveryNote;

import java.util.List;

/**
 * <pre>
 *  送货单表 Mapper 接口
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
public interface DeliveryNoteMapper extends BaseMapper<DeliveryNote> {

    /**
     * 查询送货单列表
     * @param deliveryNoteRequestDTO
     * @return
     */
    List<DeliveryNoteDTO> findList(DeliveryNoteRequestDTO deliveryNoteRequestDTO);

    Integer countCreate(DeliveryNoteRequestDTO deliveryNoteRequestDTO);

    List<DeliveryNoteDTO> findDeliveryNoteList(DeliveryNoteRequestDTO deliveryNoteRequestDTO);

    List<DeliveryNoteDTO> list(DeliveryNoteRequestDTO deliveryNoteRequestDTO);

    List<DeliveryNoteDTO> listInDeliveryAppoint(DeliveryNoteRequestDTO deliveryNoteRequestDTO);
}
