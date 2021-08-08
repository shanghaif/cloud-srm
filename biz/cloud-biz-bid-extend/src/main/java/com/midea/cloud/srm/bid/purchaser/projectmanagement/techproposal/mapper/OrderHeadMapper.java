package com.midea.cloud.srm.bid.purchaser.projectmanagement.techproposal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.BidVendor;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.techproposal.entity.OrderHead;
import com.midea.cloud.srm.model.bid.suppliercooperate.vo.BidOrderHeadVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * <pre>
 * 供应商投标头表
 * </pre>
 * 
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 * 
 *          <pre>
 *  修改记录
 *  修改后版本: 
 *  修改人: 
 *  修改日期: 2020年3月21日 下午2:35:58  
 *  修改内容:
 *          </pre>
 */
public interface OrderHeadMapper extends BaseMapper<OrderHead> {
    public BidOrderHeadVO getOrderHeadByBidingId(@Param("bidingId") Long bidingId, @Param("bidVendorId") Long bidVendorId);


    /**
     * 查询 第一轮仍未提交报价的供应商集
     *
     * @param biddingId 寻源单ID
     * @return 仍未提交报价的供应商集
     */
    List<BidVendor> findFirstRoundNotPricingVendors(@Param("biddingId") Long biddingId);
}
