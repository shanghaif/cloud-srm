package com.midea.cloud.srm.sup.demotion.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.supplier.demotion.dto.CompanyDemotionQueryDTO;
import com.midea.cloud.srm.model.supplier.demotion.entity.CompanyDemotion;

import java.util.List;

/**
 * <p>
 * 供应商升降级表 Mapper 接口
 * </p>
 *
 * @author xiexh12@meicloud.com
 * @since 2021-01-05
 */
public interface CompanyDemotionMapper extends BaseMapper<CompanyDemotion> {

    /**
     * 分页条件查询
     * @param queryDTO
     * @return
     */
    List<CompanyDemotion> listPageByParam(CompanyDemotionQueryDTO queryDTO);

}
