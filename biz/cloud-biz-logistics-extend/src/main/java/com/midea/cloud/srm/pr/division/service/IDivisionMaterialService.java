package com.midea.cloud.srm.pr.division.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.pm.pr.division.dto.DivisionMaterialQueryDTO;
import com.midea.cloud.srm.model.pm.pr.division.entity.DivisionMaterial;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
*  <pre>
 *  物料分工规则表 服务类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-07-22 08:45:14
 *  修改内容:
 * </pre>
*/
public interface IDivisionMaterialService extends IService<DivisionMaterial> {

    /**
     * 保存物料分工规则
     * @param divisionMaterials
     */
    void saveOrUpdateDivisionMaterial(List<DivisionMaterial> divisionMaterials);

    /**
     * 分页条件查询
     * @param divisionMaterialQueryDTO
     * @return
     */
    PageInfo<DivisionMaterial> listPageByParam(DivisionMaterialQueryDTO divisionMaterialQueryDTO);

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
    void importExcel(MultipartFile file) throws Exception;
}
