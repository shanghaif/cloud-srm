package com.midea.cloud.srm.base.categorydv.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.base.categorydv.service.ICategoryDvService;
import com.midea.cloud.srm.base.categorydv.utils.ExportUtils;
import com.midea.cloud.srm.model.base.categorydv.dto.CategoryDvDTO;
import com.midea.cloud.srm.model.base.categorydv.dto.DvRequestDTO;
import com.midea.cloud.srm.model.base.categorydv.entity.CategoryDv;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.common.ExportExcelParam;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  品类分工 前端控制器
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-06 10:04:24
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/categoryDv")
public class CategoryDvController extends BaseController {

    @Autowired
    private ICategoryDvService iCategoryDvService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public CategoryDv get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iCategoryDvService.getById(id);
    }

    /**
    * 新增
    * @param categoryDv
    */
    @PostMapping("/add")
    public void add(CategoryDv categoryDv) {
        Long id = IdGenrator.generate();
        categoryDv.setCategoryDvId(id);
        iCategoryDvService.save(categoryDv);
    }
    
    /**
    * 删除
    * @param categoryDvId
    */
    @GetMapping("/delete")
    public void delete(Long categoryDvId) {
        Assert.notNull(categoryDvId, "id不能为空");
        iCategoryDvService.removeById(categoryDvId);
    }

    /**
    * 修改
    * @param categoryDv
    */
    @PostMapping("/modify")
    public void modify(CategoryDv categoryDv) {
        iCategoryDvService.updateById(categoryDv);
    }

    /**
    * 分页查询
    * @param categoryDv
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<CategoryDv> listPage(@RequestBody CategoryDv categoryDv) {
        PageUtil.startPage(categoryDv.getPageNum(), categoryDv.getPageSize());
        QueryWrapper<CategoryDv> wrapper = new QueryWrapper<CategoryDv>(categoryDv);
        return new PageInfo<CategoryDv>(iCategoryDvService.list(wrapper));
    }

    /**
     * 根据参数分页查询
     * @param requestDTO
     * @return
     */
    @PostMapping("/listPageByParam")
    public PageInfo<CategoryDv> listPageByParam(@RequestBody DvRequestDTO requestDTO) {
        return iCategoryDvService.listPageByParam(requestDTO);
    }

    /**
     * 根据参数查询
     * @param requestDTO
     * @return
     */
    @PostMapping("/listByParam")
    public List<CategoryDvDTO> listByParam(@RequestBody DvRequestDTO requestDTO) {
        return iCategoryDvService.listByParam(requestDTO);
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<CategoryDv> listAll() { 
        return iCategoryDvService.list();
    }

    /**
     * 保存或更新
     */
    @PostMapping("/saveOrUpdateDv")
    public void  saveOrUpdateDv(@RequestBody CategoryDv categoryDv){
            iCategoryDvService.saveOrUpdateDv(categoryDv);
    }

    /**
     * 保存或更新
     */
    @PostMapping("/saveOrUpdateDvBatch")
    public void  saveOrUpdateDvBatch(@RequestBody List<CategoryDv> categoryDvs){
        iCategoryDvService.saveOrUpdateDvBatch(categoryDvs);
    }

    @PostMapping("/queryUserByCategoryId")
    public List<CategoryDv> queryUserByCategoryId(@RequestBody CategoryDv categoryDv) {
        QueryWrapper<CategoryDv> queryWrapper = new QueryWrapper<>(categoryDv);
        return iCategoryDvService.list(queryWrapper);
    }

    /**
     * 导入文件模板下载
     * @return
     */
    @RequestMapping("/importModelDownload")
    public void importModelDownload(HttpServletResponse response) throws IOException {
        iCategoryDvService.importModelDownload(response);
    }

    /**
     * 导入文件
     * @param file
     */
    @RequestMapping("/importExcel")
    public Map<String,Object> importExcel(@RequestParam("file") MultipartFile file, Fileupload fileupload) throws Exception {
        return iCategoryDvService.importExcel(file,fileupload);
    }

    /**
     * 获取导出标题
     * @return
     */
    @RequestMapping("/exportExcelTitle")
    public Map<String,String> exportExcelTitle(){
        return ExportUtils.getCategoryDvTitles();
    }

    /**
     * 导出文件
     * @param excelParam
     * @param response
     * @throws Exception
     */
    @RequestMapping("/exportExcel")
    public void exportExcel(@RequestBody ExportExcelParam<CategoryDv> excelParam, HttpServletResponse response) throws Exception {
        iCategoryDvService.exportStart(excelParam,response);
    }

}
