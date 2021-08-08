package com.midea.cloud.srm.base.purchase.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.base.purchase.service.ICategoryPurchaseTypeService;
import com.midea.cloud.srm.model.base.purchase.dto.CategoryPurchaseTypeDTO;
import com.midea.cloud.srm.model.base.purchase.entity.CategoryPurchaseType;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  物料小类对应采购类型 前端控制器
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-10-26 14:15:53
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/purchase/category-purchase-type")
public class CategoryPurchaseTypeController extends BaseController {

    @Autowired
    private ICategoryPurchaseTypeService iCategoryPurchaseTypeService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public CategoryPurchaseType get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iCategoryPurchaseTypeService.getById(id);
    }

    /**
    * 新增
    * @param categoryPurchaseType
    */
    @PostMapping("/add")
    public void add(@RequestBody CategoryPurchaseType categoryPurchaseType) {
        Long id = IdGenrator.generate();
        categoryPurchaseType.setId(id);
        iCategoryPurchaseTypeService.save(categoryPurchaseType);
    }

    /**
     * 查询这条小类对应的采购类型
     */
    @GetMapping("/listPurchaseTypes")
    public List<CategoryPurchaseType> listPurchaseTypes(@RequestParam("categoryId") Long categoryId){
        return iCategoryPurchaseTypeService.listPurchaseTypes(categoryId);
    }

    /**
     * 保存或更新 物料小类采购类型对应关系
     */
    @PostMapping("/saveOrUpdateCategoryPurchaseTypes")
    public void saveOrUpdateCategoryPurchaseTypes(@RequestBody CategoryPurchaseTypeDTO dto) {
        iCategoryPurchaseTypeService.saveOrUpdateCategoryPurchaseTypes(dto);
    }

    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iCategoryPurchaseTypeService.removeById(id);
    }

    /**
    * 修改
    * @param categoryPurchaseType
    */
    @PostMapping("/modify")
    public void modify(@RequestBody CategoryPurchaseType categoryPurchaseType) {
        iCategoryPurchaseTypeService.updateById(categoryPurchaseType);
    }

    /**
    * 分页查询
    * @param categoryPurchaseType
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<CategoryPurchaseType> listPage(@RequestBody CategoryPurchaseType categoryPurchaseType) {
        PageUtil.startPage(categoryPurchaseType.getPageNum(), categoryPurchaseType.getPageSize());
        QueryWrapper<CategoryPurchaseType> wrapper = new QueryWrapper<CategoryPurchaseType>(categoryPurchaseType);
        return new PageInfo<CategoryPurchaseType>(iCategoryPurchaseTypeService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<CategoryPurchaseType> listAll() { 
        return iCategoryPurchaseTypeService.list();
    }
 
}
