package com.midea.cloud.srm.po.logisticsOrder.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.logistics.po.order.entity.OrderFile;
import com.midea.cloud.srm.po.logisticsOrder.service.IOrderFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  物流采购订单附件 前端控制器
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-12-05 18:52:41
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/po/order-file")
public class OrderFileController extends BaseController {

    @Autowired
    private IOrderFileService iOrderFileService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public OrderFile get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iOrderFileService.getById(id);
    }

    /**
    * 新增
    * @param orderFile
    */
    @PostMapping("/add")
    public void add(@RequestBody OrderFile orderFile) {
        Long id = IdGenrator.generate();
        orderFile.setOrderFileId(id);
        iOrderFileService.save(orderFile);
    }

    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iOrderFileService.removeById(id);
    }

    /**
    * 修改
    * @param orderFile
    */
    @PostMapping("/modify")
    public void modify(@RequestBody OrderFile orderFile) {
        iOrderFileService.updateById(orderFile);
    }

    /**
    * 分页查询
    * @param orderFile
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<OrderFile> listPage(@RequestBody OrderFile orderFile) {
        PageUtil.startPage(orderFile.getPageNum(), orderFile.getPageSize());
        QueryWrapper<OrderFile> wrapper = new QueryWrapper<OrderFile>(orderFile);
        return new PageInfo<OrderFile>(iOrderFileService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<OrderFile> listAll() {
        return iOrderFileService.list();
    }

}
