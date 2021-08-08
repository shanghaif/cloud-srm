package com.midea.cloud.srm.model.suppliercooperate.deliver.dto;

import com.midea.cloud.srm.model.common.BaseEntity;
import com.midea.cloud.srm.model.suppliercooperate.deliver.entity.DeliverPlanDetail;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderDetailDTO;
import lombok.Data;

import java.util.List;

@Data
public class OrderAppointDTO extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private DeliverPlanDetail deliverPlanDetail;
    private List<OrderDetailDTO> orderDetailDTOList;
}
