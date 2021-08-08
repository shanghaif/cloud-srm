package com.midea.cloud.srm.base.serviceconfig.controller;

import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.base.serviceconfig.service.ISupplierConfigService;
import com.midea.cloud.srm.model.base.serviceconfig.entity.SupplierConfig;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *   前端控制器
 * </pre>
*
* @author chensl26@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-02-26 16:18:14
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/serviceConfig/supplier-config")
public class SupplierConfigController extends BaseController {

    @Autowired
    private ISupplierConfigService iSupplierConfigService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public SupplierConfig get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iSupplierConfigService.getById(id);
    }

    /**
    * 新增
    * @param supplierConfig
    */
    @PostMapping("/add")
    public void add(SupplierConfig supplierConfig) {
        Long id = IdGenrator.generate();
        supplierConfig.setSupplierConfigId(id);
        iSupplierConfigService.save(supplierConfig);
    }
    
    /**
    * 删除
    * @param id
    */
    @PostMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iSupplierConfigService.removeById(id);
    }

    /**
    * 修改
    * @param supplierConfig
    */
    @PostMapping("/modify")
    public void modify(SupplierConfig supplierConfig) {
        iSupplierConfigService.updateById(supplierConfig);
    }

    /**
    * 分页查询
    * @param supplierConfig
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<SupplierConfig> listPage(SupplierConfig supplierConfig) {
        PageUtil.startPage(supplierConfig.getPageNum(), supplierConfig.getPageSize());
        QueryWrapper<SupplierConfig> wrapper = new QueryWrapper<SupplierConfig>(supplierConfig);
        return new PageInfo<SupplierConfig>(iSupplierConfigService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @GetMapping("/listAll")
    public List<SupplierConfig> listAll() { 
        return iSupplierConfigService.list();
    }


    /**
     * 新增
     * @param supplierConfig
     */
    @PostMapping("/saveForOne")
    public void saveForOne(@RequestBody SupplierConfig supplierConfig) {

        iSupplierConfigService.saveForOne(supplierConfig);
    }
 
}
