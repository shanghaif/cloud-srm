package com.midea.cloud.srm.price.model.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.price.model.entity.ModelElement;
import com.midea.cloud.srm.price.model.service.IModelElementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

;

/**
*  <pre>
 *  价格模型成本要素 前端控制器
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-07-25 14:27:17
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/model-element")
public class ModelElementController extends BaseController {

    @Autowired
    private IModelElementService iModelElementService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public ModelElement get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iModelElementService.getById(id);
    }

    /**
    * 新增
    * @param modelElement
    */
    @PostMapping("/add")
    public void add(@RequestBody ModelElement modelElement) {
        Long id = IdGenrator.generate();
        modelElement.setModelElementId(id);
        iModelElementService.save(modelElement);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iModelElementService.removeById(id);
    }

    /**
    * 修改
    * @param modelElement
    */
    @PostMapping("/modify")
    public void modify(@RequestBody ModelElement modelElement) {
        iModelElementService.updateById(modelElement);
    }

    /**
    * 分页查询
    * @param modelElement
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<ModelElement> listPage(@RequestBody ModelElement modelElement) {
        PageUtil.startPage(modelElement.getPageNum(), modelElement.getPageSize());
        QueryWrapper<ModelElement> wrapper = new QueryWrapper<ModelElement>(modelElement);
        return new PageInfo<ModelElement>(iModelElementService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<ModelElement> listAll() { 
        return iModelElementService.list();
    }
 
}
