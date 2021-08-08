package com.midea.cloud.srm.feign.bid;

import com.midea.cloud.srm.feign.base.IVendorBiddingEssentialFactorValueService;
import com.midea.cloud.srm.model.base.formula.vo.EssentialFactorValue;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.BidBidingCurrency;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * FeignClient - 供应商投标明细行APi
 *
 * @author zixuan.yan@meicloud.com
 */
@FeignClient(value = "cloud-biz-bidding", contextId = "cloud-biz-bidding-orderLineFormulaPriceDetail")
public interface BidOrderLineFormulaPriceDetailClient extends IVendorBiddingEssentialFactorValueService {

    @GetMapping({"/techProposal/bidOrderLineFormulaPriceDetails/findEssentialFactorValuesByDetailId"})
    List<EssentialFactorValue> findEssentialFactorValues(@RequestParam("detailId") long detailId);

    /**
     * 根据bidId返回
     */
    @GetMapping("/bidInitiating/biding/getCurrencyByBidingId")
    public List<BidBidingCurrency> getCurrencyByBidId(@RequestParam("bidingId") Long bidingId);
}
