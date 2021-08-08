package com.midea.cloud.srm.sup.riskraider.r8.controller;

import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.midea.cloud.srm.sup.riskraider.r8.service.IR8DiscreditGuaranteeDetailService;
import com.midea.cloud.srm.model.supplier.riskraider.r8.entity.R8DiscreditGuaranteeDetail;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  对外担保列表 前端控制器
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
@RequestMapping("/sup/r8-discredit-guarantee-detail")
public class R8DiscreditGuaranteeDetailController extends BaseController {

    @Autowired
    private IR8DiscreditGuaranteeDetailService iR8DiscreditGuaranteeDetailService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public R8DiscreditGuaranteeDetail get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iR8DiscreditGuaranteeDetailService.getById(id);
    }

    /**
    * 新增
    * @param r8DiscreditGuaranteeDetail
    */
    @PostMapping("/add")
    public void add(@RequestBody R8DiscreditGuaranteeDetail r8DiscreditGuaranteeDetail) {
        Long id = IdGenrator.generate();
        r8DiscreditGuaranteeDetail.setDiscreditGuaranteeDetailId(id);
        iR8DiscreditGuaranteeDetailService.save(r8DiscreditGuaranteeDetail);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iR8DiscreditGuaranteeDetailService.removeById(id);
    }

    /**
    * 修改
    * @param r8DiscreditGuaranteeDetail
    */
    @PostMapping("/modify")
    public void modify(@RequestBody R8DiscreditGuaranteeDetail r8DiscreditGuaranteeDetail) {
        iR8DiscreditGuaranteeDetailService.updateById(r8DiscreditGuaranteeDetail);
    }

    /**
    * 分页查询
    * @param r8DiscreditGuaranteeDetail
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<R8DiscreditGuaranteeDetail> listPage(@RequestBody R8DiscreditGuaranteeDetail r8DiscreditGuaranteeDetail) {
        PageUtil.startPage(r8DiscreditGuaranteeDetail.getPageNum(), r8DiscreditGuaranteeDetail.getPageSize());
        QueryWrapper<R8DiscreditGuaranteeDetail> wrapper = new QueryWrapper<R8DiscreditGuaranteeDetail>(r8DiscreditGuaranteeDetail);
        return new PageInfo<R8DiscreditGuaranteeDetail>(iR8DiscreditGuaranteeDetailService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<R8DiscreditGuaranteeDetail> listAll() { 
        return iR8DiscreditGuaranteeDetailService.list();
    }
 
}
