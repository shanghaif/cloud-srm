package com.midea.cloud.srm.po.logisticsOrder.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.logistics.po.order.entity.OrderLine;
import com.midea.cloud.srm.model.logistics.po.order.entity.OrderLineFee;

import java.util.List;

/**
*  <pre>
 *  物流采购订单费用项表 服务类
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-12-05 18:52:09
 *  修改内容:
 * </pre>
*/
public interface IOrderLineFeeService extends IService<OrderLineFee> {

    void addOrderLineFeeBatch(OrderLine orderLine, List<OrderLineFee> orderLineFeeList);

    void updateBatch(OrderLine orderLine, List<OrderLineFee> orderLineList);
}
