package com.midea.cloud.srm.po.logisticsOrder.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.logistics.po.order.entity.OrderHead;
import com.midea.cloud.srm.model.logistics.po.order.entity.OrderLine;

import java.util.List;

/**
*  <pre>
 *  物流采购订单行表 服务类
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-12-05 18:51:00
 *  修改内容:
 * </pre>
*/
public interface IOrderLineService extends IService<OrderLine> {

    void addOrderLineBatch(OrderHead orderHead,List<OrderLine> orderLineList);

    void updateBatch(OrderHead orderHead, List<OrderLine> orderLineList);

    void  removeBatch(Long orderHeadId);

    List<OrderLine>listBatch(Long orderHeadId);

    void  deleteByLineId(Long lineId);
}
