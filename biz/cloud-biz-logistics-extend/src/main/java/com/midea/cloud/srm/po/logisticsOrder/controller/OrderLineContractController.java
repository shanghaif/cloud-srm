package com.midea.cloud.srm.po.logisticsOrder.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.logistics.po.order.entity.OrderLineContract;
import com.midea.cloud.srm.po.logisticsOrder.service.IOrderLineContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  物流采购订单行合同表 前端控制器
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-12-05 18:51:30
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/po/order-line-contract")
public class OrderLineContractController extends BaseController {

    @Autowired
    private IOrderLineContractService iOrderLineContractService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public OrderLineContract get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iOrderLineContractService.getById(id);
    }

    /**
    * 新增
    * @param orderLineContract
    */
    @PostMapping("/add")
    public void add(@RequestBody OrderLineContract orderLineContract) {
        Long id = IdGenrator.generate();
        orderLineContract.setOrderLineContractId(id);
        iOrderLineContractService.save(orderLineContract);
    }

    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iOrderLineContractService.removeById(id);
    }

    /**
    * 修改
    * @param orderLineContract
    */
    @PostMapping("/modify")
    public void modify(@RequestBody OrderLineContract orderLineContract) {
        iOrderLineContractService.updateById(orderLineContract);
    }

    /**
    * 分页查询
    * @param orderLineContract
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<OrderLineContract> listPage(@RequestBody OrderLineContract orderLineContract) {
        PageUtil.startPage(orderLineContract.getPageNum(), orderLineContract.getPageSize());
        QueryWrapper<OrderLineContract> wrapper = new QueryWrapper<OrderLineContract>(orderLineContract);
        return new PageInfo<OrderLineContract>(iOrderLineContractService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<OrderLineContract> listAll() {
        return iOrderLineContractService.list();
    }

}
