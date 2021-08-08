package com.midea.cloud.srm.bid.purchaser.projectmanagement.techproposal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.entity.BidVendor;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.businessproposal.entity.Round;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.techproposal.entity.OrderHead;
import com.midea.cloud.srm.model.logistics.bid.suppliercooperate.vo.BidOrderHeadVO;
import com.midea.cloud.srm.model.logistics.bid.suppliercooperate.vo.BidOrderLineVO;
import com.midea.cloud.srm.model.logistics.bid.suppliercooperate.vo.SupplierBidingVO;

import java.io.IOException;
import java.math.BigDecimal;
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
 *  修改日期: 2020年3月19日 下午7:11:35
 *  修改内容:
 *          </pre>
 */
public interface IOrderHeadService extends IService<OrderHead> {

    /**
     * 查找数据：投标-供应商端
     * @param bidingId
     * @return
     */
    BidOrderHeadVO getOrderHeadByBidingIdAndBidVerdorId(Long bidingId, Long bidVendorId);

    /**
     * 保存投标信息
     * @param bidOrderHeadVO
     * @return
     */
    Long saveOrderInfo(BidOrderHeadVO bidOrderHeadVO, boolean isSubmit);

    /**
     * 撤回投标
     * @param bidOrderHeadVO
     * @return
     */
    boolean withdrawOrder(BidOrderHeadVO bidOrderHeadVO);

    /**
     *  提交投标
      * @param bidOrderHeadVO
     * @return
     */
    boolean submitOrder(BidOrderHeadVO bidOrderHeadVO) throws IOException;

    /**
     * 供应商端--点击投标的时候，判断投标的条件。
     * @param supplierBidingVO
     * @return
     */
    void judgeBidingConditions(SupplierBidingVO supplierBidingVO);

    /**
     * 查找投标详情
     */
   BidOrderHeadVO getOrderHeadInfo(BidOrderHeadVO bidOrderHeadVO);

    List<BidOrderLineVO> matchOrderLine(Long bidingId, Long orderHeadId, Long bidVendorId, List<Object> list);


    List<BidOrderLineVO> getOrderLineVOS(Long bidingId, Long orderHeadId, Long bidVendorId);

    /**
     * 判断保存权限
     * @param bidOrderHeadVO
     */
   void judgeSaveOrderInfoPerssion(BidOrderHeadVO bidOrderHeadVO);

    /**
     * 为没有报价的供应商默认报价
     * @param bidSupplierId
     * @param bidingId
     */
    void submitOrderForSupplier(Long bidSupplierId, OrderHead lastHead, Long bidingId, Round currentRound);




    /**
     * 获取或创建 供应商投标信息
     *
     * @param queryParam    查询参数
     * @return  供应商投标信息
     */
    BidOrderHeadVO getOrCreateOrderHeadInfo(BidOrderHeadVO queryParam);

    /**
     * 更新 投标行价格
     *
     * @param lineId    投标行ID
     * @param price     价格
     */
    void updateOrderLinePrice(Long lineId, BigDecimal price);

    /**
     * 查询 仍未提交报价的供应商集
     *
     * @param biddingId 寻源单ID
     * @return 仍未提交报价的供应商集
     */
    List<BidVendor> findNotPricingVendors(Long biddingId);

}
