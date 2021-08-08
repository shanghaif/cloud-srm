package com.midea.cloud.srm.supcooperate.deliver.controller;

import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.suppliercooperate.deliver.dto.DeliverPlanDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliver.dto.OrderAppointDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliver.entity.DeliverPlan;
import com.midea.cloud.srm.model.suppliercooperate.deliver.entity.DeliverPlanDetail;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderDetailDTO;
import com.midea.cloud.srm.model.suppliercooperate.order.dto.OrderRequestDTO;
import com.midea.cloud.srm.supcooperate.deliver.service.IOrderAppointService;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.springframework.beans.factory.annotation.Autowired;
import com.midea.cloud.srm.model.suppliercooperate.deliver.entity.OrderAppoint;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  指定采购订单表 前端控制器
 * </pre>
*
* @author zhi1772778785@163.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-28 13:59:09
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/deliver/orderAppoint")
public class OrderAppointController extends BaseController {

    @Autowired
    private IOrderAppointService iOrderAppointService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public OrderAppoint get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iOrderAppointService.getById(id);
    }

    /**
    *批量新增
    * @param orderAppointDTO
    */
    @PostMapping("/addBatch")
    public void addBatch(@RequestBody OrderAppointDTO orderAppointDTO) {
        Assert.notNull(orderAppointDTO,"请勾选需要添加的采购订单");
        Assert.notNull(orderAppointDTO.getDeliverPlanDetail(),"请指定添加的采购订单的到货计划行信息");
        Assert.notNull(orderAppointDTO.getDeliverPlanDetail().getDeliverPlanDetailId(),"请指定添加的采购订单的到货计划行信息");
        Assert.isTrue(!CollectionUtils.isEmpty(orderAppointDTO.getOrderDetailDTOList()),"请勾选需要添加的采购订单");
        iOrderAppointService.addBatch(orderAppointDTO);
    }


    
    /**
    * 批量删除
    * @param ids
    */
    @PostMapping("/deleteBatch")
    public void deleteBatch(@RequestBody List<Long> ids) {
        Assert.isTrue(!CollectionUtils.isEmpty(ids),"请勾选需要删除的采购订单");
        iOrderAppointService.deleteBatch(ids);
    }

    /**
    * 修改
    * @param orderAppoint
    */
    @PostMapping("/modify")
    public void modify(@RequestBody OrderAppoint orderAppoint) {
        iOrderAppointService.updateById(orderAppoint);
    }

    /**
    * 分页查询
    * @param deliverPlanDetail
    * @return
    */
    @PostMapping("/orderAppointListPage")
    public PageInfo<OrderAppoint> orderAppointListPage(@RequestBody DeliverPlanDetail deliverPlanDetail) {
        return iOrderAppointService.orderAppointListPage(deliverPlanDetail);
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<OrderAppoint> listAll() { 
        return iOrderAppointService.list();
    }
    /**
     *查看筛选条件后的采购订单
     */
    @PostMapping("/OrderDetailListpage")
    public PageInfo<OrderDetailDTO> OrderDetailListpage(@RequestBody DeliverPlanDTO deliverPlan){
        Assert.notNull(deliverPlan, "筛选条件不能为空。");
        return iOrderAppointService.OrderDetailListpage(deliverPlan);
    }
    /**
     *查看筛选条件后的采购订单
     */
    @PostMapping("/OrderDetailpage")
    public PageInfo<OrderDetailDTO> OrderDetailpage(@RequestBody DeliverPlanDTO deliverPlan){
        Assert.notNull(deliverPlan, "筛选条件不能为空。");
        PageUtil.startPage(deliverPlan.getPageNum(), deliverPlan.getPageSize());
        return new PageInfo<OrderDetailDTO>(iOrderAppointService.OrderDetailList(deliverPlan));
    }

}
