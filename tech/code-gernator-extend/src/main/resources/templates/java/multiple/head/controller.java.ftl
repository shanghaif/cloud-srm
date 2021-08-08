package com.midea.cloud.srm.${codeVo.moduleName}.${packageName}.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.${codeVo.moduleName}.${packageName}.service.${classFileName}Service;
import com.midea.cloud.srm.${codeVo.moduleName}.${packageName}.service.${lineClassFileName}Service;
import com.midea.cloud.srm.model.${codeVo.moduleName}.${packageName}.entity.${classFileName};
import com.midea.cloud.srm.model.${codeVo.moduleName}.${packageName}.entity.${lineClassFileName};
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
* <pre>
 *  ${codeVo.functionDesc} 控制层
 * </pre>
*
* @author ${codeVo.author}
* @version 1.00.00
*
* <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: ${.now}
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/${codeVo.moduleName}/${packageName}")
public class ${classFileName}Controller extends BaseController {



    @Autowired
    private ${lineClassFileName}Service ${lineTargetName}Service;

    @Autowired
    private ${classFileName}Service ${targetName}Service;


    /**
     * 分页查询
     * @param ${targetName}
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<${classFileName}> listPage(@RequestBody ${classFileName} ${targetName}) {
        return ${targetName}Service.listPage(${targetName});
    }

    /**
    * 获取头行详情
    * @param id
    */
    @GetMapping("/get")
    public ${classFileName} get(Long id) {
        Assert.notNull(id, "id不能为空");
        return ${targetName}Service.getDetailById(id);
    }

    /**
    * 新增头行详情
    * @param ${targetName}
    */
    @PostMapping("/addOrUpdate")
    public Long add(@RequestBody ${classFileName} ${targetName}) {
        return  ${targetName}Service.addOrUpdate(${targetName});
    }

    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        ${targetName}Service.removeById(id);
    }
    /**
     * 批量删除
     * @param ids
     */
    @GetMapping("/bathDelete")
    public void bathDelete(@RequestBody List<Long> ids) throws IOException{
        ${targetName}Service.batchDeleted(ids);
    }
    /**
     * 导入行文件模板下载
     * @return
     */
    @RequestMapping("/export${lineClassFileName}ExcelTemplate")
    public void export${lineClassFileName}ExcelTemplate(HttpServletResponse response) throws IOException {
        ${lineTargetName}Service.exportExcelTemplate(response);
    }
    /**
     * 导入行文件内容
     * @param file
     */
    @RequestMapping("/import${lineClassFileName}Excel")
    public Map<String,Object> import${lineClassFileName}Excel(@RequestParam("file") MultipartFile file, Fileupload fileupload) throws Exception {
        return ${lineTargetName}Service.importExcel(file,fileupload);
    }
    /**
     * 导出文件
     * @param excelParam
     * @param response
     * @throws Exception
     */
    @RequestMapping("/export${lineClassFileName}Excel")
    public void export${lineClassFileName}Excel(@RequestBody ${lineClassFileName} excelParam, HttpServletResponse response) throws Exception {
        ${lineTargetName}Service.exportExcel(excelParam,response);
    }
}
