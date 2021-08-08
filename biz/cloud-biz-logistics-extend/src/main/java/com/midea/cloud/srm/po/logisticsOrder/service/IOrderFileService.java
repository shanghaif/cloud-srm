package com.midea.cloud.srm.po.logisticsOrder.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.logistics.po.order.entity.OrderFile;
import com.midea.cloud.srm.model.logistics.po.order.entity.OrderHead;

import java.util.List;

/**
*  <pre>
 *  物流采购订单附件 服务类
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-12-05 18:52:41
 *  修改内容:
 * </pre>
*/
public interface IOrderFileService extends IService<OrderFile> {

    void addOrderFileBatch(OrderHead orderHead, List<OrderFile> orderFileList);

    void updateOrderAttachBatch(OrderHead orderHead, List<OrderFile> orderFileList);
}
