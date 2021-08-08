package com.midea.cloud.srm.model.logistics.po.order.dto;

import com.midea.cloud.srm.model.logistics.po.order.entity.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @Author vent
 * @Description
 **/
@Data
@Accessors(chain = true)
public class LogisticsOrderDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 采购订单头
     */
    private OrderHead orderHead;

    /**
     * 订单行
     */
    private List<OrderLine> orderLineList;

    /**
     * 订单合同行
     */
    private List<OrderLineContract> orderLineContractList;

    /**
     * 订单船期行
     */
    private List<OrderLineShip> orderLineShipList;

    /**
     * 订单附件行
     */
    private List<OrderFile> orderFileList;




}
