package com.midea.cloud.srm.base.organization.controller;

import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.base.organization.entity.GidailyRate;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.midea.cloud.srm.base.organization.service.IGidailyRateService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  汇率表（隆基汇率同步） 前端控制器
 * </pre>
*
* @author xiexh12@midea.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-25 11:00:26
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/organization/gidaily-rate")
public class GidailyRateController extends BaseController {

    @Autowired
    private IGidailyRateService iGidailyRateService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public GidailyRate get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iGidailyRateService.getById(id);
    }

    /**
    * 新增
    * @param gidailyRate
    */
    @PostMapping("/add")
    public void add(@RequestBody GidailyRate gidailyRate) {
        Long id = IdGenrator.generate();
        gidailyRate.setId(id);
        iGidailyRateService.save(gidailyRate);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iGidailyRateService.removeById(id);
    }

    /**
    * 修改
    * @param gidailyRate
    */
    @PostMapping("/modify")
    public void modify(@RequestBody GidailyRate gidailyRate) {
        iGidailyRateService.updateById(gidailyRate);
    }

    /**
    * 分页查询
    * @param gidailyRate
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<GidailyRate> listPage(@RequestBody GidailyRate gidailyRate) {
        PageUtil.startPage(gidailyRate.getPageNum(), gidailyRate.getPageSize());
        QueryWrapper<GidailyRate> wrapper = new QueryWrapper<GidailyRate>(gidailyRate);
        return new PageInfo<GidailyRate>(iGidailyRateService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<GidailyRate> listAll() { 
        return iGidailyRateService.list();
    }
 
}
