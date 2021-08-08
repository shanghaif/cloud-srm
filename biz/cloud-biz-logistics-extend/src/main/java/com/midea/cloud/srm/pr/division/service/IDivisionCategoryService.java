package com.midea.cloud.srm.pr.division.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.ExportExcel;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.pm.pr.division.dto.DivisionCategoryQueryDTO;
import com.midea.cloud.srm.model.pm.pr.division.entity.DivisionCategory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  品类分工规则表 服务类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-07-22 08:41:41
 *  修改内容:
 * </pre>
*/
public interface IDivisionCategoryService extends IService<DivisionCategory> , ExportExcel<DivisionCategory> {

    /**
     * 保存品类分工规则
     * @param divisionCategories
     */
    void saveOrUpdateDivisionCategory(List<DivisionCategory> divisionCategories);

    /**
     * 分页条件查询
     * @param divisionCategoryQueryDTO
     */
    PageInfo<DivisionCategory> listPageByParam(DivisionCategoryQueryDTO divisionCategoryQueryDTO);

    /**
     * 导入文件模板下载
     * @param response
     */
    void importModelDownload(HttpServletResponse response) throws Exception;

    /**
     * 导入文件
     * @param file
     * @return
     */
    Map<String,Object> importExcel(MultipartFile file) throws Exception;

    boolean deleteDucplite();



}
