package com.midea.cloud.srm.supcooperate.order.controller;

import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.WarehousingReturnDetailErp;
import com.midea.cloud.srm.supcooperate.order.service.IWarehousingReturnDetailErpService;
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
 *  入库退货明细erp表 前端控制器
 * </pre>
*
* @author chenwj92@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-25 14:30:44
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/order/warehousingReturnDetailErp")
public class WarehousingReturnDetailErpController extends BaseController {

    @Autowired
    private IWarehousingReturnDetailErpService iWarehousingReturnDetailErpService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public WarehousingReturnDetailErp get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iWarehousingReturnDetailErpService.getById(id);
    }

    /**
    * 新增
    * @param warehousingReturnDetailErp
    */
    @PostMapping("/add")
    public void add(@RequestBody WarehousingReturnDetailErp warehousingReturnDetailErp) {
        Long id = IdGenrator.generate();
        warehousingReturnDetailErp.setWarehousingReturnDetailErpId(id);
        iWarehousingReturnDetailErpService.save(warehousingReturnDetailErp);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iWarehousingReturnDetailErpService.removeById(id);
    }

    /**
    * 修改
    * @param warehousingReturnDetailErp
    */
    @PostMapping("/modify")
    public void modify(@RequestBody WarehousingReturnDetailErp warehousingReturnDetailErp) {
        iWarehousingReturnDetailErpService.updateById(warehousingReturnDetailErp);
    }

    /**
    * 分页查询
    * @param warehousingReturnDetailErp
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<WarehousingReturnDetailErp> listPage(@RequestBody WarehousingReturnDetailErp warehousingReturnDetailErp) {
        PageUtil.startPage(warehousingReturnDetailErp.getPageNum(), warehousingReturnDetailErp.getPageSize());
        QueryWrapper<WarehousingReturnDetailErp> wrapper = new QueryWrapper<WarehousingReturnDetailErp>(warehousingReturnDetailErp);
        return new PageInfo<WarehousingReturnDetailErp>(iWarehousingReturnDetailErpService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<WarehousingReturnDetailErp> listAll() { 
        return iWarehousingReturnDetailErpService.list();
    }
 
}
