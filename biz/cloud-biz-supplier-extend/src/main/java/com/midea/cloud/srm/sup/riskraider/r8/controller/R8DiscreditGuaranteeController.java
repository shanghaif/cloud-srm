package com.midea.cloud.srm.sup.riskraider.r8.controller;

import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.midea.cloud.srm.sup.riskraider.r8.service.IR8DiscreditGuaranteeService;
import com.midea.cloud.srm.model.supplier.riskraider.r8.entity.R8DiscreditGuarantee;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  主体企业失信信息表 前端控制器
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
@RequestMapping("/sup/r8-discredit-guarantee")
public class R8DiscreditGuaranteeController extends BaseController {

    @Autowired
    private IR8DiscreditGuaranteeService iR8DiscreditGuaranteeService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public R8DiscreditGuarantee get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iR8DiscreditGuaranteeService.getById(id);
    }

    /**
    * 新增
    * @param r8DiscreditGuarantee
    */
    @PostMapping("/add")
    public void add(@RequestBody R8DiscreditGuarantee r8DiscreditGuarantee) {
        Long id = IdGenrator.generate();
        r8DiscreditGuarantee.setDiscreditGuaranteeId(id);
        iR8DiscreditGuaranteeService.save(r8DiscreditGuarantee);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iR8DiscreditGuaranteeService.removeById(id);
    }

    /**
    * 修改
    * @param r8DiscreditGuarantee
    */
    @PostMapping("/modify")
    public void modify(@RequestBody R8DiscreditGuarantee r8DiscreditGuarantee) {
        iR8DiscreditGuaranteeService.updateById(r8DiscreditGuarantee);
    }

    /**
    * 分页查询
    * @param r8DiscreditGuarantee
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<R8DiscreditGuarantee> listPage(@RequestBody R8DiscreditGuarantee r8DiscreditGuarantee) {
        PageUtil.startPage(r8DiscreditGuarantee.getPageNum(), r8DiscreditGuarantee.getPageSize());
        QueryWrapper<R8DiscreditGuarantee> wrapper = new QueryWrapper<R8DiscreditGuarantee>(r8DiscreditGuarantee);
        return new PageInfo<R8DiscreditGuarantee>(iR8DiscreditGuaranteeService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<R8DiscreditGuarantee> listAll() { 
        return iR8DiscreditGuaranteeService.list();
    }
 
}
