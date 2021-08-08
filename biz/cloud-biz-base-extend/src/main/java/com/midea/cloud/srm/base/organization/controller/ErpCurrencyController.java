package com.midea.cloud.srm.base.organization.controller;

import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.base.organization.entity.ErpCurrency;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.midea.cloud.srm.base.organization.service.IErpCurrencyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;

import java.util.Currency;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  币种表（隆基币种同步） 前端控制器
 * </pre>
*
* @author xiexh12@midea.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-25 14:16:59
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/organization/erpCurrency")
public class ErpCurrencyController extends BaseController {

    @Autowired
    private IErpCurrencyService iErpCurrencyService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public ErpCurrency get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iErpCurrencyService.getById(id);
    }

    /**
    * 新增
    * @param erpCurrency
    */
    @PostMapping("/add")
    public void add(@RequestBody ErpCurrency erpCurrency) {
        Long id = IdGenrator.generate();
        erpCurrency.setId(id);
        iErpCurrencyService.save(erpCurrency);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iErpCurrencyService.removeById(id);
    }

    /**
    * 修改
    * @param erpCurrency
    */
    @PostMapping("/modify")
    public void modify(@RequestBody ErpCurrency erpCurrency) {
        iErpCurrencyService.updateById(erpCurrency);
    }

    /**
    * 分页查询
    * @param erpCurrency
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<ErpCurrency> listPage(@RequestBody ErpCurrency erpCurrency) {
        PageUtil.startPage(erpCurrency.getPageNum(), erpCurrency.getPageSize());
        QueryWrapper<ErpCurrency> wrapper = new QueryWrapper<ErpCurrency>(erpCurrency);
        return new PageInfo<ErpCurrency>(iErpCurrencyService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<ErpCurrency> listAll() {
        return iErpCurrencyService.list();
    }
 
}
