package com.midea.cloud.srm.base.customimport.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.base.customimport.dto.ImportModelParamDto;
import com.midea.cloud.srm.model.base.customimport.entity.ImportHead;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  自定义导入头表 服务类
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
public interface IImportHeadService extends IService<ImportHead> {
    /**
     * 导入模板下载
     * @param importModelParamDto
     * @param response
     */
    void importModelDownload(ImportModelParamDto importModelParamDto, HttpServletResponse response) throws IOException;

    /**
     * 自定义导入
     * @param file
     * @param fileupload
     * @return
     */
    Map<String,Object> importExcel(MultipartFile file, Fileupload fileupload) throws IOException;

    /**
     * 根据条件字段导出
     * @param importModelParamDto
     * @param response
     */
    void exportExcel(@RequestBody ImportModelParamDto importModelParamDto, HttpServletResponse response) throws IOException;

    /**
     * 获取详情
     * @param importHeadId
     * @return
     */
    ImportHead get(Long importHeadId);

    /**
     * 分页查询接口
     * @param importModelParamDto
     * @return
     */
    PageInfo<List<Object>> listPage(@RequestBody ImportModelParamDto importModelParamDto);
}
