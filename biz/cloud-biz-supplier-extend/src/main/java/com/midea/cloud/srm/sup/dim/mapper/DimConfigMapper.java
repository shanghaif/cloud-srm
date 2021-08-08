package com.midea.cloud.srm.sup.dim.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.supplier.dim.dto.DimConfigDTO;
import com.midea.cloud.srm.model.supplier.dim.entity.DimConfig;

import java.util.List;

/**
 * <p>
 * 维度设置表 Mapper 接口
 * </p>
 *
 * @author zhuwl7@meicloud.com
 * @since 2020-03-02
 */
public interface DimConfigMapper extends BaseMapper<DimConfig> {

    List<DimConfigDTO> getByTemplateId(Long templatedId);

    List<DimConfigDTO> getDtoByParam(DimConfig dimConfig);
}
