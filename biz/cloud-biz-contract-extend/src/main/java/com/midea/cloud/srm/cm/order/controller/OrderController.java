package com.midea.cloud.srm.cm.order.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.common.utils.StringUtil;
import com.midea.cloud.srm.cm.order.service.IOrderService;
import com.midea.cloud.srm.model.cm.order.dto.OrderDto;
import com.midea.cloud.srm.model.cm.order.entity.Order;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
*  <pre>
 *  合同单据 前端控制器
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-07-06 09:37:25
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/order")
public class OrderController extends BaseController {

    @Autowired
    private IOrderService iOrderService;

    /**
    * 获取
    * @param orderId
    */
    @GetMapping("/queryById")
    public OrderDto queryById(Long orderId) {
        return iOrderService.queryById(orderId);
    }

    /**
    * 新增
    * @param orderDto
    */
    @PostMapping("/add")
    public Long add(@RequestBody OrderDto orderDto) {
        return iOrderService.add(orderDto);
    }
    
    /**
    * 删除
    * @param orderId
    */
    @GetMapping("/delete")
    public void delete(Long orderId) {
        Assert.notNull(orderId, "id不能为空");
        iOrderService.removeById(orderId);
    }

    /**
    * 修改
    * @param orderDto
    */
    @PostMapping("/modify")
    public void modify(@RequestBody OrderDto orderDto) {
        iOrderService.update(orderDto);
    }

    /**
    * 分页查询
    * @param order
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<Order> listPage(@RequestBody Order order) {
        PageUtil.startPage(order.getPageNum(), order.getPageSize());
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtil.notEmpty(order.getOrderCode()),"ORDER_CODE",order.getOrderCode());
        wrapper.like(StringUtil.notEmpty(order.getOrderName()),"ORDER_NAME",order.getOrderName());
        wrapper.orderByDesc("ORDER_ID");
        return new PageInfo<>(iOrderService.list(wrapper));
    }
 
}
