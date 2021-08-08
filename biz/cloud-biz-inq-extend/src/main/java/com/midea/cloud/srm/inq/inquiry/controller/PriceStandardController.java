package com.midea.cloud.srm.inq.inquiry.controller;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.midea.cloud.srm.inq.inquiry.service.IPriceStandardService;
import com.midea.cloud.srm.model.inq.inquiry.entity.PriceStandard;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  配额-差价标准 前端控制器
 * </pre>
*
* @author yourname@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-05 16:40:36
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/inquiry/priceStandard")
public class PriceStandardController extends BaseController {

    @Autowired
    private IPriceStandardService iPriceStandardService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public PriceStandard get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iPriceStandardService.getById(id);
    }

    /**
    * 新增或保存
    * @param priceStandard
    */
    @PostMapping("/priceStandardAdd")
    public void priceStandardAdd(@RequestBody List<PriceStandard> priceStandard) {
        Assert.isTrue(CollectionUtils.isNotEmpty(priceStandard),"保存或修改的对象不能为空。");
        iPriceStandardService.priceStandardAdd(priceStandard);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iPriceStandardService.removeById(id);
    }

    /**
    * 修改
    * @param priceStandard
    */
    @PostMapping("/modify")
    public void modify(@RequestBody PriceStandard priceStandard) {
        iPriceStandardService.updateById(priceStandard);
    }

    /**
    * 分页查询
    * @param priceStandard
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<PriceStandard> listPage(@RequestBody PriceStandard priceStandard) {
        PageUtil.startPage(priceStandard.getPageNum(), priceStandard.getPageSize());
        QueryWrapper<PriceStandard> wrapper = new QueryWrapper<PriceStandard>(priceStandard);
        return new PageInfo<PriceStandard>(iPriceStandardService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<PriceStandard> listAll() { 
        return iPriceStandardService.list();
    }
 
}
