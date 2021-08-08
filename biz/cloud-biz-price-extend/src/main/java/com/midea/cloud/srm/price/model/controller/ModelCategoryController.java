package com.midea.cloud.srm.price.model.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.price.model.entity.ModelCategory;
import com.midea.cloud.srm.price.model.service.IModelCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  价格模型采购分类 前端控制器
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-07-25 14:27:16
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/model-category")
public class ModelCategoryController extends BaseController {

    @Autowired
    private IModelCategoryService iModelCategoryService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public ModelCategory get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iModelCategoryService.getById(id);
    }

    /**
    * 新增
    * @param modelCategory
    */
    @PostMapping("/add")
    public void add(@RequestBody ModelCategory modelCategory) {
        Long id = IdGenrator.generate();
        modelCategory.setModelCategoryId(id);
        iModelCategoryService.save(modelCategory);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iModelCategoryService.removeById(id);
    }

    /**
    * 修改
    * @param modelCategory
    */
    @PostMapping("/modify")
    public void modify(@RequestBody ModelCategory modelCategory) {
        iModelCategoryService.updateById(modelCategory);
    }

    /**
    * 分页查询
    * @param modelCategory
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<ModelCategory> listPage(@RequestBody ModelCategory modelCategory) {
        PageUtil.startPage(modelCategory.getPageNum(), modelCategory.getPageSize());
        QueryWrapper<ModelCategory> wrapper = new QueryWrapper<ModelCategory>(modelCategory);
        return new PageInfo<ModelCategory>(iModelCategoryService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<ModelCategory> listAll() { 
        return iModelCategoryService.list();
    }
 
}
