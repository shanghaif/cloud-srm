package com.midea.cloud.srm.base.organization.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.base.organization.service.IProjectService;
import com.midea.cloud.srm.model.base.organization.entity.Project;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  项目任务信息头表（隆基项目任务信息头同步） 前端控制器
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-03 17:34:31
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/organization/project")
public class ProjectController extends BaseController {

    @Autowired
    private IProjectService iProjectService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public Project get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iProjectService.getById(id);
    }

    /**
    * 新增
    * @param project
    */
    @PostMapping("/add")
    public void add(@RequestBody Project project) {
        Long id = IdGenrator.generate();
        project.setId(id);
        iProjectService.save(project);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iProjectService.removeById(id);
    }

    /**
    * 修改
    * @param project
    */
    @PostMapping("/modify")
    public void modify(@RequestBody Project project) {
        iProjectService.updateById(project);
    }

    /**
    * 分页查询
    * @param project
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<Project> listPage(@RequestBody Project project) {
        PageUtil.startPage(project.getPageNum(), project.getPageSize());
        QueryWrapper<Project> wrapper = new QueryWrapper<Project>(project);
        return new PageInfo<Project>(iProjectService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<Project> listAll() { 
        return iProjectService.list();
    }
 
}
