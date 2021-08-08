package com.midea.cloud.srm.base.organization.controller;

import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.base.organization.service.IErpPurchaseTaxService;
import com.midea.cloud.srm.model.base.organization.entity.ErpPurchaseTax;
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
 *  税率表（隆基税率数据同步） 前端控制器
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-12 18:13:35
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/organization/erp-purchase-tax")
public class ErpPurchaseTaxController extends BaseController {

    @Autowired
    private IErpPurchaseTaxService iErpPurchaseTaxService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public ErpPurchaseTax get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iErpPurchaseTaxService.getById(id);
    }

    /**
    * 新增
    * @param erpPurchaseTax
    */
    @PostMapping("/add")
    public void add(@RequestBody ErpPurchaseTax erpPurchaseTax) {
        Long id = IdGenrator.generate();
        erpPurchaseTax.setTaxId(id);
        iErpPurchaseTaxService.save(erpPurchaseTax);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iErpPurchaseTaxService.removeById(id);
    }

    /**
    * 修改
    * @param erpPurchaseTax
    */
    @PostMapping("/modify")
    public void modify(@RequestBody ErpPurchaseTax erpPurchaseTax) {
        iErpPurchaseTaxService.updateById(erpPurchaseTax);
    }

    /**
    * 分页查询
    * @param erpPurchaseTax
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<ErpPurchaseTax> listPage(@RequestBody ErpPurchaseTax erpPurchaseTax) {
        PageUtil.startPage(erpPurchaseTax.getPageNum(), erpPurchaseTax.getPageSize());
        QueryWrapper<ErpPurchaseTax> wrapper = new QueryWrapper<ErpPurchaseTax>(erpPurchaseTax);
        return new PageInfo<ErpPurchaseTax>(iErpPurchaseTaxService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<ErpPurchaseTax> listAll() { 
        return iErpPurchaseTaxService.list();
    }
 
}
