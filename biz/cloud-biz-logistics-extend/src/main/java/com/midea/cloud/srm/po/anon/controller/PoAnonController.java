package com.midea.cloud.srm.po.anon.controller;

import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderSaveRequestDTO;
import com.midea.cloud.srm.po.order.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <pre>
 *  不需暴露外部网关的接口，服务内部调用接口(无需登录就可以对微服务模块间暴露)
 * </pre>
 *
 * @author ex_lizp6@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-4-11 9:09
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/po-anon")
@Slf4j
public class PoAnonController {
    @Autowired
    private IOrderService iorderService;

    /**
     * 查询订单详情
     *
     * @param orderId
     * @return
     */
    @GetMapping("/queryOrderById")
    public OrderSaveRequestDTO queryOrderById(Long orderId) {
        return iorderService.queryOrderById(orderId);
    }


    /**
     * 创建订单-审批（approvalInEditStatus历史遗留问题）
     */
    @PostMapping("/approvalInEditStatus")
    public void approvalInEditStatus(@RequestBody OrderSaveRequestDTO param){
        iorderService.approval(param);
    }

    /**
     * 编辑订单-驳回
     */
    @PostMapping("/rejectInEditStatus")
    public void rejectInEditStatus(@RequestBody OrderSaveRequestDTO param){
        iorderService.reject(param);
    }


    /**
     * 编辑订单-撤回
     */
    @PostMapping("/withdrawInEditStatus")
    public void withdrawInEditStatus(@RequestBody OrderSaveRequestDTO param) throws Exception{
        iorderService.withdrawInEditStatus(param);
    }

}
