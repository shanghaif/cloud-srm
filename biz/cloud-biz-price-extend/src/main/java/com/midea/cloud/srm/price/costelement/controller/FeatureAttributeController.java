package com.midea.cloud.srm.price.costelement.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.price.costelement.entity.FeatureAttribute;
import com.midea.cloud.srm.price.costelement.service.IFeatureAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  要素属性表 前端控制器
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-07-25 13:58:20
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/feature-attribute")
public class FeatureAttributeController extends BaseController {

    @Autowired
    private IFeatureAttributeService iFeatureAttributeService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public FeatureAttribute get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iFeatureAttributeService.getById(id);
    }

    /**
    * 新增
    * @param featureAttribute
    */
    @PostMapping("/add")
    public void add(@RequestBody FeatureAttribute featureAttribute) {
        Long id = IdGenrator.generate();
        featureAttribute.setFeatureAttributeId(id);
        iFeatureAttributeService.save(featureAttribute);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iFeatureAttributeService.removeById(id);
    }

    /**
    * 修改
    * @param featureAttribute
    */
    @PostMapping("/modify")
    public void modify(@RequestBody FeatureAttribute featureAttribute) {
        iFeatureAttributeService.updateById(featureAttribute);
    }

    /**
    * 分页查询
    * @param featureAttribute
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<FeatureAttribute> listPage(@RequestBody FeatureAttribute featureAttribute) {
        PageUtil.startPage(featureAttribute.getPageNum(), featureAttribute.getPageSize());
        QueryWrapper<FeatureAttribute> wrapper = new QueryWrapper<FeatureAttribute>(featureAttribute);
        return new PageInfo<FeatureAttribute>(iFeatureAttributeService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<FeatureAttribute> listAll() { 
        return iFeatureAttributeService.list();
    }
 
}
