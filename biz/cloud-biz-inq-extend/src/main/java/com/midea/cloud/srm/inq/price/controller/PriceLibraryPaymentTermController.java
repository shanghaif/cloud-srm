package com.midea.cloud.srm.inq.price.controller;

import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.inq.price.service.IPriceLibraryPaymentTermService;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.inq.price.entity.PriceLibraryPaymentTerm;
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
 *  价格库付款条款 前端控制器
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-24 22:14:25
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/inq/price-library-payment-term")
public class PriceLibraryPaymentTermController extends BaseController {

    @Autowired
    private IPriceLibraryPaymentTermService iPriceLibraryPaymentTermService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public PriceLibraryPaymentTerm get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iPriceLibraryPaymentTermService.getById(id);
    }

    /**
    * 新增
    * @param priceLibraryPaymentTerm
    */
    @PostMapping("/add")
    public void add(@RequestBody PriceLibraryPaymentTerm priceLibraryPaymentTerm) {
        Long id = IdGenrator.generate();
        priceLibraryPaymentTerm.setPriceLibraryPaymentTermId(id);
        iPriceLibraryPaymentTermService.save(priceLibraryPaymentTerm);
    }

    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iPriceLibraryPaymentTermService.removeById(id);
    }

    /**
    * 修改
    * @param priceLibraryPaymentTerm
    */
    @PostMapping("/modify")
    public void modify(@RequestBody PriceLibraryPaymentTerm priceLibraryPaymentTerm) {
        iPriceLibraryPaymentTermService.updateById(priceLibraryPaymentTerm);
    }

    /**
    * 分页查询
    * @param priceLibraryPaymentTerm
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<PriceLibraryPaymentTerm> listPage(@RequestBody PriceLibraryPaymentTerm priceLibraryPaymentTerm) {
        PageUtil.startPage(priceLibraryPaymentTerm.getPageNum(), priceLibraryPaymentTerm.getPageSize());
        QueryWrapper<PriceLibraryPaymentTerm> wrapper = new QueryWrapper<PriceLibraryPaymentTerm>(priceLibraryPaymentTerm);
        return new PageInfo<PriceLibraryPaymentTerm>(iPriceLibraryPaymentTermService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<PriceLibraryPaymentTerm> listAll() {
        return iPriceLibraryPaymentTermService.list();
    }


    /**
     * 根据条件查询所有
     * @param priceLibraryPaymentTerm
     * @return
     */
    @PostMapping("/list")
    public List<PriceLibraryPaymentTerm> list(@RequestBody PriceLibraryPaymentTerm priceLibraryPaymentTerm){
        return iPriceLibraryPaymentTermService.list(new QueryWrapper<>(priceLibraryPaymentTerm));
    }

}
