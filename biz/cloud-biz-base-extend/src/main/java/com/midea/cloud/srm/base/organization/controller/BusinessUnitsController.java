package com.midea.cloud.srm.base.organization.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.base.organization.service.IBusinessUnitsService;
import com.midea.cloud.srm.model.base.organization.entity.BusinessUnits;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  业务实体接口表 前端控制器
 * </pre>
*
* @author wuwl18@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-04 15:38:43
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/organization/businessUnits")
public class BusinessUnitsController extends BaseController {

    @Autowired
    private IBusinessUnitsService iBusinessUnitsService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public BusinessUnits get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iBusinessUnitsService.getById(id);
    }

    /**
    * 新增
    * @param businessUnits
    */
    @PostMapping("/add")
    public void add(@RequestBody BusinessUnits businessUnits) {
        Long id = IdGenrator.generate();
        businessUnits.setItfHeaderId(id);
        iBusinessUnitsService.save(businessUnits);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iBusinessUnitsService.removeById(id);
    }

    /**
    * 修改
    * @param businessUnits
    */
    @PostMapping("/modify")
    public void modify(@RequestBody BusinessUnits businessUnits) {
        iBusinessUnitsService.updateById(businessUnits);
    }

    /**
    * 分页查询
    * @param businessUnits
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<BusinessUnits> listPage(@RequestBody BusinessUnits businessUnits) {
        PageUtil.startPage(businessUnits.getPageNum(), businessUnits.getPageSize());
        QueryWrapper<BusinessUnits> wrapper = new QueryWrapper<BusinessUnits>(businessUnits);
        return new PageInfo<BusinessUnits>(iBusinessUnitsService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<BusinessUnits> listAll() { 
        return iBusinessUnitsService.list();
    }
 
}
