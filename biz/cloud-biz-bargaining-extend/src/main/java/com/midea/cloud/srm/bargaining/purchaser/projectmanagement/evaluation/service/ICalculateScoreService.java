package com.midea.cloud.srm.bargaining.purchaser.projectmanagement.evaluation.service;

import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.Biding;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.techproposal.entity.OrderLine;

import java.util.List;

/**
 * 得分计算服务
 *
 * @author zixuan.yan@meicloud.com
 */
public interface ICalculateScoreService {

    /**
     * 计算得分
     *
     * @param bidding    招标单
     * @param orderLines 供应商报价行集
     */
    void calculateAndSet(Biding bidding, List<OrderLine> orderLines);

    /**
     * 得分排序
     *
     * @param orderLines 供应商报价行集
     */
    void rankAndSet(List<OrderLine> orderLines);
}
