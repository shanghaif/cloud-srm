package com.midea.cloud.srm.sup.dim.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.supplier.dim.dto.DimFieldDTO;
import com.midea.cloud.srm.model.supplier.dim.entity.DimField;

import java.util.List;

/**
 * <p>
 * 维度字段表 Mapper 接口
 * </p>
 *
 * @author zhuwl7@meicloud.com
 * @since 2020-02-29
 */
public interface DimFieldMapper extends BaseMapper<DimField> {

    List<DimFieldDTO> listPageByParam(DimFieldDTO requestDto);
}
