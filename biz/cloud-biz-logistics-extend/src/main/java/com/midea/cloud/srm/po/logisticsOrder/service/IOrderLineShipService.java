package com.midea.cloud.srm.po.logisticsOrder.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.logistics.po.order.entity.OrderHead;
import com.midea.cloud.srm.model.logistics.po.order.entity.OrderLineShip;

import java.util.List;

/**
*  <pre>
 *  物流采购订单行船期表 服务类
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-12-11 11:21:42
 *  修改内容:
 * </pre>
*/
public interface IOrderLineShipService extends IService<OrderLineShip> {

    /**
     * 批量新增订单船期行
     */
    void addOrderShipBatch(OrderHead orderHead, List<OrderLineShip> orderLineShipList);

    void updateOrderLineShipBatch(OrderHead orderHead, List<OrderLineShip> orderLineShipList);
}
