package com.midea.cloud.srm.bid.purchaser.projectmanagement.techproposal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.techproposal.entity.OrderLine;
import com.midea.cloud.srm.model.logistics.bid.suppliercooperate.vo.BidOrderLineVO;

import java.util.List;
import java.util.Set;

/**
 * 
 * 
 * <pre>
 * 供应商投标行表
 * </pre>
 * 
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 * 
 *          <pre>
 *  修改记录
 *  修改后版本: 
 *  修改人: 
 *  修改日期: 2020年3月25日 上午11:24:21  
 *  修改内容:
 *          </pre>
 */
public interface IOrderLineService extends IService<OrderLine> {
    /**
     * 当第一轮报价的时候，直接获取需求行的数据
     * @param biddingId
     * @return
     */
    List<BidOrderLineVO> getRequirementLineByBidingIdAndBidVendorId(Long biddingId, Long bidVendorId);

    /**
     *  当不是第一次报价的时候，如果第二轮报价，淘汰的行数据不要查询出来
     * @param biddingId
     * @param bidVendorId
     * @return
     */
    List<BidOrderLineVO> getWinOrderLineByBidingIdAndVendorId(Long biddingId, Long bidVendorId);

    /**
     * 当多轮投标的时候，查找所有轮次的投标行信息
     * @param bidingId
     * @param vendorId
     * @return
     */
    List<BidOrderLineVO> getOrderLineByBidingIdAndVendorId(Long bidingId, Long vendorId);

    /**
     * 根据投标头表ID，查找投标行数据
     * @param orderHeadId
     * @return
     */
    List<BidOrderLineVO> getOrderLineByOrderHeadId(Long orderHeadId);

    /**
     * 查找跟标数据
     * @param orderHeadId
     * @return
     */
    List<BidOrderLineVO> getWithStandardOrderInfoByOrderHeadId(Long orderHeadId);

    boolean saveBatchOrderLines(List<BidOrderLineVO> orderLineVOS, boolean isSubmit, Set<Long> materialIds);

}
