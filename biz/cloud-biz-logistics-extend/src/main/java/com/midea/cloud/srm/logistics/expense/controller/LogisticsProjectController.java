package com.midea.cloud.srm.logistics.expense.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.logistics.expense.service.ILogisticsProjectService;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.logistics.expense.entity.LogisticsProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  服务项目表 前端控制器
 * </pre>
*
* @author xiexh12@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-11-27 14:47:59
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/logistics/logistics-project")
public class LogisticsProjectController extends BaseController {

    @Autowired
    private ILogisticsProjectService iLogisticsProjectService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public LogisticsProject get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iLogisticsProjectService.getById(id);
    }

    /**
    * 新增
    * @param logisticsProject
    */
    @PostMapping("/add")
    public void add(@RequestBody LogisticsProject logisticsProject) {
        Long id = IdGenrator.generate();
        logisticsProject.setProjectId(id);
        iLogisticsProjectService.save(logisticsProject);
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iLogisticsProjectService.removeById(id);
    }

    /**
    * 修改
    * @param logisticsProject
    */
    @PostMapping("/modify")
    public void modify(@RequestBody LogisticsProject logisticsProject) {
        iLogisticsProjectService.updateById(logisticsProject);
    }

    /**
    * 分页查询
    * @param logisticsProject
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<LogisticsProject> listPage(@RequestBody LogisticsProject logisticsProject) {
        PageUtil.startPage(logisticsProject.getPageNum(), logisticsProject.getPageSize());
        QueryWrapper<LogisticsProject> wrapper = new QueryWrapper<LogisticsProject>(logisticsProject);
        return new PageInfo<LogisticsProject>(iLogisticsProjectService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<LogisticsProject> listAll() { 
        return iLogisticsProjectService.list();
    }
 
}
