package com.midea.cloud.srm.supcooperate.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.utils.AppUserUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderSaveRequestDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.Order;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.OrderPaymentProvision;
import com.midea.cloud.srm.supcooperate.order.mapper.OrderPaymentProvisionMapper;
import com.midea.cloud.srm.supcooperate.order.service.IOrderPaymentProvisionService;
import com.midea.cloud.srm.supcooperate.order.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.commons.util.IdUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import sun.rmi.server.LoaderHandler;

import java.util.ArrayList;
import java.util.Date;
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
 *  修改日期: 2020/8/13 15:05
 *  修改内容:
 * </pre>
 */
@Service
public class OrderPaymentProvisionServiceImpl extends ServiceImpl<OrderPaymentProvisionMapper, OrderPaymentProvision> implements IOrderPaymentProvisionService {

    @Autowired
    private OrderPaymentProvisionMapper orderPaymentProvisionMapper;

    @Autowired
    private IOrderService orderService;

    @Override
    public List<OrderPaymentProvision> getByOrderId(Long orderId) {
        QueryWrapper<OrderPaymentProvision> wrapper = new QueryWrapper<>();
        wrapper.eq("ORDER_ID",orderId);
        wrapper.orderByAsc("PAYMENT_PERIODS_NUMBER");
        return this.list(wrapper);
    }

    @Override
    @Transactional
    public void savePaymentProvision(OrderSaveRequestDTO param) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Order order = orderService.getById(param.getOrder().getOrderId());
        Assert.notNull(order, LocaleHandler.getLocaleMsg("查询不到订单"));
        this.remove(new QueryWrapper<OrderPaymentProvision>(new OrderPaymentProvision().setOrderId(order.getOrderId())));
        checkPaymentProvisionAdd(param.getPaymentProvisionList());
        List<OrderPaymentProvision> paymentProvisionList = new ArrayList<>();
        param.getPaymentProvisionList().stream().forEach(item -> {
            paymentProvisionList.add(
                    item.setOrderId(order.getOrderId())
                            .setOrderPaymentProvisionId(IdGenrator.generate())
                            .setCreationDate(new Date())
                            .setCreatedBy(loginAppUser.getNickname())
                            .setCreatedId(loginAppUser.getUserId())
            );
        });
        this.saveBatch(paymentProvisionList);
    }

    public void checkPaymentProvisionAdd(List<OrderPaymentProvision> paymentProvisionList){
        paymentProvisionList.stream().forEach(item -> {
            Assert.hasText(item.getPaymentStage(),LocaleHandler.getLocaleMsg("付款阶段不可为空"));
            Assert.hasText(item.getPaymentPeriod(),LocaleHandler.getLocaleMsg("付款账期不可为空"));
            Assert.hasText(item.getPaymentTerm(),LocaleHandler.getLocaleMsg("付款条件不可为空"));
            Assert.hasText(item.getPaymentWay(),LocaleHandler.getLocaleMsg("付款方式不可为空"));
            Assert.notNull(item.getPaymentRadio(),LocaleHandler.getLocaleMsg("付款比例不可为空"));
            Assert.notNull(item.getPaymentPeriodsNumber(),LocaleHandler.getLocaleMsg("付款期数不可为空"));
        });
    }
}
