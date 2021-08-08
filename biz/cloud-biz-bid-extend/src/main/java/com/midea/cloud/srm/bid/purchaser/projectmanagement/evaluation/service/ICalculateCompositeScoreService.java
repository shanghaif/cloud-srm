package com.midea.cloud.srm.bid.purchaser.projectmanagement.evaluation.service;

import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.techproposal.entity.OrderLine;

import java.util.Collection;

/**
 * 综合得分计算服务
 *
 * @author zixuan.yan@meicloud.com
 */
public interface ICalculateCompositeScoreService {

    /**
     * 计算供应商综合得分并设置
     *
     * @param orderLines 供应商报价行集
     */
    void calculateAndSet(Collection<OrderLine> orderLines);
}
