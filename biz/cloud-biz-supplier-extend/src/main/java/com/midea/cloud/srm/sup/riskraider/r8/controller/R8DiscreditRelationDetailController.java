package com.midea.cloud.srm.sup.riskraider.r8.controller;

import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.midea.cloud.srm.sup.riskraider.r8.service.IR8DiscreditRelationDetailService;
import com.midea.cloud.srm.model.supplier.riskraider.r8.entity.R8DiscreditRelationDetail;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  关联企业债务列表 前端控制器
 * </pre>
*
* @author chenwt24@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-01-18 10:46:05
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/sup/r8-discredit-relation-detail")
public class R8DiscreditRelationDetailController extends BaseController {

    @Autowired
    private IR8DiscreditRelationDetailService iR8DiscreditRelationDetailService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public R8DiscreditRelationDetail get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iR8DiscreditRelationDetailService.getById(id);
    }

    /**
    * 新增
    * @param r8DiscreditRelationDetail
    */
    @PostMapping("/add")
    public void add(@RequestBody R8DiscreditRelationDetail r8DiscreditRelationDetail) {
        Long id = IdGenrator.generate();
        r8DiscreditRelationDetail.setDiscreditRelationDetailId(id);
        iR8DiscreditRelationDetailService.save(r8DiscreditRelationDetail);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iR8DiscreditRelationDetailService.removeById(id);
    }

    /**
    * 修改
    * @param r8DiscreditRelationDetail
    */
    @PostMapping("/modify")
    public void modify(@RequestBody R8DiscreditRelationDetail r8DiscreditRelationDetail) {
        iR8DiscreditRelationDetailService.updateById(r8DiscreditRelationDetail);
    }

    /**
    * 分页查询
    * @param r8DiscreditRelationDetail
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<R8DiscreditRelationDetail> listPage(@RequestBody R8DiscreditRelationDetail r8DiscreditRelationDetail) {
        PageUtil.startPage(r8DiscreditRelationDetail.getPageNum(), r8DiscreditRelationDetail.getPageSize());
        QueryWrapper<R8DiscreditRelationDetail> wrapper = new QueryWrapper<R8DiscreditRelationDetail>(r8DiscreditRelationDetail);
        return new PageInfo<R8DiscreditRelationDetail>(iR8DiscreditRelationDetailService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<R8DiscreditRelationDetail> listAll() { 
        return iR8DiscreditRelationDetailService.list();
    }
 
}
