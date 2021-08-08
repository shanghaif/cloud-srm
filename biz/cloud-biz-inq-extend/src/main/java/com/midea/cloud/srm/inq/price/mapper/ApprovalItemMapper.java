package com.midea.cloud.srm.inq.price.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.inq.price.entity.ApprovalItem;
import com.midea.cloud.srm.model.pm.po.dto.NetPriceQueryDTO;

import java.util.List;

/**
 * <p>
 * 价格审批单行表 Mapper 接口
 * </p>
 *
 * @author chensl26@meiCloud.com
 * @since 2020-04-08
 */
public interface ApprovalItemMapper extends BaseMapper<ApprovalItem> {

	List<ApprovalItem> queryApprovalItemList(NetPriceQueryDTO netPriceQueryDTO);
	
}
