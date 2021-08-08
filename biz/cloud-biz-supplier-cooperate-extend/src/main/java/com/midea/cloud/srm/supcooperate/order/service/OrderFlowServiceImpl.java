package com.midea.cloud.srm.supcooperate.order.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.midea.cloud.common.enums.neworder.OrderDetailStatus;
import com.midea.cloud.common.enums.pm.po.PurchaseOrderEnum;
import com.midea.cloud.common.enums.pm.pr.requirement.RequirementApproveStatus;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementHead;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.Order;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.OrderDetail;
import com.midea.cloud.srm.model.workflow.service.IFlowBusinessCallbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OrderFlowServiceImpl implements IFlowBusinessCallbackService {

    @Autowired IOrderService iOrderService;

    @Autowired
    private IOrderDetailService iOrderDetailService;
    @Override
    public void submitFlow(Long businessId, String param) throws Exception {
        log.info("submitFlow: {}, {}", businessId, param);

        Order order = new Order().setOrderId(businessId).setOrderStatus(PurchaseOrderEnum.UNDER_APPROVAL.getValue());
        iOrderService.updateById(order);
    }

    @Override
    public void passFlow(Long businessId, String param) throws Exception {
        log.info("passFlow: {}, {}", businessId, param);
        
        Order order = new Order().setOrderId(businessId).setOrderStatus(PurchaseOrderEnum.APPROVED.getValue());
        iOrderService.updateById(order);

        //修改订单明细
        List<OrderDetail> orderDetailList = iOrderDetailService.list(new QueryWrapper<OrderDetail>(new OrderDetail().setOrderId(businessId)));
        for(OrderDetail orderDetail : orderDetailList){
            //【是否供应商确认】逻辑控制
            if("Y".equals(order.getCeeaIfSupplierConfirm())){
                orderDetail.setOrderDetailStatus(OrderDetailStatus.WAITING_VENDOR_CONFIRM.getValue());
            }else{
                orderDetail.setOrderDetailStatus(OrderDetailStatus.ACCEPT.getValue());
            }
            orderDetail.setCeeaApprovedNum(orderDetail.getOrderNum());
            orderDetail.setCeeaFirstApprovedNum(orderDetail.getOrderNum());
        }
        iOrderDetailService.updateBatchById(orderDetailList);
    }

    @Override
    public void rejectFlow(Long businessId, String param) throws Exception {
        log.info("rejectFlow: {}, {}", businessId, param);
        Order order = new Order().setOrderId(businessId).setOrderStatus(PurchaseOrderEnum.REJECT.getValue());
        iOrderService.updateById(order);
    }

    @Override
    public void withdrawFlow(Long businessId, String param) throws Exception {
        log.info("withdrawFlow: {}, {}", businessId, param);
        Order order = new Order().setOrderId(businessId).setOrderStatus(PurchaseOrderEnum.WITHDRAW.getValue());
        iOrderService.updateById(order);
    }

    @Override
    public void destoryFlow(Long businessId, String param) throws Exception {
        log.info("destoryFlow: {}, {}", businessId, param);
        Order order = new Order().setOrderId(businessId).setOrderStatus(PurchaseOrderEnum.ABANDONED.getValue());
        iOrderService.updateById(order);
    }

    @Override
    public String getVariableFlow(Long businessId, String param) throws Exception {
        log.info("getVariableFlow: {}, {}", businessId, param);
        return null;
    }

    @Override
    public String getDataPushFlow(Long businessId, String param) throws Exception {
        log.info("getDataPushFlow: {}, {}", businessId, param);
        return null;
    }
}
