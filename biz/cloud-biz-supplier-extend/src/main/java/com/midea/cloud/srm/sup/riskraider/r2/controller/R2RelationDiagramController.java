package com.midea.cloud.srm.sup.riskraider.r2.controller;

import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.midea.cloud.srm.sup.riskraider.r2.service.IR2RelationDiagramService;
import com.midea.cloud.srm.model.supplier.riskraider.r2.entity.R2RelationDiagram;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  关系挖掘表 前端控制器
 * </pre>
*
* @author chenwt24@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-01-18 10:19:50
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/cloud-biz-supplier-extend/r2-relation-diagram")
public class R2RelationDiagramController extends BaseController {

    @Autowired
    private IR2RelationDiagramService iR2RelationDiagramService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public R2RelationDiagram get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iR2RelationDiagramService.getById(id);
    }

    /**
    * 新增
    * @param r2RelationDiagram
    */
    @PostMapping("/add")
    public void add(@RequestBody R2RelationDiagram r2RelationDiagram) {
        Long id = IdGenrator.generate();
        r2RelationDiagram.setRelationDiagramId(id);
        iR2RelationDiagramService.save(r2RelationDiagram);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iR2RelationDiagramService.removeById(id);
    }

    /**
    * 修改
    * @param r2RelationDiagram
    */
    @PostMapping("/modify")
    public void modify(@RequestBody R2RelationDiagram r2RelationDiagram) {
        iR2RelationDiagramService.updateById(r2RelationDiagram);
    }

    /**
    * 分页查询
    * @param r2RelationDiagram
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<R2RelationDiagram> listPage(@RequestBody R2RelationDiagram r2RelationDiagram) {
        PageUtil.startPage(r2RelationDiagram.getPageNum(), r2RelationDiagram.getPageSize());
        QueryWrapper<R2RelationDiagram> wrapper = new QueryWrapper<R2RelationDiagram>(r2RelationDiagram);
        return new PageInfo<R2RelationDiagram>(iR2RelationDiagramService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<R2RelationDiagram> listAll() { 
        return iR2RelationDiagramService.list();
    }
 
}
