package com.midea.cloud.srm.sup.riskraider.r8.controller;

import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.midea.cloud.srm.sup.riskraider.r8.service.IR8DiscreditMainCreditService;
import com.midea.cloud.srm.model.supplier.riskraider.r8.entity.R8DiscreditMainCredit;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  主体拖欠贷款列表 前端控制器
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
@RequestMapping("/sup/r8-discredit-main-credit")
public class R8DiscreditMainCreditController extends BaseController {

    @Autowired
    private IR8DiscreditMainCreditService iR8DiscreditMainCreditService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public R8DiscreditMainCredit get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iR8DiscreditMainCreditService.getById(id);
    }

    /**
    * 新增
    * @param r8DiscreditMainCredit
    */
    @PostMapping("/add")
    public void add(@RequestBody R8DiscreditMainCredit r8DiscreditMainCredit) {
        Long id = IdGenrator.generate();
        r8DiscreditMainCredit.setDiscreditMainCreditId(id);
        iR8DiscreditMainCreditService.save(r8DiscreditMainCredit);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iR8DiscreditMainCreditService.removeById(id);
    }

    /**
    * 修改
    * @param r8DiscreditMainCredit
    */
    @PostMapping("/modify")
    public void modify(@RequestBody R8DiscreditMainCredit r8DiscreditMainCredit) {
        iR8DiscreditMainCreditService.updateById(r8DiscreditMainCredit);
    }

    /**
    * 分页查询
    * @param r8DiscreditMainCredit
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<R8DiscreditMainCredit> listPage(@RequestBody R8DiscreditMainCredit r8DiscreditMainCredit) {
        PageUtil.startPage(r8DiscreditMainCredit.getPageNum(), r8DiscreditMainCredit.getPageSize());
        QueryWrapper<R8DiscreditMainCredit> wrapper = new QueryWrapper<R8DiscreditMainCredit>(r8DiscreditMainCredit);
        return new PageInfo<R8DiscreditMainCredit>(iR8DiscreditMainCreditService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<R8DiscreditMainCredit> listAll() { 
        return iR8DiscreditMainCreditService.list();
    }
 
}
