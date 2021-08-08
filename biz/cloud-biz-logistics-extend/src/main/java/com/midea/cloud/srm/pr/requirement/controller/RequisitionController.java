package com.midea.cloud.srm.pr.requirement.controller;

import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.Requisition;
import com.midea.cloud.srm.pr.requirement.service.IRequisitionService;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  采购申请表（隆基采购申请同步） 前端控制器
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-01 09:45:43
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/organization/requisition")
public class RequisitionController extends BaseController {

    @Autowired
    private IRequisitionService iRequisitionService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public Requisition get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iRequisitionService.getById(id);
    }

    /**
    * 新增
    * @param requisition
    */
    @PostMapping("/add")
    public void add(@RequestBody Requisition requisition) {
        iRequisitionService.save(requisition);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iRequisitionService.removeById(id);
    }

    /**
    * 修改
    * @param requisition
    */
    @PostMapping("/modify")
    public void modify(@RequestBody Requisition requisition) {
        iRequisitionService.updateById(requisition);
    }

    /**
    * 分页查询
    * @param requisition
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<Requisition> listPage(@RequestBody Requisition requisition) {
        PageUtil.startPage(requisition.getPageNum(), requisition.getPageSize());
        QueryWrapper<Requisition> wrapper = new QueryWrapper<Requisition>(requisition);
        return new PageInfo<Requisition>(iRequisitionService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<Requisition> listAll() { 
        return iRequisitionService.list();
    }
 
}
