package com.midea.cloud.srm.base.purchase.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.base.purchase.service.IPurchaseUnitService;
import com.midea.cloud.srm.model.base.purchase.entity.PurchaseUnit;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  单位设置 前端控制器
 * </pre>
*
* @author chensl26@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-02-28 11:40:17
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/purchase/purchaseUnit")
public class PurchaseUnitController extends BaseController {

    @Autowired
    private IPurchaseUnitService iPurchaseUnitService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public PurchaseUnit get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iPurchaseUnitService.getById(id);
    }

    /**
    * 新增
    * @param purchaseUnit
    */
    @PostMapping("/add")
    public void add(PurchaseUnit purchaseUnit) {
        Long id = IdGenrator.generate();
        purchaseUnit.setUnitId(id);
        iPurchaseUnitService.save(purchaseUnit);
    }

    /**
    * 删除
    * @param id
    */
    @PostMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iPurchaseUnitService.removeById(id);
    }

    /**
    * 修改
    * @param purchaseUnit
    */
    @PostMapping("/modify")
    public void modify(PurchaseUnit purchaseUnit) {
        iPurchaseUnitService.updateById(purchaseUnit);
    }

    /**
     * 分页条件查询
     * @param purchaseUnit
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<PurchaseUnit> listPage(@RequestBody PurchaseUnit purchaseUnit) {
        return iPurchaseUnitService.listPage(purchaseUnit);
    }

    /**
    * 查询所有生效
    * @return
    */
    @PostMapping("/listAll")
    public List<PurchaseUnit> listAll() {
        return iPurchaseUnitService.listEnableAndLanguage();
    }

    /**
     * 新增或编辑单位设置
     * @param purchaseUnit
     */
    @PostMapping("/saveOrUpdate")
    public void saveOrUpdate(@RequestBody PurchaseUnit purchaseUnit) {
        iPurchaseUnitService.saveOrUpdateUnit(purchaseUnit);
    }

    /**
     * 根据条件查询列表
     * @param purchaseUnit
     */
    @PostMapping("/listByParam")
    public List<PurchaseUnit> listByParam(@RequestBody PurchaseUnit purchaseUnit) {
        Assert.notNull(purchaseUnit, "入参单位对象不能为空");
        return iPurchaseUnitService.list(new QueryWrapper<>(purchaseUnit));
    }

    /**
     * 根据编码列表批量查询单位列表
     */
    @PostMapping("/listPurchaseUnitByCodeList")
    List<PurchaseUnit> listPurchaseUnitByCodeList(@RequestBody List<String> purchaseUnitCodeList) {
        return iPurchaseUnitService.listPurchaseUnitByCodeList(purchaseUnitCodeList);
    }

}
