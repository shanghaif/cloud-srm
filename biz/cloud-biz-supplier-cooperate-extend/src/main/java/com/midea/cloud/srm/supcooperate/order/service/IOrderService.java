package com.midea.cloud.srm.supcooperate.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.base.work.dto.WorkCount;
import com.midea.cloud.srm.model.suppliercooperate.excel.ErrorCell;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.ExcelOrderRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderDetailDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderSaveRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.Order;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.OrderDetail;

import java.util.List;

/**
 * <pre>
 *  采购订单表 服务类
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/19 19:12
 *  修改内容:
 * </pre>
 */
public interface IOrderService extends IService<Order> {

    List<OrderDTO> listPage(OrderRequestDTO orderRequestDTO);

    List<OrderDTO> listPageUpdates(OrderRequestDTO orderRequestDTO);

    void saveOrUpdate(OrderSaveRequestDTO param);

    List<ErrorCell> saveBatchByExcel(List<ExcelOrderRequestDTO> list);

    void submitBatch(List<Long> ids);

    void comfirmBatch(List<Long> ids);

    void refuseBatch(List<Order> list);

    WorkCount countSubmit(OrderRequestDTO orderRequestDTO);

    void comfirm(Order order, List<OrderDetail> detailList);

    void delete(Long orderId);

    List<Order> listOrderByOrderNumberOrErpNumber(List<String> numbers);

    List<Order> listByOrderNumbers(List<String> orderNumbers);

    public List<OrderDetailDTO> listOrderDetailPage(OrderRequestDTO orderRequestDTO);


    public void batchAccept(List<Long> ids);

    public void batchRefuse(List<Long> ids,String refusedReason);

    /**
     * 订单审批
     * @param orderId
     */
    void approval(Long orderId);

    /**
     * 批量删除订单
     * @param ids
     */
    void batchDelete(List<Long> ids);

    /**
     * 供应商确认
     * @param ids
     */
    void supplierConfirm(List<Long> ids);

    /**
     * 供应商拒绝
     * @param orderDetail
     */
    void supplierReject(OrderDetail orderDetail);
}
