package com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.Biding;

import java.util.List;

/**
 * <p>
 * 招标基础信息表 Mapper 接口
 * </p>
 *
 * @author fengdc3
 * @since 2020-03-18
 */
public interface BidingMapper extends BaseMapper<Biding> {

    List<Biding> listPage(Biding biding);
}
