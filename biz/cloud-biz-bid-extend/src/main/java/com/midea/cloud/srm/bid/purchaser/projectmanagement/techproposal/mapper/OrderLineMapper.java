package com.midea.cloud.srm.bid.purchaser.projectmanagement.techproposal.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.techproposal.entity.OrderLine;
import com.midea.cloud.srm.model.bid.suppliercooperate.vo.BidOrderLineVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <pre>
 * 供应商投标行表
 * </pre>
 *
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020年3月25日 上午11:23:35
 *  修改内容:
 *          </pre>
 */
public interface OrderLineMapper extends BaseMapper<OrderLine> {

    @Deprecated
    List<BidOrderLineVO> getRequirementLineByBidingId(Long bidingId, Long bidVendorId);

    @Deprecated
    List<BidOrderLineVO> getWinOrderLineByBidingIdAndVendorId(Long bidingId, Long vendorId);

    List<BidOrderLineVO> getOrderLineByBidingIdAndVendorId(Long bidingId, Long vendorId);

    List<BidOrderLineVO> getOrderLineByOrderHeadId(Long orderHeadId);

    List<BidOrderLineVO> getWithStandardOrderInfoByOrderHeadId(Long orderHeadId);

    List<OrderLine> getBidingResultList(Long bidingId);

    @Select("select distinct ${ew.sqlSelect} from `scc_bid_order_line` ${ew.customSqlSegment}")
    List<OrderLine> getDistinctColumnByCondition(@Param(Constants.WRAPPER) Wrapper wrapper);
}
