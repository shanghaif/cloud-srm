package com.midea.cloud.srm.supcooperate.order.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderSaveRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.OrderDetail;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.OrderPaymentProvision;
import com.midea.cloud.srm.supcooperate.order.service.IOrderPaymentProvisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <pre>
 *  订单付款条款controller
 * </pre>
 *
 * @author chenwj@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人: chenwj
 *  修改日期: 2020/8/13 15:04
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/order/orderPaymentProvision")
public class OrderPaymentProvisionController extends BaseController {
    @Autowired
    private IOrderPaymentProvisionService orderPaymentProvisionService;

    @GetMapping("/getOrderPaymentProvisionByOrderId")
    public List<OrderPaymentProvision> getByOrderId(@RequestParam("orderId") Long orderId){
        return orderPaymentProvisionService.getByOrderId(orderId);
    }

    /**
     * 根据orderId删除付款条款
     */
    @PostMapping("/deleteOrderPaymentProvisionByOrderId")
    public void deleteByOrderId(@RequestParam("orderId") Long orderId){
        Assert.notNull(orderId, LocaleHandler.getLocaleMsg("orderId为空"));
        orderPaymentProvisionService.remove(new QueryWrapper<OrderPaymentProvision>(new OrderPaymentProvision().setOrderId(orderId)));
    }

    @PostMapping("/save")
    public void save(@RequestBody OrderSaveRequestDTO param){
        orderPaymentProvisionService.savePaymentProvision(param);
    }

}
