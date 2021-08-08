package com.midea.cloud.srm.po.logisticsOrder.controller;

import com.midea.cloud.common.enums.logistics.LogisticsOrderStatus;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.logistics.po.order.dto.LogisticsOrderDTO;
import com.midea.cloud.srm.model.logistics.po.order.entity.OrderHead;
import com.midea.cloud.srm.model.logistics.po.order.vo.LogisticsOrderVO;
import com.midea.cloud.srm.po.logisticsOrder.service.IOrderHeadService;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
 *  <pre>
 *  物流采购订单头表 前端控制器
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
@RestController(value = "LogisticsOrderHeadController")
@RequestMapping("/po/order-head")
public class OrderHeadController extends BaseController {

    @Autowired
    private IOrderHeadService iOrderHeadService;

    /**
     * 获取
     * @param id
     */
    @GetMapping("/get")
    public OrderHead get(@RequestParam("id") Long id) {
        Assert.notNull(id, "id不能为空");
        return iOrderHeadService.getById(id);
    }

    /**
     * 获取
     * @param id
     */
    @GetMapping("/getByHeadId")
    public LogisticsOrderVO getByHeadId(@RequestParam("id") Long id) {
        Assert.notNull(id, "id不能为空");
        return iOrderHeadService.getByHeadId(id);
    }

    /**
     * 新增
     * @param orderDTO
     */
    @PostMapping("/add")
    public Long add(@RequestBody LogisticsOrderDTO orderDTO) {
        return iOrderHeadService.addOrder(orderDTO);
    }

    /**
     * 删除
     * @param id
     */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iOrderHeadService.deleteByHeadId(id);
    }

    /**
     * 修改
     * @param orderHead
     */
    @PostMapping("/modify")
    public void modify(@RequestBody OrderHead orderHead) {
        iOrderHeadService.updateById(orderHead);
    }

    /**
     * 分页查询
     * @param orderHead
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<OrderHead> listPage(@RequestBody OrderHead orderHead) {
        return iOrderHeadService.listPage(orderHead);
    }

    /**
     * 查询所有
     * @return
     */
    @PostMapping("/listAll")
    public List<OrderHead> listAll() {
        return iOrderHeadService.list();
    }

    /**
     * 暂存
     * @return
     */
    @PostMapping("/temporarySave")
    public Long temporarySave(@RequestBody LogisticsOrderDTO orderDTO){
        return iOrderHeadService.temporarySave(orderDTO);
    }

    /**
     * 提交审批
     *
     * @param orderDTO
     */
//    @PreAuthorize("hasAuthority('pr:requirementApply:submitAudit')")
    @PostMapping("/submitApproval")
    public Long submitApproval(@RequestBody LogisticsOrderDTO orderDTO) {
        return iOrderHeadService.submitApproval(orderDTO);

    }


    /**
     * 完成
     *
     * @param orderHeadId
     */
//    @PreAuthorize("hasAuthority('pr:requirementApply:confirm')")
    @GetMapping("/approval")
    public void approval(Long orderHeadId) {
        iOrderHeadService.updateApproved(orderHeadId, LogisticsOrderStatus.COMPLETED.getValue());
    }

    /**
     * 提交供应商确认
     *
     * @param orderHeadId
     */
//    @PreAuthorize("hasAuthority('pr:requirementApply:confirm')")
    @GetMapping("/submitVendorConfirm")
    public void submitVendorConfirm(@RequestParam("orderHeadId") Long orderHeadId) {
        iOrderHeadService.submitVendorConfirm(orderHeadId);
    }

    /**
     * 供应商拒绝
     *
     * @param orderHead
     */
//    @PreAuthorize("hasAuthority('pr:requirementApply:confirm')")
    @PostMapping("/refuse")
    public void refuse(@RequestBody OrderHead orderHead) {
        iOrderHeadService.refuse(orderHead);
    }


    /**
     * 订单同步tms
     * @param orderHeadId
     */
    @GetMapping("/syncTms")
    public void syncTms(@RequestParam("orderHeadId") Long orderHeadId){
//        iOrderHeadService.syncTms(orderHeadId);
        iOrderHeadService.syncTmsLongi(orderHeadId);
    }

    /**
     * 招标单转物流采购订单
     * @param bidingId
     * @return
     */
    @GetMapping("/bidingToOrders")
    public List<OrderHead> bidingToOrders(@RequestParam("bidingId") Long bidingId){
        return iOrderHeadService.bidingToOrders(bidingId);
    }

    /**
     * 批量作废订单
     * @param ids
     */
    @PostMapping("/batchCancel")
    public void batchCancel(@RequestBody List<Long> ids){
        iOrderHeadService.batchCancel(ids);
    }

    /**
     * 批量删除订单
     * @param ids
     */
    @PostMapping("/batchDelete")
    public void batchDelete(@RequestBody List<Long> ids) {
        iOrderHeadService.batchDelete(ids);
    }



}
