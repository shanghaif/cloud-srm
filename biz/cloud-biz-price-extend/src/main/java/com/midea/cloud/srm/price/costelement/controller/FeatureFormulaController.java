package com.midea.cloud.srm.price.costelement.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.price.costelement.entity.FeatureFormula;
import com.midea.cloud.srm.price.costelement.service.IFeatureFormulaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  要素公式表 前端控制器
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
@RequestMapping("/feature-formula")
public class FeatureFormulaController extends BaseController {

    @Autowired
    private IFeatureFormulaService iFeatureFormulaService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public FeatureFormula get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iFeatureFormulaService.getById(id);
    }

    /**
    * 新增
    * @param featureFormula
    */
    @PostMapping("/add")
    public void add(@RequestBody FeatureFormula featureFormula) {
        Long id = IdGenrator.generate();
        featureFormula.setFeatureFormulaId(id);
        iFeatureFormulaService.save(featureFormula);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iFeatureFormulaService.removeById(id);
    }

    /**
    * 修改
    * @param featureFormula
    */
    @PostMapping("/modify")
    public void modify(@RequestBody FeatureFormula featureFormula) {
        iFeatureFormulaService.updateById(featureFormula);
    }

    /**
    * 分页查询
    * @param featureFormula
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<FeatureFormula> listPage(@RequestBody FeatureFormula featureFormula) {
        PageUtil.startPage(featureFormula.getPageNum(), featureFormula.getPageSize());
        QueryWrapper<FeatureFormula> wrapper = new QueryWrapper<FeatureFormula>(featureFormula);
        return new PageInfo<FeatureFormula>(iFeatureFormulaService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<FeatureFormula> listAll() { 
        return iFeatureFormulaService.list();
    }
 
}
