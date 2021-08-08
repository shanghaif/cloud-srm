package com.midea.cloud.srm.base.purchase.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.base.purchase.service.IPurchaseTaxService;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseTax;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  税率设置 前端控制器
 * </pre>
*
* @author chensl26@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-02-27 15:06:10
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/purchase/purchaseTax")
public class PurchaseTaxController extends BaseController {

    @Autowired
    private IPurchaseTaxService iPurchaseTaxService;

    /**
     * 获取
     * @param id
     */
    @GetMapping("/get")
    public PurchaseTax get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iPurchaseTaxService.getById(id);
    }
    /**
     * 获取
     * @param taxKey
     * @param language
     */
    @GetMapping("/getByTaxKeyAndLanguage")
    public PurchaseTax getByTaxKeyAndLanguage(@RequestParam("taxKey") String taxKey,@RequestParam("language") String language) {
        Assert.notNull(taxKey, "税率编码不能为空");
        Assert.notNull(language, "语言不能为空");
        return iPurchaseTaxService.getOne(new QueryWrapper<PurchaseTax>().eq("TAX_KEY",taxKey).eq("LANGUAGE",language));
    }

    /**
    * 新增
    * @param purchaseTax
    */
    @PostMapping("/add")
    public void add(PurchaseTax purchaseTax) {
        Long id = IdGenrator.generate();
        purchaseTax.setTaxId(id);
        iPurchaseTaxService.save(purchaseTax);
    }
    
    /**
    * 删除
    * @param id
    */
    @PostMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iPurchaseTaxService.removeById(id);
    }

    /**
    * 修改
    * @param purchaseTax
    */
    @PostMapping("/modify")
    public void modify(PurchaseTax purchaseTax) {
        iPurchaseTaxService.updateById(purchaseTax);
    }

    /**
     * 分页条件查询
     * @param purchaseTax
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<PurchaseTax> listPage(@RequestBody PurchaseTax purchaseTax) {
        return iPurchaseTaxService.listPage(purchaseTax);
    }


    /**
    * 查询所有生效
    * @return
    */
    @PostMapping("/listAll")
    public List<PurchaseTax> listAll() { 
        return iPurchaseTaxService.listEnableAndLanguage();
    }


    /**
     * 新增或编辑税率
     * @param purchaseTax
     */
    @PostMapping("/saveOrUpdate")
    public void saveOrUpdate(@RequestBody PurchaseTax purchaseTax) {
        iPurchaseTaxService.saveOrUpdateTax(purchaseTax);
    }


}
