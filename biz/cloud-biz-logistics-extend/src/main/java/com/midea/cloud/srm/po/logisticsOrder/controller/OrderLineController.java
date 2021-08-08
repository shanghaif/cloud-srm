package com.midea.cloud.srm.po.logisticsOrder.controller;

import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.logistics.po.order.entity.OrderLine;
import com.midea.cloud.srm.po.logisticsOrder.service.IOrderLineService;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  物流采购订单行表 前端控制器
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-12-05 18:51:00
 *  修改内容:
 * </pre>
*/
@RestController(value = "LogisticsOrderLineController")
@RequestMapping("/po/order-line")
public class OrderLineController extends BaseController {

    @Autowired
    private IOrderLineService iOrderLineService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public OrderLine get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iOrderLineService.getById(id);
    }

    /**
    * 新增
    * @param orderLine
    */
    @PostMapping("/add")
    public void add(@RequestBody OrderLine orderLine) {
        Long id = IdGenrator.generate();
        orderLine.setOrderLineId(id);
        iOrderLineService.save(orderLine);
    }

    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iOrderLineService.deleteByLineId(id);
    }

    /**
    * 修改
    * @param orderLine
    */
    @PostMapping("/modify")
    public void modify(@RequestBody OrderLine orderLine) {
        iOrderLineService.updateById(orderLine);
    }

    /**
    * 分页查询
    * @param orderLine
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<OrderLine> listPage(@RequestBody OrderLine orderLine) {
        PageUtil.startPage(orderLine.getPageNum(), orderLine.getPageSize());
        QueryWrapper<OrderLine> wrapper = new QueryWrapper<OrderLine>(orderLine);
        return new PageInfo<OrderLine>(iOrderLineService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<OrderLine> listAll() {
        return iOrderLineService.list();
    }

}
