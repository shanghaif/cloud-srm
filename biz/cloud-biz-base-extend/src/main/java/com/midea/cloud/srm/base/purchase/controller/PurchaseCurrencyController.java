package com.midea.cloud.srm.base.purchase.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.base.purchase.service.IPurchaseCurrencyService;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseCurrency;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  币种设置 前端控制器
 * </pre>
*
* @author chensl26@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-02-26 14:08:07
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/purchase/purchaseCurrency")
public class PurchaseCurrencyController extends BaseController {

    @Autowired
    private IPurchaseCurrencyService iPurchaseCurrencyService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public PurchaseCurrency get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iPurchaseCurrencyService.getById(id);
    }

    /**
    * 新增
    * @param purchaseCurrency
    */
    @PostMapping("/add")
    public void add(PurchaseCurrency purchaseCurrency) {
        Long id = IdGenrator.generate();
        purchaseCurrency.setCurrencyId(id);
        iPurchaseCurrencyService.save(purchaseCurrency);
    }

    /**
    * 删除
    * @param id
    */
    @PostMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iPurchaseCurrencyService.removeById(id);
    }

    /**
    * 修改
    * @param purchaseCurrency
    */
    @PostMapping("/modify")
    public void modify(PurchaseCurrency purchaseCurrency) {
        iPurchaseCurrencyService.updateById(purchaseCurrency);
    }


    /**
     * 分页条件查询
     * @param purchaseCurrency
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<PurchaseCurrency> listPage(@RequestBody PurchaseCurrency purchaseCurrency) {
        return iPurchaseCurrencyService.listPage(purchaseCurrency);
    }

    /**
    * 查询所有生效
    * @return
    */
    @PostMapping("/listAll")
    public List<PurchaseCurrency> listAll() {
        return iPurchaseCurrencyService.listEnableAndLanguage();
    }


    /**
     * 新增或编辑币种
     * @param purchaseCurrency
     */
    @PostMapping("/saveOrUpdate")
    public void saveOrUpdate(@RequestBody PurchaseCurrency purchaseCurrency) {
        iPurchaseCurrencyService.saveOrUpdateCurrency(purchaseCurrency);

    }

    /**
     * 根据参数获取货币
     * @param purchaseCurrency
     * @return
     */
    @PostMapping("/getPurchaseCurrencyByParam")
    public PurchaseCurrency getPurchaseCurrencyByParam(@RequestBody PurchaseCurrency purchaseCurrency) {
        return iPurchaseCurrencyService.getOne(new QueryWrapper<PurchaseCurrency>(purchaseCurrency));
    }

    /**
     * 查询所有币种(价格库导入使用)
     */
    @GetMapping("/listAllCurrencyForImport")
    public List<PurchaseCurrency> listAllCurrencyForImport(){
        return iPurchaseCurrencyService.listAllCurrencyForImport();
    }

}
