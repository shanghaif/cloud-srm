package com.midea.cloud.srm.bid.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.logistics.bid.dto.LgtBidingDto;
import com.midea.cloud.srm.model.logistics.bid.entity.LgtBiding;

import java.util.List;

/**
 * <p>
 * 物流寻源基础信息表 Mapper 接口
 * </p>
 *
 * @author wangpr@meiCloud.com
 * @since 2021-01-06
 */
public interface LgtBidingMapper extends BaseMapper<LgtBiding> {

    /**
     * 采购商列表查询
     * @param lgtBiding
     * @return
     */
    List<LgtBiding> listPageByBuyers(LgtBiding lgtBiding);

    /**
     * 供应商查询列表
     * @param lgtBidingDto
     * @return
     */
    List<LgtBidingDto> queryLgtBidingDto(LgtBidingDto lgtBidingDto);
}
