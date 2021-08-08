package com.midea.cloud.srm.sup.riskraider.r7.controller;

import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.midea.cloud.srm.sup.riskraider.r7.service.IR7FinancialDetailService;
import com.midea.cloud.srm.model.supplier.riskraider.r7.entity.R7FinancialDetail;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  企业财务明细表 前端控制器
 * </pre>
*
* @author chenwt24@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-01-18 10:42:20
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/sup/r7-financial-detail")
public class R7FinancialDetailController extends BaseController {

    @Autowired
    private IR7FinancialDetailService iR7FinancialDetailService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public R7FinancialDetail get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iR7FinancialDetailService.getById(id);
    }

    /**
    * 新增
    * @param r7FinancialDetail
    */
    @PostMapping("/add")
    public void add(@RequestBody R7FinancialDetail r7FinancialDetail) {
        Long id = IdGenrator.generate();
        r7FinancialDetail.setFinancialDetailId(id);
        iR7FinancialDetailService.save(r7FinancialDetail);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iR7FinancialDetailService.removeById(id);
    }

    /**
    * 修改
    * @param r7FinancialDetail
    */
    @PostMapping("/modify")
    public void modify(@RequestBody R7FinancialDetail r7FinancialDetail) {
        iR7FinancialDetailService.updateById(r7FinancialDetail);
    }

    /**
    * 分页查询
    * @param r7FinancialDetail
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<R7FinancialDetail> listPage(@RequestBody R7FinancialDetail r7FinancialDetail) {
        PageUtil.startPage(r7FinancialDetail.getPageNum(), r7FinancialDetail.getPageSize());
        QueryWrapper<R7FinancialDetail> wrapper = new QueryWrapper<R7FinancialDetail>(r7FinancialDetail);
        return new PageInfo<R7FinancialDetail>(iR7FinancialDetailService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<R7FinancialDetail> listAll() { 
        return iR7FinancialDetailService.list();
    }
 
}
