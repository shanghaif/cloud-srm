package com.midea.cloud.srm.model.bid.purchaser.projectmanagement.techscore.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collection;

/**
 * 供应商绩效得分计算参数
 *
 * @author zixuan.yan@meicloud.com
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CalculateVendorPerformanceScoreParameter implements Serializable {

    private Collection<OrderLine>   orderLines;


    @Builder
    @AllArgsConstructor
    @Data
    public static class OrderLine {
        private final Long  orderLineId;
        private final Long  biddingId;
        private final Long  bidVendorId;
        private final Long  requirementLineId;
        private final Long  targetId;
        private final Long  categoryId;
        private final Long  orgId;
        private final Long  ouId;
    }
}
