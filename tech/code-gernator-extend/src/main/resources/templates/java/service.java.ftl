package com.midea.cloud.srm.${codeVo.moduleName}.${packageName}.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.${codeVo.moduleName}.${packageName}.entity.${classFileName};
import java.util.List;
import java.io.IOException;
<#if (codeVo.isExport == 1) || (codeVo.isImport) ==1 >
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import org.springframework.web.multipart.MultipartFile;
</#if>

/**
* <pre>
 *  ${codeVo.functionDesc} 服务类
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
public interface ${classFileName}Service extends IService<${classFileName}>{
    /*
    批量更新或者批量新增
    */
     void batchSaveOrUpdate(List<${classFileName}> ${targetName}List) throws IOException;

    /*
     批量删除
     */
    void batchDeleted(List<Long> ids) throws IOException;
    <#if codeVo.isImport == 1>
    /*
    导出excel模板文件
    */
    public void exportExcelTemplate(HttpServletResponse response) throws IOException;
    </#if>
    <#if codeVo.isImport == 1>
    /*
    导入excel 内容
     */
    Map<String,Object> importExcel(MultipartFile file, Fileupload fileupload) throws Exception;
    </#if>
    <#if (codeVo.isExport == 1)>
    /*
     导出excel内容数据
     param ： 传入参数
     */
    void exportExcel(${classFileName} excelParam, HttpServletResponse response)throws IOException;
   </#if>
    /*
   分页查询
    */
    PageInfo<${classFileName}> listPage(${classFileName} ${targetName});


}
