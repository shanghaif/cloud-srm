package com.midea.cloud.srm.base.organization.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.base.organization.entity.ErpMaterialItem;

import java.util.List;

/**
 * <p>
 * 物料维护表（隆基物料同步） Mapper 接口
 * </p>
 *
 * @author xiexh12@midea.com
 * @since 2020-08-20
 */
public interface ErpMaterialItemMapper extends BaseMapper<ErpMaterialItem> {
    List<ErpMaterialItem> queryErpMaterialItem500();
}
