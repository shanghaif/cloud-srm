package com.midea.cloud.srm.supcooperate.order.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.OrderAttach;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.OrderDetail;
import com.midea.cloud.srm.supcooperate.order.service.IOrderAttachService;
import com.midea.cloud.srm.supcooperate.order.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author chenwj92@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人: chenwj92
 *  修改日期: 2020/8/11 8:54
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/order/orderAttach")
public class OrderAttachController {
    @Autowired
    private IOrderAttachService orderAttachService;

    @GetMapping("/getOrderAttachById")
    public OrderAttach getOrderAttachById(@RequestParam("orderAttachId") Long orderAttachId){
        return orderAttachService.getById(orderAttachId);
    }

    @GetMapping("/getOrderAttachByOrderId")
    public List<OrderAttach> getOrderAttachByOrderId(@RequestParam("orderId") Long orderId){
        return orderAttachService.list(new QueryWrapper<OrderAttach>(new OrderAttach().setOrderId(orderId)));
    }

    /**
     * 根据订单id删除订单文件
     */
    @PostMapping("/deleteOrderAttachByOrderId")
    public void deleteByOrderId(Long orderId){
        Assert.notNull(orderId, LocaleHandler.getLocaleMsg("orderId为空"));
        orderAttachService.remove(new QueryWrapper<OrderAttach>(new OrderAttach().setOrderId(orderId)));
    }

    /**
     * 保存文件
     */
    @PostMapping("/saveOrderAttach")
    public void save(@RequestBody OrderAttach param){
        orderAttachService.save(param);
    }
}
