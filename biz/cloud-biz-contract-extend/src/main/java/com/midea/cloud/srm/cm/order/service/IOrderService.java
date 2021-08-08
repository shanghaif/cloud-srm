package com.midea.cloud.srm.cm.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.cm.order.dto.OrderDto;
import com.midea.cloud.srm.model.cm.order.entity.Order;

/**
*  <pre>
 *  合同单据 服务类
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-07-06 09:37:25
 *  修改内容:
 * </pre>
*/
public interface IOrderService extends IService<Order> {
    /**
     * 新增
     * @param orderDto
     */
    Long add(OrderDto orderDto);

    /**
     * 查询合同
     * @param orderId
     * @return
     */
    OrderDto queryById(Long orderId);

    /**
     * 更新
     * @param orderDto
     */
    void update(OrderDto orderDto);
}
