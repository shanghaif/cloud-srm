package com.midea.cloud.srm.sup.riskraider.r8.controller;

import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.midea.cloud.srm.sup.riskraider.r8.service.IR8DiscreditCustomService;
import com.midea.cloud.srm.model.supplier.riskraider.r8.entity.R8DiscreditCustom;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  关联企业失信信息表 前端控制器
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
@RequestMapping("/sup/r8-discredit-custom")
public class R8DiscreditCustomController extends BaseController {

    @Autowired
    private IR8DiscreditCustomService iR8DiscreditCustomService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public R8DiscreditCustom get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iR8DiscreditCustomService.getById(id);
    }

    /**
    * 新增
    * @param r8DiscreditCustom
    */
    @PostMapping("/add")
    public void add(@RequestBody R8DiscreditCustom r8DiscreditCustom) {
        Long id = IdGenrator.generate();
        r8DiscreditCustom.setDiscreditCustomId(id);
        iR8DiscreditCustomService.save(r8DiscreditCustom);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iR8DiscreditCustomService.removeById(id);
    }

    /**
    * 修改
    * @param r8DiscreditCustom
    */
    @PostMapping("/modify")
    public void modify(@RequestBody R8DiscreditCustom r8DiscreditCustom) {
        iR8DiscreditCustomService.updateById(r8DiscreditCustom);
    }

    /**
    * 分页查询
    * @param r8DiscreditCustom
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<R8DiscreditCustom> listPage(@RequestBody R8DiscreditCustom r8DiscreditCustom) {
        PageUtil.startPage(r8DiscreditCustom.getPageNum(), r8DiscreditCustom.getPageSize());
        QueryWrapper<R8DiscreditCustom> wrapper = new QueryWrapper<R8DiscreditCustom>(r8DiscreditCustom);
        return new PageInfo<R8DiscreditCustom>(iR8DiscreditCustomService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<R8DiscreditCustom> listAll() { 
        return iR8DiscreditCustomService.list();
    }
 
}
