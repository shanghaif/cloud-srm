package com.midea.cloud.srm.sup.riskraider.r8.controller;

import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.midea.cloud.srm.sup.riskraider.r8.service.IR8DiscreditMainExecutorService;
import com.midea.cloud.srm.model.supplier.riskraider.r8.entity.R8DiscreditMainExecutor;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  主体被执行人列表 前端控制器
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
@RequestMapping("/sup/r8-discredit-main-executor")
public class R8DiscreditMainExecutorController extends BaseController {

    @Autowired
    private IR8DiscreditMainExecutorService iR8DiscreditMainExecutorService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public R8DiscreditMainExecutor get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iR8DiscreditMainExecutorService.getById(id);
    }

    /**
    * 新增
    * @param r8DiscreditMainExecutor
    */
    @PostMapping("/add")
    public void add(@RequestBody R8DiscreditMainExecutor r8DiscreditMainExecutor) {
        Long id = IdGenrator.generate();
        r8DiscreditMainExecutor.setDiscreditMainExecutorId(id);
        iR8DiscreditMainExecutorService.save(r8DiscreditMainExecutor);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iR8DiscreditMainExecutorService.removeById(id);
    }

    /**
    * 修改
    * @param r8DiscreditMainExecutor
    */
    @PostMapping("/modify")
    public void modify(@RequestBody R8DiscreditMainExecutor r8DiscreditMainExecutor) {
        iR8DiscreditMainExecutorService.updateById(r8DiscreditMainExecutor);
    }

    /**
    * 分页查询
    * @param r8DiscreditMainExecutor
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<R8DiscreditMainExecutor> listPage(@RequestBody R8DiscreditMainExecutor r8DiscreditMainExecutor) {
        PageUtil.startPage(r8DiscreditMainExecutor.getPageNum(), r8DiscreditMainExecutor.getPageSize());
        QueryWrapper<R8DiscreditMainExecutor> wrapper = new QueryWrapper<R8DiscreditMainExecutor>(r8DiscreditMainExecutor);
        return new PageInfo<R8DiscreditMainExecutor>(iR8DiscreditMainExecutorService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<R8DiscreditMainExecutor> listAll() { 
        return iR8DiscreditMainExecutorService.list();
    }
 
}
