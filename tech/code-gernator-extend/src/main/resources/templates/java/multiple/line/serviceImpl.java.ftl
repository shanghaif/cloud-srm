package com.midea.cloud.srm.${codeVo.moduleName}.${packageName}.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;


import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.${codeVo.moduleName}.${packageName}.mapper.${lineClassFileName}Mapper;
import com.midea.cloud.srm.${codeVo.moduleName}.${packageName}.service.${lineClassFileName}Service;

import com.midea.cloud.srm.model.${codeVo.moduleName}.${packageName}.entity.${lineClassFileName};

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import org.apache.commons.collections4.CollectionUtils;
import java.util.Map;
import java.util.ArrayList;
 import java.util.Arrays;
import com.midea.cloud.srm.model.${codeVo.moduleName}.${packageName}.dto.Excel${lineClassFileName}Dto;
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
public class ${lineClassFileName}ServiceImpl extends ServiceImpl<${lineClassFileName}Mapper, ${lineClassFileName}> implements ${lineClassFileName}Service {

    @Resource
    private FileCenterClient fileCenterClient;

    @Transactional
    public void batchUpdate(List<${lineClassFileName}> ${lineClassFileName}List) {
        this.saveOrUpdateBatch(${lineClassFileName}List);
    }

    @Override
    @Transactional
    public void batchSaveOrUpdate(List<${lineClassFileName}> ${lineClassFileName}List) throws IOException {
        for(${lineClassFileName} ${lineClassFileName} : ${lineClassFileName}List){
            if(${lineClassFileName}.get${linePk?cap_first}() == null){
                Long id = IdGenrator.generate();
                ${lineClassFileName}.set${linePk?cap_first}(id);
            }
        }
        if(!CollectionUtils.isEmpty(${lineClassFileName}List)) {
            batchUpdate(${lineClassFileName}List);
        }
    }

    @Override
    @Transactional
    public void batchDeleted(List<Long> ids) {
        this.removeByIds(ids);
    }

    @Override
    public void exportExcelTemplate(HttpServletResponse response) throws IOException {
        // 获取输出流
        OutputStream outputStream = EasyExcelUtil.getServletOutputStream(response,"模板下载");
        EasyExcel.write(outputStream).head(Excel${lineClassFileName}Dto.class).sheet(0).sheetName("sheetName").doWrite(Arrays.asList(new Excel${lineClassFileName}Dto()));
    }

    @Override
    @Transactional
    public Map<String, Object> importExcel(MultipartFile file, Fileupload fileupload) throws Exception {
        // 检查参数
        EasyExcelUtil.checkParam(file, fileupload);
        // 读取数据
        List<Excel${lineClassFileName}Dto> ${lineClassFileName}Dtos = EasyExcelUtil.readExcelWithModel(file, Excel${lineClassFileName}Dto.class);
        // 检查导入数据是否正确
        AtomicBoolean errorFlag = new AtomicBoolean(true);
        // 导入数据校验
        List<${lineClassFileName}> ${lineClassFileName}s = chackImportParam(${lineClassFileName}Dtos, errorFlag);

        if(errorFlag.get()){
            // 保存或者更新
            batchSaveOrUpdate(${lineClassFileName}s);
        }else {
            // 有错误,上传错误文件
            Fileupload errorFileupload = EasyExcelUtil.uploadErrorFile(fileCenterClient, fileupload, ${lineClassFileName}Dtos, Excel${lineClassFileName}Dto.class, file);
            return ImportStatus.importError(errorFileupload.getFileuploadId(),errorFileupload.getFileSourceName());
        }
        return ImportStatus.importSuccess();
    }

    /**
     * 检查导入参数
     * @param excel${lineClassFileName}Dto
     * @param errorFlag
     * @return 业务实体集合
     */
    public List<${lineClassFileName}> chackImportParam(List<Excel${lineClassFileName}Dto> excel${lineClassFileName}Dto, AtomicBoolean errorFlag){
        /**
         * 省略校验过程,数据有错误则把  errorFlag.set(false);
         */
        List<${lineClassFileName}> ${lineClassFileName}s = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(excel${lineClassFileName}Dto)){
            excel${lineClassFileName}Dto.forEach(${lineClassFileName}Dto -> {
                ${lineClassFileName} ${lineClassFileName} = new ${lineClassFileName}();
                StringBuffer errorMsg = new StringBuffer();
                BeanCopyUtil.copyProperties(${lineClassFileName},${lineClassFileName}Dto);
                // 检查示例: noticeId 非空
//                Long noticeId = ${lineClassFileName}Dto.getNoticeId();
//                if(ObjectUtils.isEmpty(noticeId)){
//                    errorMsg.append("noticeId不能为空;");
//                    errorFlag.set(false);
//                }else {
//                    ${lineClassFileName}.setNoticeId(noticeId);
//                }
//
//                // .......
//                if(errorMsg.length() > 0){
//                    ${lineClassFileName}Dto.setErrorMsg(errorMsg.toString());
//                }else {
//                    ${lineClassFileName}s.add(${lineClassFileName});
//                }
                ${lineClassFileName}s.add(${lineClassFileName});
            });
        }
        return ${lineClassFileName}s;
    }

    @Override
    public void exportExcel(${lineClassFileName} excelParam, HttpServletResponse response) throws IOException {
        // 查询数据
        List<${lineClassFileName}> ${lineClassFileName}s = get${lineClassFileName}s(excelParam);
        List<Excel${lineClassFileName}Dto> excel${lineClassFileName}Dto = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(${lineClassFileName}s)){
            ${lineClassFileName}s.forEach(${lineClassFileName} -> {
                Excel${lineClassFileName}Dto ${lineClassFileName}Dto = new Excel${lineClassFileName}Dto();
                BeanCopyUtil.copyProperties(${lineClassFileName}Dto,${lineClassFileName});
                excel${lineClassFileName}Dto.add(${lineClassFileName}Dto);
            });
        }
        // 获取输出流
        OutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, "导出文件名");
        EasyExcel.write(outputStream).head(Excel${lineClassFileName}Dto.class).sheet(0).sheetName("sheetName").doWrite(excel${lineClassFileName}Dto);

    }

    @Override
    public PageInfo<${lineClassFileName}> listPage(${lineClassFileName} ${lineClassFileName}) {
        PageUtil.startPage(${lineClassFileName}.getPageNum(), ${lineClassFileName}.getPageSize());
        List<${lineClassFileName}> ${lineClassFileName}s = get${lineClassFileName}s(${lineClassFileName});
        return new PageInfo<>(${lineClassFileName}s);
    }

    public List<${lineClassFileName}> get${lineClassFileName}s(${lineClassFileName} ${lineClassFileName}) {
        QueryWrapper<${lineClassFileName}> wrapper = new QueryWrapper<>();
  //      wrapper.eq("NOTICE_ID",${lineClassFileName}.getNoticeId()); // 精准匹配
//        wrapper.like("TITLE",${lineClassFileName}.getTitle());      // 模糊匹配
//        wrapper.and(queryWrapper ->
//                queryWrapper.ge("CREATION_DATE",${lineClassFileName}.getStartDate()).
//                        le("CREATION_DATE",${lineClassFileName}.getEndDate())); // 时间范围查询
        return this.list(wrapper);
    }
}
