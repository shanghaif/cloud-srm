package com.midea.cloud.srm.sup.riskraider.r8.controller;

import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.midea.cloud.srm.sup.riskraider.r8.service.IR8DiscreditRelationService;
import com.midea.cloud.srm.model.supplier.riskraider.r8.entity.R8DiscreditRelation;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  关联企业失信信息表 前端控制器
 * </pre>
*
* @author chenwt24@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-01-18 10:46:05
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/sup/r8-discredit-relation")
public class R8DiscreditRelationController extends BaseController {

    @Autowired
    private IR8DiscreditRelationService iR8DiscreditRelationService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public R8DiscreditRelation get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iR8DiscreditRelationService.getById(id);
    }

    /**
    * 新增
    * @param r8DiscreditRelation
    */
    @PostMapping("/add")
    public void add(@RequestBody R8DiscreditRelation r8DiscreditRelation) {
        Long id = IdGenrator.generate();
        r8DiscreditRelation.setDiscreditRelationId(id);
        iR8DiscreditRelationService.save(r8DiscreditRelation);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iR8DiscreditRelationService.removeById(id);
    }

    /**
    * 修改
    * @param r8DiscreditRelation
    */
    @PostMapping("/modify")
    public void modify(@RequestBody R8DiscreditRelation r8DiscreditRelation) {
        iR8DiscreditRelationService.updateById(r8DiscreditRelation);
    }

    /**
    * 分页查询
    * @param r8DiscreditRelation
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<R8DiscreditRelation> listPage(@RequestBody R8DiscreditRelation r8DiscreditRelation) {
        PageUtil.startPage(r8DiscreditRelation.getPageNum(), r8DiscreditRelation.getPageSize());
        QueryWrapper<R8DiscreditRelation> wrapper = new QueryWrapper<R8DiscreditRelation>(r8DiscreditRelation);
        return new PageInfo<R8DiscreditRelation>(iR8DiscreditRelationService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<R8DiscreditRelation> listAll() { 
        return iR8DiscreditRelationService.list();
    }
 
}
