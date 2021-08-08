package com.midea.cloud.srm.sup.dim.controller;

import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.supplier.dim.entity.DimFieldConfig;
import com.midea.cloud.srm.sup.dim.service.IDimFieldConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  维度字段配置表 前端控制器
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-02 10:04:17
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/dim/dimFieldConfig")
public class DimFieldConfigController extends BaseController {

    @Autowired
    private IDimFieldConfigService iDimFieldConfigService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public DimFieldConfig get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iDimFieldConfigService.getById(id);
    }

    /**
    * 新增
    * @param dimFieldConfig
    */
    @PostMapping("/add")
    public void add(DimFieldConfig dimFieldConfig) {
        Long id = IdGenrator.generate();
        dimFieldConfig.setFieldConfigId(id);
        iDimFieldConfigService.save(dimFieldConfig);
    }
    
    /**
    * 删除
    * @param id
    */
    @PostMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iDimFieldConfigService.removeById(id);
    }

    /**
    * 修改
    * @param dimFieldConfig
    */
    @PostMapping("/modify")
    public void modify(DimFieldConfig dimFieldConfig) {
        iDimFieldConfigService.updateById(dimFieldConfig);
    }

    /**
    * 分页查询
    * @param dimFieldConfig
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<DimFieldConfig> listPage(DimFieldConfig dimFieldConfig) {
        PageUtil.startPage(dimFieldConfig.getPageNum(), dimFieldConfig.getPageSize());
        QueryWrapper<DimFieldConfig> wrapper = new QueryWrapper<DimFieldConfig>(dimFieldConfig);
        return new PageInfo<DimFieldConfig>(iDimFieldConfigService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<DimFieldConfig> listAll() { 
        return iDimFieldConfigService.list();
    }
 
}
