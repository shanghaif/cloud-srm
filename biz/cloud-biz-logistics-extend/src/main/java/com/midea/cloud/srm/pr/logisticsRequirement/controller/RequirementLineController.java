package com.midea.cloud.srm.pr.logisticsRequirement.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.logistics.pr.requirement.entity.LogisticsRequirementLine;
import com.midea.cloud.srm.pr.logisticsRequirement.service.IRequirementLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
*  <pre>
 *  物流采购需求行表 前端控制器
 * </pre>
*
* @author chenwt24@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-11-27 10:59:47
 *  修改内容:
 * </pre>
*/
@RestController(value = "LogisticsRequirementLineController")
@RequestMapping("/pr/requirement-line")
public class RequirementLineController extends BaseController {

    @Autowired
    private IRequirementLineService iRequirementLineService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public LogisticsRequirementLine get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iRequirementLineService.getById(id);
    }

    /**
    * 新增
    * @param requirementLine
    */
    @PostMapping("/add")
    public void add(@RequestBody LogisticsRequirementLine requirementLine) {
        Long id = IdGenrator.generate();
        requirementLine.setRequirementLineId(id);
        iRequirementLineService.save(requirementLine);
    }

    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iRequirementLineService.removeById(id);
    }

    /**
    * 修改
    * @param requirementLine
    */
    @PostMapping("/modify")
    public void modify(@RequestBody LogisticsRequirementLine requirementLine) {
        iRequirementLineService.updateById(requirementLine);
    }

    /**
    * 分页查询
    * @param requirementLine
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<LogisticsRequirementLine> listPage(@RequestBody LogisticsRequirementLine requirementLine) {
        PageUtil.startPage(requirementLine.getPageNum(), requirementLine.getPageSize());
        QueryWrapper<LogisticsRequirementLine> wrapper = new QueryWrapper<LogisticsRequirementLine>(requirementLine);
        return new PageInfo<LogisticsRequirementLine>(iRequirementLineService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<LogisticsRequirementLine> listAll() {
        return iRequirementLineService.list();
    }


    /**
     * 采购需求-物料明细-导入模板下载
     * @param response
     * @throws IOException
     */
    @RequestMapping("/importRequirementLineModelDownload")
    public void importModelDownload(HttpServletResponse response,@RequestParam Long templateHeadId) throws Exception {
        iRequirementLineService.importRequirementLineModelDownload(response,templateHeadId);
    }

    /**
     * 物流采购申请-路线-导入模板下载
     * @param response
     */
    @RequestMapping("/importModelDownload2")
    public void importModelDownload2(HttpServletResponse response) throws IOException {
        iRequirementLineService.importModelDownload2(response);
    }

    /**
     * 物流采购申请-路线-导入
     * @param file
     * @return
     */
    @RequestMapping("/importExcel")
    public List<LogisticsRequirementLine> importExcel(@RequestParam("file") MultipartFile file) throws IOException {
        return iRequirementLineService.importExcel(file);
    }

    /**
     * 物流采购申请-路线-导出文件下载
     */
    @RequestMapping("/export")
    public void export(HttpServletResponse response, @RequestParam("id") Long id) throws IOException {
        iRequirementLineService.export(response, id);
    }

}
