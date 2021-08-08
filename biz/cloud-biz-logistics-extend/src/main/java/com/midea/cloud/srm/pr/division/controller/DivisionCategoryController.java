package com.midea.cloud.srm.pr.division.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.base.categorydv.entity.CategoryDv;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.common.ExportExcelParam;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.pm.pr.division.dto.DivisionCategoryQueryDTO;
import com.midea.cloud.srm.model.pm.pr.division.entity.DivisionCategory;
import com.midea.cloud.srm.pr.division.service.IDivisionCategoryService;
import com.midea.cloud.srm.pr.division.utils.ExportUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *  品类分工规则表 前端控制器
 * </pre>
 *
 * @author chensl26@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-07-22 08:41:41
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/division/divisionCategory")
public class DivisionCategoryController extends BaseController {

    @Autowired
    private IDivisionCategoryService iDivisionCategoryService;

    /**
     * 新增品类分工规则
     *
     * @param divisionCategories
     */
    @PostMapping("/saveDivisionCategory")
    public void saveDivisionCategory(@RequestBody List<DivisionCategory> divisionCategories) {
        iDivisionCategoryService.saveOrUpdateDivisionCategory(divisionCategories);
    }

    /**
     * 编辑品类分工规则
     *
     * @param divisionCategories
     */
    @PostMapping("/updateDivisionCategory")
    public void updateDivisionCategory(@RequestBody List<DivisionCategory> divisionCategories) {
        iDivisionCategoryService.saveOrUpdateDivisionCategory(divisionCategories);
    }

    /**
     * 删除品类分工规则
     *
     * @param divisionCategoryId
     */
    @GetMapping("/deleteDivisionCategory")
    public void deleteDivisionCategory(@RequestParam Long divisionCategoryId) {
        iDivisionCategoryService.removeById(divisionCategoryId);
    }

    /**
     * 分页条件查询
     *
     * @param divisionCategoryQueryDTO
     * @return
     */
    @PostMapping("/listPageByParam")
    public PageInfo<DivisionCategory> listPageByParam(@RequestBody DivisionCategoryQueryDTO divisionCategoryQueryDTO) {
        return iDivisionCategoryService.listPageByParam(divisionCategoryQueryDTO);
    }


    /**
     * 导入文件模板下载
     *
     * @param response
     * @throws IOException
     */
    @RequestMapping("/importModelDownload")
    public void importModelDownload(HttpServletResponse response) throws Exception {
        iDivisionCategoryService.importModelDownload(response);
    }

    /**
     * 导入文件
     *
     * @param file
     */
    @RequestMapping("/importExcel")
    public Map<String, Object> importExcel(@RequestParam("file") MultipartFile file) throws Exception {
        return iDivisionCategoryService.importExcel(file);
    }

    /**
     * 获取标题
     * @return
     */
    @PostMapping("/exportExcelTitle")
    public Map<String, String> exportExcelTitle() {
        return ExportUtils.getCategoryDvTitles();
    }

    /**
     * 导出接口
     * @param excelParam
     * @param response
     * @throws IOException
     */
    @PostMapping("/exportExcel")
    public void exportExcel(@RequestBody ExportExcelParam<DivisionCategory> excelParam, HttpServletResponse response) throws IOException {
        iDivisionCategoryService.exportStart(excelParam, response);
    }

    @PostMapping("/deleteDucplite")
    public boolean deleteDucplite(){
        return iDivisionCategoryService.deleteDucplite();
    }
}
