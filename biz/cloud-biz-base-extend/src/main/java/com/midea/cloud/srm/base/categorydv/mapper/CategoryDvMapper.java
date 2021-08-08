package com.midea.cloud.srm.base.categorydv.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.base.categorydv.dto.CategoryDvDTO;
import com.midea.cloud.srm.model.base.categorydv.dto.DvRequestDTO;
import com.midea.cloud.srm.model.base.categorydv.entity.CategoryDv;

import java.util.List;

/**
 * <p>
 * 品类分工 Mapper 接口
 * </p>
 *
 * @author zhuwl7@meicloud.com
 * @since 2020-03-06
 */
public interface CategoryDvMapper extends BaseMapper<CategoryDv> {

    List<CategoryDvDTO> listByParam(DvRequestDTO requestDTO);
}
