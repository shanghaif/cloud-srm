package com.midea.cloud.srm.bid.purchaser.projectmanagement.evaluation.service;

/**
 * 价格审批单服务
 *
 * @author zixuan.yan@meicloud.com
 */
public interface IPriceReviewFormService {

    /**
     * 生成 价格审批单
     *
     * @param biddingId 招标单ID
     */
    void generateForm(Long biddingId);
}
