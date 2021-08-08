package com.midea.cloud.srm.sup.riskraider.r7.controller;

import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.supplier.info.entity.CompanyInfo;
import org.springframework.beans.factory.annotation.Autowired;
import com.midea.cloud.srm.sup.riskraider.r7.service.IR7FinancialService;
import com.midea.cloud.srm.model.supplier.riskraider.r7.entity.R7Financial;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  企业财务信息表 前端控制器
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
@RequestMapping("/sup/r7-financial")
public class R7FinancialController extends BaseController {

    @Autowired
    private IR7FinancialService iR7FinancialService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public R7Financial get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iR7FinancialService.getById(id);
    }

    /**
    * 新增
    * @param r7Financial
    */
    @PostMapping("/add")
    public void add(@RequestBody R7Financial r7Financial) {
        Long id = IdGenrator.generate();
        r7Financial.setFinancialId(id);
        iR7FinancialService.save(r7Financial);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iR7FinancialService.removeById(id);
    }

    /**
    * 修改
    * @param r7Financial
    */
    @PostMapping("/modify")
    public void modify(@RequestBody R7Financial r7Financial) {
        iR7FinancialService.updateById(r7Financial);
    }

    /**
    * 分页查询
    * @param r7Financial
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<R7Financial> listPage(@RequestBody R7Financial r7Financial) {
        PageUtil.startPage(r7Financial.getPageNum(), r7Financial.getPageSize());
        QueryWrapper<R7Financial> wrapper = new QueryWrapper<R7Financial>(r7Financial);
        return new PageInfo<R7Financial>(iR7FinancialService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<R7Financial> listAll() {
        return iR7FinancialService.list();
    }

    /**
     * 查询所有
     * @return
     */
    @PostMapping("/addFinancial")
    public void addFinancial(@RequestBody CompanyInfo companyInfo) {
        iR7FinancialService.saveOrUpdateR7FromRaider(companyInfo);
    }

    /**
     * 查询所有
     * @return
     */
    @PostMapping("/saveR7ToEs")
    public void saveR7ToEs(@RequestBody CompanyInfo companyInfo) throws Exception {
        iR7FinancialService.saveR7ToEs(companyInfo);
    }
 
}
