package com.midea.cloud.srm.po.logisticsOrder.controller;

import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.logistics.po.order.entity.OrderLineShip;
import com.midea.cloud.srm.po.logisticsOrder.service.IOrderLineShipService;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  物流采购订单行船期表 前端控制器
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-12-11 11:21:42
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/po/order-line-ship")
public class OrderLineShipController extends BaseController {

    @Autowired
    private IOrderLineShipService iOrderLineShipService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public OrderLineShip get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iOrderLineShipService.getById(id);
    }

    /**
    * 新增
    * @param orderLineShip
    */
    @PostMapping("/add")
    public void add(@RequestBody OrderLineShip orderLineShip) {
        Long id = IdGenrator.generate();
        orderLineShip.setOrderLineShipId(id);
        iOrderLineShipService.save(orderLineShip);
    }

    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iOrderLineShipService.removeById(id);
    }

    /**
    * 修改
    * @param orderLineShip
    */
    @PostMapping("/modify")
    public void modify(@RequestBody OrderLineShip orderLineShip) {
        iOrderLineShipService.updateById(orderLineShip);
    }

    /**
    * 分页查询
    * @param orderLineShip
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<OrderLineShip> listPage(@RequestBody OrderLineShip orderLineShip) {
        PageUtil.startPage(orderLineShip.getPageNum(), orderLineShip.getPageSize());
        QueryWrapper<OrderLineShip> wrapper = new QueryWrapper<OrderLineShip>(orderLineShip);
        return new PageInfo<OrderLineShip>(iOrderLineShipService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<OrderLineShip> listAll() {
        return iOrderLineShipService.list();
    }

}
