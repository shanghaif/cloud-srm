package com.midea.cloud.srm.base.organization.controller;

import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.base.organization.entity.ErpMaterialItem;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.midea.cloud.srm.base.organization.service.IErpMaterialItemService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  物料维护表（隆基物料同步） 前端控制器
 * </pre>
*
* @author xiexh12@midea.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-20 15:19:46
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/organization/material-item")
public class ErpMaterialItemController extends BaseController {

    @Autowired
    private IErpMaterialItemService IErpMaterialItemService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public ErpMaterialItem get(Long id) {
        Assert.notNull(id, "id不能为空");
        return IErpMaterialItemService.getById(id);
    }

    /**
    * 新增
    * @param erpMaterialItem
    */
    @PostMapping("/add")
    public void add(@RequestBody ErpMaterialItem erpMaterialItem) {
        Long id = IdGenrator.generate();
        erpMaterialItem.setId(id);
        IErpMaterialItemService.save(erpMaterialItem);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        IErpMaterialItemService.removeById(id);
    }

    /**
    * 修改
    * @param erpMaterialItem
    */
    @PostMapping("/modify")
    public void modify(@RequestBody ErpMaterialItem erpMaterialItem) {
        IErpMaterialItemService.updateById(erpMaterialItem);
    }

    /**
    * 分页查询
    * @param erpMaterialItem
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<ErpMaterialItem> listPage(@RequestBody ErpMaterialItem erpMaterialItem) {
        PageUtil.startPage(erpMaterialItem.getPageNum(), erpMaterialItem.getPageSize());
        QueryWrapper<ErpMaterialItem> wrapper = new QueryWrapper<ErpMaterialItem>(erpMaterialItem);
        return new PageInfo<ErpMaterialItem>(IErpMaterialItemService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<ErpMaterialItem> listAll() {
        return IErpMaterialItemService.list();
    }
 
}
