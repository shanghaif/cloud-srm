package com.midea.cloud.srm.bid.purchaser.projectmanagement.evaluation.service;

import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.entity.BidVendor;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.techproposal.entity.OrderLine;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.techscore.vo.CalculateVendorTechnologyScoreParameter;
import com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.techscore.vo.VendorTechnologyScore;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 技术得分计算服务
 *
 * @author zixuan.yan@meicloud.com
 */
public interface ICalculateTechnologyScoreService {

    /**
     * 计算 供应商技术得分
     *
     * @param parameter 计算参数
     */
    List<VendorTechnologyScore> calculate(CalculateVendorTechnologyScoreParameter parameter);

    /**
     * 计算并设置 供应商技术得分
     *
     * @param orderLines 供应商报价行集
     */
    void calculateAndSet(Collection<OrderLine> orderLines);


    /* =========================================================================== */

    /**
     * 报价行匹配指定供应商
     */
    Function<BidVendor, Predicate<OrderLine>> filterVendor =
            bidVendor -> orderLine -> bidVendor.getBidVendorId().equals(orderLine.getBidVendorId());
}
