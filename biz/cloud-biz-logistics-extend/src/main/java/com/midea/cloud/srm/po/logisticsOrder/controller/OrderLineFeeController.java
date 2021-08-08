package com.midea.cloud.srm.po.logisticsOrder.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.logistics.po.order.entity.OrderLineFee;
import com.midea.cloud.srm.po.logisticsOrder.service.IOrderLineFeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  物流采购订单费用项表 前端控制器
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-12-05 18:52:09
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/po/order-line-fee")
public class OrderLineFeeController extends BaseController {

    @Autowired
    private IOrderLineFeeService iOrderLineFeeService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public OrderLineFee get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iOrderLineFeeService.getById(id);
    }

    /**
    * 新增
    * @param orderLineFee
    */
    @PostMapping("/add")
    public void add(@RequestBody OrderLineFee orderLineFee) {
        Long id = IdGenrator.generate();
        orderLineFee.setOrderLineFeeId(id);
        iOrderLineFeeService.save(orderLineFee);
    }

    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iOrderLineFeeService.removeById(id);
    }

    /**
    * 修改
    * @param orderLineFee
    */
    @PostMapping("/modify")
    public void modify(@RequestBody OrderLineFee orderLineFee) {
        iOrderLineFeeService.updateById(orderLineFee);
    }

    /**
    * 分页查询
    * @param orderLineFee
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<OrderLineFee> listPage(@RequestBody OrderLineFee orderLineFee) {
        PageUtil.startPage(orderLineFee.getPageNum(), orderLineFee.getPageSize());
        QueryWrapper<OrderLineFee> wrapper = new QueryWrapper<OrderLineFee>(orderLineFee);
        return new PageInfo<OrderLineFee>(iOrderLineFeeService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<OrderLineFee> listAll() {
        return iOrderLineFeeService.list();
    }

}
