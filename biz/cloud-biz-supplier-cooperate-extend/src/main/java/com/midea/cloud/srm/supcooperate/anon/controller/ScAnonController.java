package com.midea.cloud.srm.supcooperate.anon.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderDetailDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.Order;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.OrderDetail;
import com.midea.cloud.srm.supcooperate.order.service.IOrderDetailService;
import com.midea.cloud.srm.supcooperate.order.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Wrapper;
import java.util.List;

/**
 * <pre>
 *  不需暴露外部网关的接口，服务内部调用接口(无需登录就可以对微服务模块间暴露)
 * </pre>
 *
 * @author tanjl11@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/20 9:55
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/sc-anon/internal")
public class ScAnonController {
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IOrderDetailService iOrderDetailService;

    @GetMapping("/getOrderById")
    public Order getOrderById(@RequestParam("orderId") Long orderId){
       return  orderService.getById(orderId);
    }

    @PostMapping("/updateById")
    public void updateById(@RequestBody Order order){
         orderService.updateById(order);
    }

    @PostMapping("/order/batchGetOrderIdsByOrderNumbers")
    public List<Order> batchGetOrderIdsByOrderNumbers(@RequestBody List<String> orderNumbers){
        QueryWrapper<Order> wrapper = new QueryWrapper<Order>();
        wrapper.in("ORDER_NUMBER",orderNumbers);
        List<Order> orderList = orderService.list(wrapper);
        return orderList;
    }

    /**
     * 分页查询订单明细
     * @param orderRequestDTO 订单数据请求传输对象
     * @return
     */
    @PostMapping("/OrderDetailList")
    public List<OrderDetailDTO> OrderDetailList(@RequestBody OrderRequestDTO orderRequestDTO) {
        if ("SERVICE".equals(orderRequestDTO.getAssetType())){
            return iOrderDetailService.OrderDetailListPageCopy(orderRequestDTO);
        }
        return iOrderDetailService.OrderDetailListPage1(orderRequestDTO);
    }

    @PostMapping("/orderDetail/listValidOrderDetail")
    public List<OrderDetail> listValidOrderDetail(@RequestBody List<OrderDetail> orderDetails ){
        return iOrderDetailService.listValidOrderDetail(orderDetails);
    }

}
