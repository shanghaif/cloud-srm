package com.midea.cloud.srm.sup.dim.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.supplier.dim.entity.DimFieldConfig;
import com.midea.cloud.srm.sup.dim.mapper.DimFieldConfigMapper;
import com.midea.cloud.srm.sup.dim.service.IDimFieldConfigService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
*  <pre>
 *  维度字段配置表 服务实现类
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
@Service
public class DimFieldConfigServiceImpl extends ServiceImpl<DimFieldConfigMapper, DimFieldConfig> implements IDimFieldConfigService {

    @Override
    public void deleteByDimconfigID(Long dimConfigId) {
        QueryWrapper<DimFieldConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("DIM_CONFIG_ID",dimConfigId);
        this.remove(queryWrapper);
    }

    @Override
    public void saveBatchDimField(List<DimFieldConfig> dimFieldConfigs,Long dimConfigId) {
        for(DimFieldConfig dimFieldConfig:dimFieldConfigs){
            if(dimFieldConfig.getFieldConfigId() != null){
                dimFieldConfig.setLastUpdateDate(new Date());
            }else {
                Long id = IdGenrator.generate();
                dimFieldConfig.setFieldConfigId(id);
                dimFieldConfig.setDimConfigId(dimConfigId);
                dimFieldConfig.setCreationDate(new Date());
            }
        }
        this.saveOrUpdateBatch(dimFieldConfigs);
    }
}
