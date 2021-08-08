package com.midea.cloud.srm.sup.riskraider.r8.controller;

import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.midea.cloud.srm.sup.riskraider.r8.service.IR8DiscreditMainLoanService;
import com.midea.cloud.srm.model.supplier.riskraider.r8.entity.R8DiscreditMainLoan;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  主体拖欠借款列表 前端控制器
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
@RequestMapping("/sup/r8-discredit-main-loan")
public class R8DiscreditMainLoanController extends BaseController {

    @Autowired
    private IR8DiscreditMainLoanService iR8DiscreditMainLoanService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public R8DiscreditMainLoan get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iR8DiscreditMainLoanService.getById(id);
    }

    /**
    * 新增
    * @param r8DiscreditMainLoan
    */
    @PostMapping("/add")
    public void add(@RequestBody R8DiscreditMainLoan r8DiscreditMainLoan) {
        Long id = IdGenrator.generate();
        r8DiscreditMainLoan.setDiscreditMainLoanId(id);
        iR8DiscreditMainLoanService.save(r8DiscreditMainLoan);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iR8DiscreditMainLoanService.removeById(id);
    }

    /**
    * 修改
    * @param r8DiscreditMainLoan
    */
    @PostMapping("/modify")
    public void modify(@RequestBody R8DiscreditMainLoan r8DiscreditMainLoan) {
        iR8DiscreditMainLoanService.updateById(r8DiscreditMainLoan);
    }

    /**
    * 分页查询
    * @param r8DiscreditMainLoan
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<R8DiscreditMainLoan> listPage(@RequestBody R8DiscreditMainLoan r8DiscreditMainLoan) {
        PageUtil.startPage(r8DiscreditMainLoan.getPageNum(), r8DiscreditMainLoan.getPageSize());
        QueryWrapper<R8DiscreditMainLoan> wrapper = new QueryWrapper<R8DiscreditMainLoan>(r8DiscreditMainLoan);
        return new PageInfo<R8DiscreditMainLoan>(iR8DiscreditMainLoanService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<R8DiscreditMainLoan> listAll() { 
        return iR8DiscreditMainLoanService.list();
    }
 
}
