package com.midea.cloud.srm.sup.dim.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.supplier.dim.dto.DimConfigDTO;
import com.midea.cloud.srm.model.supplier.dim.dto.DimTemplateDTO;
import com.midea.cloud.srm.model.supplier.dim.entity.DimTemplate;

import java.util.List;

/**
*  <pre>
 *  供应商模板配置表 服务类
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-02-29 14:35:31
 *  修改内容:
 * </pre>
*/
public interface IDimTemplateService extends IService<DimTemplate> {

    PageInfo<DimTemplate> listPage(DimTemplate dimTemplate);

    DimTemplate saveOrUpdateTemplate(DimTemplate dimTemplate);

     void checkTemplate(DimTemplate dimTemplate);

   DimTemplateDTO getByTemplateId(Long templateId);

    List<DimConfigDTO> getConfigByTemplate(DimTemplate dimTemplate);
}
