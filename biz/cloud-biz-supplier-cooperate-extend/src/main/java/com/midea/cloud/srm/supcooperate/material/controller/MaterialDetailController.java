package com.midea.cloud.srm.supcooperate.material.controller;

import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.midea.cloud.srm.supcooperate.material.service.IMaterialDetailService;
import com.midea.cloud.srm.model.suppliercooperate.material.entity.CeeaMaterialDetail;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  物料计划明细表 前端控制器
 * </pre>
*
* @author yourname@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-21 23:38:17
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/material/materialDetail")
public class MaterialDetailController extends BaseController {

    @Autowired
    private IMaterialDetailService iMaterialDetailService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public CeeaMaterialDetail get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iMaterialDetailService.getById(id);
    }

    /**
    * 新增
    * @param materialDetail
    */
    @PostMapping("/add")
    public void add(@RequestBody CeeaMaterialDetail materialDetail) {
        Long id = IdGenrator.generate();
        materialDetail.setId(id);
        iMaterialDetailService.save(materialDetail);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iMaterialDetailService.removeById(id);
    }

    /**
    * 修改
    * @param materialDetail
    */
    @PostMapping("/modify")
    public void modify(@RequestBody CeeaMaterialDetail materialDetail) {
        iMaterialDetailService.updateBycount(materialDetail);
    }

    /**
    * 分页查询
    * @param materialDetail
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<CeeaMaterialDetail> listPage(@RequestBody CeeaMaterialDetail materialDetail) {
        PageUtil.startPage(materialDetail.getPageNum(), materialDetail.getPageSize());
        QueryWrapper<CeeaMaterialDetail> wrapper = new QueryWrapper<CeeaMaterialDetail>(materialDetail);
        return new PageInfo<CeeaMaterialDetail>(iMaterialDetailService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<CeeaMaterialDetail> listAll() {
        return iMaterialDetailService.list();
    }
 
}
