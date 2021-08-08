package com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidingresult.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.bargaining.purchaser.projectmanagement.bidingresult.service.IProjectReportFileService;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidingresult.entity.ProjectReportFile;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
*  <pre>
 *  结项报告附件表 前端控制器
 * </pre>
*
* @author fengdc3@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-04-11 15:01:54
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/bidingResult/projectReportFile")
public class ProjectReportFileController extends BaseController {

    @Autowired
    private IProjectReportFileService iProjectReportFileService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public ProjectReportFile get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iProjectReportFileService.getById(id);
    }

    /**
    * 新增
    * @param projectReportFile
    */
    @PostMapping("/add")
    public void add(@RequestBody ProjectReportFile projectReportFile) {
        Long id = IdGenrator.generate();
        projectReportFile.setProjectReportFileId(id);
        iProjectReportFileService.save(projectReportFile);
    }

    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iProjectReportFileService.removeById(id);
    }

    /**
    * 修改
    * @param projectReportFile
    */
    @PostMapping("/modify")
    public void modify(@RequestBody ProjectReportFile projectReportFile) {
        iProjectReportFileService.updateById(projectReportFile);
    }

    /**
    * 分页查询
    * @param projectReportFile
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<ProjectReportFile> listPage(@RequestBody ProjectReportFile projectReportFile) {
        PageUtil.startPage(projectReportFile.getPageNum(), projectReportFile.getPageSize());
        QueryWrapper<ProjectReportFile> wrapper = new QueryWrapper<ProjectReportFile>(projectReportFile);
        return new PageInfo<ProjectReportFile>(iProjectReportFileService.list(wrapper));
    }

}
