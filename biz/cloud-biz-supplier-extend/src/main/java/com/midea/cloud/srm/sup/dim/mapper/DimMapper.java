package com.midea.cloud.srm.sup.dim.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.supplier.dim.entity.Dim;

import java.util.List;

/**
 * <p>
 * 维度表 Mapper 接口
 * </p>
 *
 * @author zhuwl7@meicloud.com
 * @since 2020-02-29
 */
public interface DimMapper extends BaseMapper<Dim> {

    List<Dim> selectExists(Dim dim);
}
