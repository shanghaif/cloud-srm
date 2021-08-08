package com.midea.cloud.srm.${codeVo.moduleName}.${packageName}.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;


import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.${codeVo.moduleName}.${packageName}.mapper.${classFileName}Mapper;
import com.midea.cloud.srm.${codeVo.moduleName}.${packageName}.service.${classFileName}Service;

import com.midea.cloud.srm.model.${codeVo.moduleName}.${packageName}.entity.${classFileName};

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
<#if (codeVo.isExport == 1) || (codeVo.isImport) ==1 >
import com.midea.cloud.srm.feign.file.FileCenterClient;
import org.apache.commons.collections4.CollectionUtils;
import java.util.Map;
import java.util.ArrayList;
 import java.util.Arrays;
import com.midea.cloud.srm.model.${codeVo.moduleName}.${packageName}.dto.Excel${classFileName}Dto;
import com.midea.cloud.common.enums.ImportStatus;
import com.midea.cloud.common.utils.BeanCopyUtil;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import com.alibaba.excel.EasyExcel;
import com.midea.cloud.common.utils.EasyExcelUtil;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
</#if>
/**
* <pre>
 *  ${codeVo.functionDesc} 服务实现类
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
@Service
public class ${classFileName}ServiceImpl extends ServiceImpl<${classFileName}Mapper, ${classFileName}> implements ${classFileName}Service {
  <#if codeVo.isImport == 1>
    @Resource
    private FileCenterClient fileCenterClient;
   </#if>
    @Transactional
    public void batchUpdate(List<${classFileName}> ${targetName}List) {
        this.saveOrUpdateBatch(${targetName}List);
    }

    @Override
    @Transactional
    public void batchSaveOrUpdate(List<${classFileName}> ${targetName}List) throws IOException {
        for(${classFileName} ${targetName} : ${targetName}List){
            if(${targetName}.get${pk?cap_first}() == null){
                Long id = IdGenrator.generate();
                ${targetName}.set${pk?cap_first}(id);
            }
        }
        if(!CollectionUtils.isEmpty(${targetName}List)) {
            batchUpdate(${targetName}List);
        }
    }

    @Override
    @Transactional
    public void batchDeleted(List<Long> ids) {
        this.removeByIds(ids);
    }
    <#if codeVo.isImport == 1>
    @Override
    public void exportExcelTemplate(HttpServletResponse response) throws IOException {
        // 获取输出流
        OutputStream outputStream = EasyExcelUtil.getServletOutputStream(response,"模板下载");
        EasyExcel.write(outputStream).head(Excel${classFileName}Dto.class).sheet(0).sheetName("sheetName").doWrite(Arrays.asList(new Excel${classFileName}Dto()));
    }
    </#if>
    <#if codeVo.isImport == 1>
    @Override
    @Transactional
    public Map<String, Object> importExcel(MultipartFile file, Fileupload fileupload) throws Exception {
        // 检查参数
        EasyExcelUtil.checkParam(file, fileupload);
        // 读取数据
        List<Excel${classFileName}Dto> ${targetName}Dtos = EasyExcelUtil.readExcelWithModel(file, Excel${classFileName}Dto.class);
        // 检查导入数据是否正确
        AtomicBoolean errorFlag = new AtomicBoolean(true);
        // 导入数据校验
        List<${classFileName}> ${targetName}s = chackImportParam(${targetName}Dtos, errorFlag);

        if(errorFlag.get()){
            // 保存或者更新
            batchSaveOrUpdate(${targetName}s);
        }else {
            // 有错误,上传错误文件
            Fileupload errorFileupload = EasyExcelUtil.uploadErrorFile(fileCenterClient, fileupload, ${targetName}Dtos, Excel${classFileName}Dto.class, file);
            return ImportStatus.importError(errorFileupload.getFileuploadId(),errorFileupload.getFileSourceName());
        }
        return ImportStatus.importSuccess();
    }
    </#if>
    <#if codeVo.isImport == 1>
    /**
     * 检查导入参数
     * @param excel${classFileName}Dto
     * @param errorFlag
     * @return 业务实体集合
     */
    public List<${classFileName}> chackImportParam(List<Excel${classFileName}Dto> excel${classFileName}Dto, AtomicBoolean errorFlag){
        /**
         * 省略校验过程,数据有错误则把  errorFlag.set(false);
         */
        List<${classFileName}> ${targetName}s = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(excel${classFileName}Dto)){
            excel${classFileName}Dto.forEach(${targetName}Dto -> {
                ${classFileName} ${targetName} = new ${classFileName}();
                StringBuffer errorMsg = new StringBuffer();
                BeanCopyUtil.copyProperties(${targetName},${targetName}Dto);
                // 检查示例: noticeId 非空
//                Long noticeId = ${targetName}Dto.getNoticeId();
//                if(ObjectUtils.isEmpty(noticeId)){
//                    errorMsg.append("noticeId不能为空;");
//                    errorFlag.set(false);
//                }else {
//                    ${targetName}.setNoticeId(noticeId);
//                }
//
//                // .......
//                if(errorMsg.length() > 0){
//                    ${targetName}Dto.setErrorMsg(errorMsg.toString());
//                }else {
//                    ${targetName}s.add(${targetName});
//                }
                ${targetName}s.add(${targetName});
            });
        }
        return ${targetName}s;
    }
    </#if>
    <#if (codeVo.isExport == 1)>
    @Override
    public void exportExcel(${classFileName} excelParam, HttpServletResponse response) throws IOException {
        // 查询数据
        List<${classFileName}> ${targetName}s = get${classFileName}s(excelParam);
        List<Excel${classFileName}Dto> excel${classFileName}Dto = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(${targetName}s)){
            ${targetName}s.forEach(${targetName} -> {
                Excel${classFileName}Dto ${targetName}Dto = new Excel${classFileName}Dto();
                BeanCopyUtil.copyProperties(${targetName}Dto,${targetName});
                excel${classFileName}Dto.add(${targetName}Dto);
            });
        }
        // 获取输出流
        OutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, "导出文件名");
        EasyExcel.write(outputStream).head(Excel${classFileName}Dto.class).sheet(0).sheetName("sheetName").doWrite(excel${classFileName}Dto);

    }
    </#if>
    @Override
    public PageInfo<${classFileName}> listPage(${classFileName} ${targetName}) {
        PageUtil.startPage(${targetName}.getPageNum(), ${targetName}.getPageSize());
        List<${classFileName}> ${targetName}s = get${classFileName}s(${targetName});
        return new PageInfo<>(${targetName}s);
    }

    public List<${classFileName}> get${classFileName}s(${classFileName} ${targetName}) {
        QueryWrapper<${classFileName}> wrapper = new QueryWrapper<>();
  //      wrapper.eq("NOTICE_ID",${targetName}.getNoticeId()); // 精准匹配
//        wrapper.like("TITLE",${targetName}.getTitle());      // 模糊匹配
//        wrapper.and(queryWrapper ->
//                queryWrapper.ge("CREATION_DATE",${targetName}.getStartDate()).
//                        le("CREATION_DATE",${targetName}.getEndDate())); // 时间范围查询
        return this.list(wrapper);
    }
}
