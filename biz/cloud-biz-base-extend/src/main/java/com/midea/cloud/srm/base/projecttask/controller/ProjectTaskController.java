package com.midea.cloud.srm.base.projecttask.controller;

import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.midea.cloud.srm.base.projecttask.service.IProjectTaskService;
import com.midea.cloud.srm.model.base.projecttask.entity.ProjectTask;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
*  <pre>
 *  项目任务配置表 前端控制器
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-12-06 17:57:38
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/projecttask/project-task")
public class ProjectTaskController extends BaseController {

    @Autowired
    private IProjectTaskService iProjectTaskService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public ProjectTask get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iProjectTaskService.getById(id);
    }

    /**
    * 新增
    * @param projectTask
    */
    @PostMapping("/add")
    public void add(@RequestBody ProjectTask projectTask) {
        Long id = IdGenrator.generate();
        projectTask.setProjectTaskId(id);
        iProjectTaskService.save(projectTask);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iProjectTaskService.removeById(id);
    }

    /**
    * 修改
    * @param projectTask
    */
    @PostMapping("/modify")
    public void modify(@RequestBody ProjectTask projectTask) {
        iProjectTaskService.updateById(projectTask);
    }

    /**
    * 分页查询
    * @param projectTask
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<ProjectTask> listPage(@RequestBody ProjectTask projectTask) {
        PageUtil.startPage(projectTask.getPageNum(), projectTask.getPageSize());
        QueryWrapper<ProjectTask> wrapper = new QueryWrapper<ProjectTask>(projectTask);
        return new PageInfo<ProjectTask>(iProjectTaskService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<ProjectTask> listAll() { 
        return iProjectTaskService.list();
    }
 
}
