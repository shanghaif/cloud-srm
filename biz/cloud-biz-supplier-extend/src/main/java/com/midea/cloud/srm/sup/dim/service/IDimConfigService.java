package com.midea.cloud.srm.sup.dim.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.supplier.dim.dto.DimConfigDTO;
import com.midea.cloud.srm.model.supplier.dim.entity.DimConfig;

import java.util.List;

/**
*  <pre>
 *  维度设置表 服务类
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-02 08:50:58
 *  修改内容:
 * </pre>
*/
public interface IDimConfigService extends IService<DimConfig> {

    void deleteDimByTemplateId(Long templateId);

    void saveBatchDimConfig(List<DimConfig> dimConfigs,Long templateId);

    List<DimConfigDTO> getByTemplateId(Long templateId);

    List<DimConfigDTO> getDtoByParam(DimConfig dimConfig);
}
