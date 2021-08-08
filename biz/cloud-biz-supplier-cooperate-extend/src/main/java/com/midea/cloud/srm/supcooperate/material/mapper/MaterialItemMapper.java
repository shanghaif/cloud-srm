package com.midea.cloud.srm.supcooperate.material.mapper;

import com.midea.cloud.srm.model.suppliercooperate.material.entity.CeeaMaterialItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 物料计划维护表 Mapper 接口
 * </p>
 *
 * @author yourname@meiCloud.com
 * @since 2020-08-21
 */
public interface MaterialItemMapper extends BaseMapper<CeeaMaterialItem> {
    void updateSchTotalQuantity(@Param("materialItemId") Long materialItemId);
}
