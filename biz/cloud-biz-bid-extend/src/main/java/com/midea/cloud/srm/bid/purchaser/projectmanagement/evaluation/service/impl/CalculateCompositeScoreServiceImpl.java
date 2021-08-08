package com.midea.cloud.srm.bid.purchaser.projectmanagement.evaluation.service.impl;

import com.midea.cloud.srm.bid.purchaser.projectmanagement.evaluation.service.ICalculateCompositeScoreService;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.techproposal.entity.OrderLine;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Implement of {@link ICalculateCompositeScoreService}.
 *
 * @author zixuan.yan@meicloud.com
 */
@Service
public class CalculateCompositeScoreServiceImpl implements ICalculateCompositeScoreService {

    @Override
    public void calculateAndSet(Collection<OrderLine> orderLines) {
        // 综合得分 = 价格得分 + 技术得分 + 绩效得分
        orderLines.forEach(orderLine -> orderLine.setCompositeScore(
                orderLine.getPriceScore().add(orderLine.getTechScore()).add(orderLine.getPerfScore())
        ));
    }
}
