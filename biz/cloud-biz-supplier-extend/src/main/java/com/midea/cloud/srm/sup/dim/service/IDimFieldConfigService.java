package com.midea.cloud.srm.sup.dim.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.supplier.dim.entity.DimFieldConfig;

import java.util.List;

/**
*  <pre>
 *  维度字段配置表 服务类
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-02 10:04:17
 *  修改内容:
 * </pre>
*/
public interface IDimFieldConfigService extends IService<DimFieldConfig> {

    void deleteByDimconfigID(Long dimConfigId);

    void saveBatchDimField(List<DimFieldConfig> dimFieldConfigs,Long dimConfigId);
}
