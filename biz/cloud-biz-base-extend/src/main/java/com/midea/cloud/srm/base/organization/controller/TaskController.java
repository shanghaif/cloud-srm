package com.midea.cloud.srm.base.organization.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.base.organization.service.ITaskService;
import com.midea.cloud.srm.model.base.organization.entity.Task;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  项目任务信息行表（隆基项目任务信息行同步） 前端控制器
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-03 17:35:16
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/organization/task")
public class TaskController extends BaseController {

    @Autowired
    private ITaskService iTaskService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public Task get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iTaskService.getById(id);
    }

    /**
    * 新增
    * @param task
    */
    @PostMapping("/add")
    public void add(@RequestBody Task task) {
        Long id = IdGenrator.generate();
        task.setId(id);
        iTaskService.save(task);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iTaskService.removeById(id);
    }

    /**
    * 修改
    * @param task
    */
    @PostMapping("/modify")
    public void modify(@RequestBody Task task) {
        iTaskService.updateById(task);
    }

    /**
    * 分页查询
    * @param task
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<Task> listPage(@RequestBody Task task) {
        PageUtil.startPage(task.getPageNum(), task.getPageSize());
        QueryWrapper<Task> wrapper = new QueryWrapper<Task>(task);
        return new PageInfo<Task>(iTaskService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<Task> listAll() { 
        return iTaskService.list();
    }
 
}
