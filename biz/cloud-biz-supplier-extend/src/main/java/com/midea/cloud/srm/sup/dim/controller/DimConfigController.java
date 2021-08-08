package com.midea.cloud.srm.sup.dim.controller;

import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.supplier.dim.dto.DimConfigDTO;
import com.midea.cloud.srm.model.supplier.dim.entity.DimConfig;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.sup.dim.service.IDimConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  维度设置表 前端控制器
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-02 08:50:58
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/dim/dimConfig")
public class DimConfigController extends BaseController {

    @Autowired
    private IDimConfigService iDimConfigService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public DimConfig get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iDimConfigService.getById(id);
    }

    /**
    * 新增
    * @param dimConfig
    */
    @PostMapping("/add")
    public void add(DimConfig dimConfig) {
        Long id = IdGenrator.generate();
        dimConfig.setDimConfigId(id);
        iDimConfigService.save(dimConfig);
    }
    
    /**
    * 删除
    * @param id
    */
    @PostMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iDimConfigService.removeById(id);
    }

    /**
    * 修改
    * @param dimConfig
    */
    @PostMapping("/modify")
    public void modify(DimConfig dimConfig) {
        iDimConfigService.updateById(dimConfig);
    }

    /**
    * 分页查询
    * @param dimConfig
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<DimConfig> listPage(DimConfig dimConfig) {
        PageUtil.startPage(dimConfig.getPageNum(), dimConfig.getPageSize());
        QueryWrapper<DimConfig> wrapper = new QueryWrapper<DimConfig>(dimConfig);
        return new PageInfo<DimConfig>(iDimConfigService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<DimConfig> listAll() { 
        return iDimConfigService.list();
    }

    /**
     * 查询所有
     * @return
     */
    @PostMapping("/getDtoByParam")
    public List<DimConfigDTO> getDtoByParam(@RequestBody DimConfig dimConfig) {

        return iDimConfigService.getDtoByParam(dimConfig);
    }

}
