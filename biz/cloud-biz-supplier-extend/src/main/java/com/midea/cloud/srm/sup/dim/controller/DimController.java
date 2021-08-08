package com.midea.cloud.srm.sup.dim.controller;

import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.sup.dim.service.IDimService;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import java.util.List;
import com.midea.cloud.srm.model.supplier.dim.entity.Dim;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  维度表 前端控制器
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-02-29 15:17:35
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/dim/dim")
public class DimController extends BaseController {

    @Autowired
    private IDimService iDimService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public Dim get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iDimService.getById(id);
    }

    /**
    * 新增
    * @param dim
    */
    @PostMapping("/add")
    public void add(Dim dim) {
        Long id = IdGenrator.generate();
        dim.setDimId(id);
        iDimService.save(dim);
    }
    
    /**
    * 删除
    * @param id
    */
    @PostMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iDimService.removeById(id);
    }

    /**
    * 修改
    * @param dim
    */
    @PostMapping("/modify")
    public void modify(Dim dim) {
        iDimService.updateById(dim);
    }

    /**
    * 分页查询
    * @param dim
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<Dim> listPage(Dim dim) {
        PageUtil.startPage(dim.getPageNum(), dim.getPageSize());
        QueryWrapper<Dim> wrapper = new QueryWrapper<Dim>(dim);
        return new PageInfo<Dim>(iDimService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<Dim> listAll() { 
        return iDimService.list();
    }

    /**
     * 排序查询全部
     * @param dim
     * @return
     */
    @PostMapping("/listOrder")
    public List<Dim> listOrder(Dim dim) {
        PageUtil.startPage(dim.getPageNum(), dim.getPageSize());
        QueryWrapper<Dim> wrapper = new QueryWrapper<Dim>(dim);
        wrapper.orderByAsc("ORDER_NUM");
        return iDimService.list(wrapper);
    }

    /**
     * 更新属性维度配置(对应数据只能更新一次)
     * @param dimList
     * @return
     */
    @PostMapping("/definitionDim")
    public void definitionDim(@RequestBody List<Dim> dimList) {
        iDimService.definitionDim(dimList);
    }

    /**
     * 更新维度的基本信息
     * @param dimList
     */
    @PostMapping("/updateBasic")
    public void updateBasic(@RequestBody List<Dim> dimList) {
        iDimService.updateBasic(dimList);
    }
}
