package com.midea.cloud.srm.po.logisticsOrder.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.logistics.po.order.dto.LogisticsOrderDTO;
import com.midea.cloud.srm.model.logistics.po.order.entity.OrderHead;
import com.midea.cloud.srm.model.logistics.po.order.vo.LogisticsOrderVO;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.Order;

import java.util.List;

/**
 *  <pre>
 *  物流采购订单头表 服务类
 * </pre>
 *
 * @author xiexh12@meicloud.com
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-12-05 18:50:05
 *  修改内容:
 * </pre>
 */
public interface IOrderHeadService extends IService<OrderHead> {

    Long addOrder(LogisticsOrderDTO orderDTO);

    Long modifyOrder(LogisticsOrderDTO orderDTO);

    void deleteByHeadId(Long orderHeadId);

    PageInfo<OrderHead> listPage(OrderHead orderHead);

    LogisticsOrderVO getByHeadId(Long orderHeadId);

    Long submitApproval(LogisticsOrderDTO orderDTO);

    void updateApproved(Long orderHeadId, String auditStatus);

    void submitVendorConfirm(Long orderHeadId);

    void refuse(OrderHead orderHead);

    Long temporarySave(LogisticsOrderDTO orderDTO);

    void syncTms(Long orderHeadId);

    void syncTmsLongi(Long orderHeadId);

    List<OrderHead> bidingToOrders(Long bidingId);

    /**
     * 批量作废订单
     * @param ids
     */
    void batchCancel(List<Long> ids);

    /**
     * 批量删除订单
     * @param ids
     */
    void batchDelete(List<Long> ids);
}
