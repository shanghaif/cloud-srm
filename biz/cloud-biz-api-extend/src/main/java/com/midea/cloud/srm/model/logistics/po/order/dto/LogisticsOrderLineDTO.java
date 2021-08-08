package com.midea.cloud.srm.model.logistics.po.order.dto;

import com.midea.cloud.srm.model.logistics.po.order.entity.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author vent
 * @Description
 **/
@Data
@Accessors(chain = true)
public class LogisticsOrderLineDTO {
    private static final long serialVersionUID = 1L;

    private OrderLine orderLine;//采购订行
    private List<OrderLineFee> orderLineFees;//采购订单费用行


}
