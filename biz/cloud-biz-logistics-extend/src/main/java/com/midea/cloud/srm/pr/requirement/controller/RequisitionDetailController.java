package com.midea.cloud.srm.pr.requirement.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequisitionDetail;
import com.midea.cloud.srm.pr.requirement.service.IRequisitionDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  采购申请明细表（隆基采购申请明细同步） 前端控制器
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-01 10:27:30
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/organization/requisition-detail")
public class RequisitionDetailController extends BaseController {

    @Autowired
    private IRequisitionDetailService iRequisitionDetailService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public RequisitionDetail get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iRequisitionDetailService.getById(id);
    }

    /**
    * 新增
    * @param requisitionDetail
    */
    @PostMapping("/add")
    public void add(@RequestBody RequisitionDetail requisitionDetail) {
        Long id = IdGenrator.generate();
        requisitionDetail.setRequisitionLineId(id);
        iRequisitionDetailService.save(requisitionDetail);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iRequisitionDetailService.removeById(id);
    }

    /**
    * 修改
    * @param requisitionDetail
    */
    @PostMapping("/modify")
    public void modify(@RequestBody RequisitionDetail requisitionDetail) {
        iRequisitionDetailService.updateById(requisitionDetail);
    }

    /**
    * 分页查询
    * @param requisitionDetail
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<RequisitionDetail> listPage(@RequestBody RequisitionDetail requisitionDetail) {
        PageUtil.startPage(requisitionDetail.getPageNum(), requisitionDetail.getPageSize());
        QueryWrapper<RequisitionDetail> wrapper = new QueryWrapper<RequisitionDetail>(requisitionDetail);
        return new PageInfo<RequisitionDetail>(iRequisitionDetailService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<RequisitionDetail> listAll() { 
        return iRequisitionDetailService.list();
    }
 
}
