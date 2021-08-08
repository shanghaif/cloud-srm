package com.midea.cloud.srm.base.organization.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.base.organization.service.ICategoryService;
import com.midea.cloud.srm.model.base.organization.entity.Category;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  类别表（隆基类别同步） 前端控制器
 * </pre>
*
* @author xiexh12@midea.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-21 19:28:00
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/organization/category")
public class CategoryController extends BaseController {

    @Autowired
    private ICategoryService iCategoryService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public Category get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iCategoryService.getById(id);
    }

    /**
    * 新增
    * @param category
    */
    @PostMapping("/add")
    public void add(@RequestBody Category category) {
        Long id = IdGenrator.generate();
        iCategoryService.save(category);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iCategoryService.removeById(id);
    }

    /**
    * 修改
    * @param category
    */
    @PostMapping("/modify")
    public void modify(@RequestBody Category category) {
        iCategoryService.updateById(category);
    }

    /**
    * 分页查询
    * @param category
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<Category> listPage(@RequestBody Category category) {
        PageUtil.startPage(category.getPageNum(), category.getPageSize());
        QueryWrapper<Category> wrapper = new QueryWrapper<Category>(category);
        return new PageInfo<Category>(iCategoryService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<Category> listAll() { 
        return iCategoryService.list();
    }
 
}
