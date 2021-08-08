package com.midea.cloud.srm.sup.riskraider.r8.controller;

import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.midea.cloud.srm.sup.riskraider.r8.service.IR8DiscreditMainDisexecutorService;
import com.midea.cloud.srm.model.supplier.riskraider.r8.entity.R8DiscreditMainDisexecutor;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  主体失信被执行人列表 前端控制器
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
@RequestMapping("/sup/r8-discredit-main-disexecutor")
public class R8DiscreditMainDisexecutorController extends BaseController {

    @Autowired
    private IR8DiscreditMainDisexecutorService iR8DiscreditMainDisexecutorService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public R8DiscreditMainDisexecutor get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iR8DiscreditMainDisexecutorService.getById(id);
    }

    /**
    * 新增
    * @param r8DiscreditMainDisexecutor
    */
    @PostMapping("/add")
    public void add(@RequestBody R8DiscreditMainDisexecutor r8DiscreditMainDisexecutor) {
        Long id = IdGenrator.generate();
        r8DiscreditMainDisexecutor.setDiscreditMainDisexecutorId(id);
        iR8DiscreditMainDisexecutorService.save(r8DiscreditMainDisexecutor);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iR8DiscreditMainDisexecutorService.removeById(id);
    }

    /**
    * 修改
    * @param r8DiscreditMainDisexecutor
    */
    @PostMapping("/modify")
    public void modify(@RequestBody R8DiscreditMainDisexecutor r8DiscreditMainDisexecutor) {
        iR8DiscreditMainDisexecutorService.updateById(r8DiscreditMainDisexecutor);
    }

    /**
    * 分页查询
    * @param r8DiscreditMainDisexecutor
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<R8DiscreditMainDisexecutor> listPage(@RequestBody R8DiscreditMainDisexecutor r8DiscreditMainDisexecutor) {
        PageUtil.startPage(r8DiscreditMainDisexecutor.getPageNum(), r8DiscreditMainDisexecutor.getPageSize());
        QueryWrapper<R8DiscreditMainDisexecutor> wrapper = new QueryWrapper<R8DiscreditMainDisexecutor>(r8DiscreditMainDisexecutor);
        return new PageInfo<R8DiscreditMainDisexecutor>(iR8DiscreditMainDisexecutorService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<R8DiscreditMainDisexecutor> listAll() { 
        return iR8DiscreditMainDisexecutorService.list();
    }
 
}
