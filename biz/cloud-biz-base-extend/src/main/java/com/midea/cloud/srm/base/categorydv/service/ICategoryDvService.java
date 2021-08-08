package com.midea.cloud.srm.base.categorydv.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.ExportExcel;
import com.midea.cloud.srm.model.base.categorydv.dto.CategoryDvDTO;
import com.midea.cloud.srm.model.base.categorydv.dto.DvRequestDTO;
import com.midea.cloud.srm.model.base.categorydv.entity.CategoryDv;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  品类分工 服务类
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
public interface ICategoryDvService extends IService<CategoryDv> , ExportExcel<CategoryDv> {

    void saveOrUpdateDv(CategoryDv categoryDv);

    void saveOrUpdateDvBatch(List<CategoryDv> categoryDvs);

    PageInfo<CategoryDv> listPageByParam(DvRequestDTO requestDTO);

    List<CategoryDvDTO> listByParam(DvRequestDTO requestDTO);

    /**
     * 导入模板下载
     * @param response
     */
    void importModelDownload(HttpServletResponse response) throws IOException;

    /**
     * 导入excel
     * @param file
     * @return
     */
    Map<String,Object> importExcel(MultipartFile file, Fileupload fileupload) throws Exception;
}
