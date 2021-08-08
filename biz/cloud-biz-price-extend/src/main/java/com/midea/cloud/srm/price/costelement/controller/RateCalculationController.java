package com.midea.cloud.srm.price.costelement.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.price.costelement.entity.RateCalculation;
import com.midea.cloud.srm.price.costelement.service.IRateCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
*  <pre>
 *  费率计算表 前端控制器
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-07-25 13:58:20
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/rate-calculation")
public class RateCalculationController extends BaseController {

    @Autowired
    private IRateCalculationService iRateCalculationService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public RateCalculation get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iRateCalculationService.getById(id);
    }

    /**
    * 新增
    * @param rateCalculation
    */
    @PostMapping("/add")
    public void add(@RequestBody RateCalculation rateCalculation) {
        Long id = IdGenrator.generate();
        rateCalculation.setRateCalculationId(id);
        iRateCalculationService.save(rateCalculation);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iRateCalculationService.removeById(id);
    }

    /**
    * 修改
    * @param rateCalculation
    */
    @PostMapping("/modify")
    public void modify(@RequestBody RateCalculation rateCalculation) {
        iRateCalculationService.updateById(rateCalculation);
    }

    /**
    * 分页查询
    * @param rateCalculation
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<RateCalculation> listPage(@RequestBody RateCalculation rateCalculation) {
        PageUtil.startPage(rateCalculation.getPageNum(), rateCalculation.getPageSize());
        QueryWrapper<RateCalculation> wrapper = new QueryWrapper<RateCalculation>(rateCalculation);
        return new PageInfo<RateCalculation>(iRateCalculationService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<RateCalculation> listAll() { 
        return iRateCalculationService.list();
    }
 
}
