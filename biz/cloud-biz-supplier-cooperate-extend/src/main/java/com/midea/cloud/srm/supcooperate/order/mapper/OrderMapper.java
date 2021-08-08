package com.midea.cloud.srm.supcooperate.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderDetailDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.Order;

import java.util.List;

/**
 * <pre>
 *  采购订单表 Mapper 接口
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/3/19 19:15
 *  修改内容:
 * </pre>
 */
public interface OrderMapper extends BaseMapper<Order> {

    /**
     * 查询订单列表
     * @param orderRequestDTO
     * @return
     */
    public List<OrderDTO> findList(OrderRequestDTO orderRequestDTO);

    Integer countSubmit(OrderRequestDTO orderRequestDTO);

    List<OrderDTO> findListUpdates(OrderRequestDTO orderRequestDTO);
    
    /**
     * 查询订单明细列表
     * @param orderRequestDTO
     * @return
     */
    public List<OrderDetailDTO> findOrderDetailList(OrderRequestDTO orderRequestDTO);
    
}


