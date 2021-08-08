package com.midea.cloud.srm.supcooperate.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.dto.DeliveryNoteDetailDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.DeliveryNoticeDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.DeliveryNoticeRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderDetailDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.DeliveryNotice;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface DeliveryNoticeMapper extends BaseMapper<DeliveryNotice> {
    List<DeliveryNoticeDTO> findList(DeliveryNoticeRequestDTO requestDTO);

    List<DeliveryNoticeDTO> listCreateDeliveryNoteDetail(List<Long> deliveryNoticeIds);

    BigDecimal getWarehouseReceiptQuantity(@Param("deliveryNoticeId") Long deliveryNoticeId);

    List<DeliveryNoticeDTO> list(DeliveryNoticeRequestDTO requestDTO);

    List<OrderDetailDTO> listInDeliveryNote(OrderRequestDTO orderRequestDTO);
}
