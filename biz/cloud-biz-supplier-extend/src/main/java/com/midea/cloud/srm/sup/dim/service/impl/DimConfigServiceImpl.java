package com.midea.cloud.srm.sup.dim.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.model.supplier.dim.dto.DimConfigDTO;
import com.midea.cloud.srm.model.supplier.dim.entity.DimConfig;
import com.midea.cloud.srm.sup.dim.mapper.DimConfigMapper;
import com.midea.cloud.srm.sup.dim.service.IDimConfigService;
import com.midea.cloud.srm.sup.dim.service.IDimFieldConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
*  <pre>
 *  维度设置表 服务实现类
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
@Service
public class DimConfigServiceImpl extends ServiceImpl<DimConfigMapper, DimConfig> implements IDimConfigService {
    @Autowired
    private IDimFieldConfigService dimFieldConfigService;

    @Autowired
    private DimConfigMapper dimConfigMapper;

    @Override
    public void deleteDimByTemplateId(Long templateId) {
        QueryWrapper<DimConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("TEMPLATE_ID",templateId);
        List<DimConfig> deleteDimConfigs = this.list(queryWrapper);
        if(deleteDimConfigs != null
                && !deleteDimConfigs.isEmpty()) {
            for (DimConfig dimConfig : deleteDimConfigs) {
                dimFieldConfigService.deleteByDimconfigID(dimConfig.getDimConfigId());
            }
            this.remove(queryWrapper);
        }
    }

    @Override
    public void saveBatchDimConfig(List<DimConfig> dimConfigs,Long templateId) {
      for(DimConfig dimConfig:dimConfigs){
          dimConfig.setTemplateId(templateId);
          if(dimConfig.getDimConfigId() != null){
              dimConfig.setLastUpdateDate(new Date());
          }else{
              dimConfig.setCreationDate(new Date());
              Long id = IdGenrator.generate();
              dimConfig.setDimConfigId(id);
          }
          this.saveOrUpdate(dimConfig);
         //根据单个删除对应的信息
          dimFieldConfigService.deleteByDimconfigID(dimConfig.getDimConfigId());
          //迭代保存对应的字段
          if(dimConfig.getDimFieldConfigs() != null
                  && !dimConfig.getDimFieldConfigs().isEmpty()) {
              dimFieldConfigService.saveBatchDimField(dimConfig.getDimFieldConfigs(),dimConfig.getDimConfigId());
          }
      }
    }

    @Override
    public List<DimConfigDTO> getByTemplateId(Long templateId) {
        return dimConfigMapper.getByTemplateId(templateId);
    }

    @Override
    public List<DimConfigDTO> getDtoByParam(DimConfig dimConfig) {
        return dimConfigMapper.getDtoByParam(dimConfig);
    }
}
