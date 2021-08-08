package com.midea.cloud.srm.base.customimport.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.base.customimport.service.IImportHeadService;
import com.midea.cloud.srm.model.base.customimport.dto.ImportModelParamDto;
import com.midea.cloud.srm.model.common.BaseController;
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
 *  自定义导入头表 前端控制器
 * </pre>
*
* @author wangpr@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-21 15:22:34
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/import-head")
public class ImportHeadController extends BaseController {

    @Autowired
    private IImportHeadService iImportHeadService;
    
    /**
    * 删除
    * @param importHeadId
    */
    @GetMapping("/delete")
    public void delete(Long importHeadId) {
        Assert.notNull(importHeadId, "importHeadId不能为空");
        iImportHeadService.removeById(importHeadId);
    }

    /**
    * 分页查询
    * @param importModelParamDto
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<List<Object>> listPage(@RequestBody ImportModelParamDto importModelParamDto) {
        return iImportHeadService.listPage(importModelParamDto);
    }

    /**
     * 导入文件模板下载
     * @return
     */
    @RequestMapping("/importModelDownload")
    public void importModelDownload(@RequestBody ImportModelParamDto importModelParamDto, HttpServletResponse response) throws IOException {
        iImportHeadService.importModelDownload(importModelParamDto,response);
    }

    /**
     * 导入文件
     * @param file
     */
    @RequestMapping("/importExcel")
    public Map<String,Object> importExcel(@RequestParam("file") MultipartFile file, Fileupload fileupload) throws Exception {
        return iImportHeadService.importExcel(file, fileupload);
    }

    /**
     * 导出文件
     * @param importModelParamDto
     * @param response
     * @throws Exception
     */
    @RequestMapping("/exportExcel")
    public void exportExcel(@RequestBody ImportModelParamDto importModelParamDto, HttpServletResponse response) throws Exception {
        iImportHeadService.exportExcel(importModelParamDto, response);
    }
}
