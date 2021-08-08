package com.midea.cloud.srm.base.test.materialImport.controller;

import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.base.test.materialImport.service.IMaterialTestService;
import com.midea.cloud.srm.model.base.test.MaterialTest;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  物料长宽高属性配置表（导测试数据用） 前端控制器
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-26 13:57:30
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/organization/material-test")
public class MaterialTestController extends BaseController {

    @Autowired
    private IMaterialTestService iMaterialTestService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public MaterialTest get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iMaterialTestService.getById(id);
    }

    /**
    * 新增
    * @param materialTest
    */
    @PostMapping("/add")
    public void add(@RequestBody MaterialTest materialTest) {
        Long id = IdGenrator.generate();
        materialTest.setId(id);
        iMaterialTestService.save(materialTest);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iMaterialTestService.removeById(id);
    }

    /**
    * 修改
    * @param materialTest
    */
    @PostMapping("/modify")
    public void modify(@RequestBody MaterialTest materialTest) {
        iMaterialTestService.updateById(materialTest);
    }

    /**
    * 分页查询
    * @param materialTest
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<MaterialTest> listPage(@RequestBody MaterialTest materialTest) {
        PageUtil.startPage(materialTest.getPageNum(), materialTest.getPageSize());
        QueryWrapper<MaterialTest> wrapper = new QueryWrapper<MaterialTest>(materialTest);
        return new PageInfo<MaterialTest>(iMaterialTestService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<MaterialTest> listAll() { 
        return iMaterialTestService.list();
    }

    @PostMapping("/importMaterialTestData")
    public void importData() {
        iMaterialTestService.importData();
    }

    @PostMapping("/createMaterialAttribute")
    public void createMaterialAttribute() {
        iMaterialTestService.createMaterialAttribute();
    }
 
}
