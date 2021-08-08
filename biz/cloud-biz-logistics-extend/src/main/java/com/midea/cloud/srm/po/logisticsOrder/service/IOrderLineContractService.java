package com.midea.cloud.srm.po.logisticsOrder.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.logistics.po.order.entity.OrderHead;
import com.midea.cloud.srm.model.logistics.po.order.entity.OrderLineContract;

import java.util.List;

/**
*  <pre>
 *  物流采购订单行合同表 服务类
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-12-05 18:51:30
 *  修改内容:
 * </pre>
*/
public interface IOrderLineContractService extends IService<OrderLineContract> {

    void addOrderContractBatch(OrderHead orderHead,List<OrderLineContract> orderLineContractList);

    void updateBatch(OrderHead orderHead, List<OrderLineContract> orderLineList);
}
