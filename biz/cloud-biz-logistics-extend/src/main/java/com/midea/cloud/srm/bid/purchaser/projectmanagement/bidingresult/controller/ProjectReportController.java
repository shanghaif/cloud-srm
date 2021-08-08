package com.midea.cloud.srm.bid.purchaser.projectmanagement.bidingresult.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidingresult.service.IProjectReportService;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidingresult.entity.ProjectReport;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidingresult.vo.ProjectReportVO;
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <pre>
 *  结项报告基础信息表 前端控制器
 * </pre>
 *
 * @author fengdc3@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-04-11 15:01:54
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/bidingResult/projectReport")
public class ProjectReportController extends BaseController {

    @Autowired
    private IProjectReportService iProjectReportService;

    /**
     * 获取信息
     *
     * @param bidingId
     */
    @GetMapping("/getProjectReport")
    public ProjectReport getProjectReport(Long bidingId) {
        return iProjectReportService.getProjectReport(bidingId);
    }

    /**
     * 新增
     *
     * @param projectReportVO
     */
    @PostMapping("/add")
    public void add(@RequestBody ProjectReportVO projectReportVO) {
        iProjectReportService.saveProjectReportAndFile(projectReportVO);
    }

    /**
     * 删除
     *
     * @param id
     */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iProjectReportService.removeById(id);
    }

    /**
     * 修改
     *
     * @param projectReport
     */
    @PostMapping("/modify")
    public void modify(@RequestBody ProjectReport projectReport) {
        iProjectReportService.updateById(projectReport);
    }

    /**
     * 分页查询
     *
     * @param projectReport
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<ProjectReport> listPage(@RequestBody ProjectReport projectReport) {
        PageUtil.startPage(projectReport.getPageNum(), projectReport.getPageSize());
        QueryWrapper<ProjectReport> wrapper = new QueryWrapper<ProjectReport>(projectReport);
        return new PageInfo<ProjectReport>(iProjectReportService.list(wrapper));
    }

    /**
     * 查询所有
     *
     * @return
     */
    @PostMapping("/listAll")
    public List<ProjectReport> listAll() {
        return iProjectReportService.list();
    }

}
