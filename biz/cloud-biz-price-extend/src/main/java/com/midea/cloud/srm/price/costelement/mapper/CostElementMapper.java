package com.midea.cloud.srm.price.costelement.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.price.costelement.entity.CostElement;

import java.util.List;

/**
 * <p>
 * 成本要素表 Mapper 接口
 * </p>
 *
 * @author wangpr@meicloud.com
 * @since 2020-07-25
 */
public interface CostElementMapper extends BaseMapper<CostElement> {
    /**
     * 分页查询
     * @param costElement
     * @return
     */
    List<CostElement> listPage(CostElement costElement);

}
