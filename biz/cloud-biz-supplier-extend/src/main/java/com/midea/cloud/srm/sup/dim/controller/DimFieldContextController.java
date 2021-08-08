package com.midea.cloud.srm.sup.dim.controller;

import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.supplier.dim.entity.DimFieldContext;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.sup.dim.service.IDimFieldContextService;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  维度字段内容表 前端控制器
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-02 15:17:08
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/dim/dimFieldContext")
public class DimFieldContextController extends BaseController {

    @Autowired
    private IDimFieldContextService iDimFieldContextService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public DimFieldContext get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iDimFieldContextService.getById(id);
    }

    /**
    * 新增
    * @param dimFieldContext
    */
    @PostMapping("/add")
    public void add(DimFieldContext dimFieldContext) {
        Long id = IdGenrator.generate();
        dimFieldContext.setFieldContextId(id);
        iDimFieldContextService.save(dimFieldContext);
    }
    
    /**
    * 删除
    * @param id
    */
    @PostMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iDimFieldContextService.removeById(id);
    }

    /**
    * 修改
    * @param dimFieldContext
    */
    @PostMapping("/modify")
    public void modify(DimFieldContext dimFieldContext) {
        iDimFieldContextService.updateById(dimFieldContext);
    }

    /**
    * 分页查询
    * @param dimFieldContext
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<DimFieldContext> listPage(DimFieldContext dimFieldContext) {
        PageUtil.startPage(dimFieldContext.getPageNum(), dimFieldContext.getPageSize());
        QueryWrapper<DimFieldContext> wrapper = new QueryWrapper<DimFieldContext>(dimFieldContext);
        return new PageInfo<DimFieldContext>(iDimFieldContextService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<DimFieldContext> listAll() { 
        return iDimFieldContextService.list();
    }
 
}
