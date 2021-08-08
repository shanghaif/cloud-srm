package com.midea.cloud.srm.${codeVo.moduleName}.${packageName}.controller;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.${codeVo.moduleName}.${packageName}.service.${classFileName}Service;
import com.midea.cloud.srm.model.${codeVo.moduleName}.${packageName}.entity.${classFileName};
import com.midea.cloud.srm.model.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.io.IOException;
<#if (codeVo.isExport == 1) || (codeVo.isImport) ==1 >
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import javax.servlet.http.HttpServletResponse;
</#if>


/**
* <pre>
 *  ${codeVo.functionDesc} 前端控制器
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
    private ${classFileName}Service ${targetName}Service;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public ${classFileName} get(Long id) {
        Assert.notNull(id, "id不能为空");
        return ${targetName}Service.getById(id);
    }

    /**
    * 新增
    * @param ${targetName}
    */
    @PostMapping("/add")
    public void add(@RequestBody ${classFileName} ${targetName}) {
        Long id = IdGenrator.generate();
        <#list selectHeadFileList as field>
        <#if (field.isPk == "1")>
        ${targetName}.set${field.javaCode?cap_first}(id);
        </#if>
        </#list>
        ${targetName}Service.save(${targetName});
    }

    /**
     * 批量新增或者修改
     * @param ${targetName}List
     */
    @PostMapping("/batchSaveOrUpdate")
    public void batchSaveOrUpdate(@RequestBody List<${classFileName}> ${targetName}List) throws IOException{
         ${targetName}Service.batchSaveOrUpdate(${targetName}List);
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
    * 修改
    * @param ${targetName}
    */
    @PostMapping("/modify")
    public void modify(@RequestBody ${classFileName} ${targetName}) {
        ${targetName}Service.updateById(${targetName});
    }

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
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<${classFileName}> listAll() {
        return ${targetName}Service.list();
    }
    <#if codeVo.isImport == 1>
    /**
     * 导入文件模板下载
     * @return
     */
    @RequestMapping("/exportExcelTemplate")
    public void exportExcelTemplate(HttpServletResponse response) throws IOException {
        ${targetName}Service.exportExcelTemplate(response);
    }
    </#if>
    <#if codeVo.isImport == 1>
    /**
     * 导入文件内容
     * @param file
     */
    @RequestMapping("/importExcel")
    public Map<String,Object> importExcel(@RequestParam("file") MultipartFile file, Fileupload fileupload) throws Exception {
        return ${targetName}Service.importExcel(file,fileupload);
    }
    </#if>
    <#if (codeVo.isExport == 1)>
    /**
     * 导出文件
     * @param excelParam
     * @param response
     * @throws Exception
     */
    @RequestMapping("/exportExcel")
    public void exportExcel(@RequestBody ${classFileName} excelParam, HttpServletResponse response) throws Exception {
        ${targetName}Service.exportExcel(excelParam,response);
    }
    </#if>
}
