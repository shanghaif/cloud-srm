package com.midea.cloud.srm.model.logistics.bargaining.purchaser.projectmanagement.techscore.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 供应商绩效得分
 *
 * @author zixuan.yan@meicloud.com
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VendorPerformanceScore implements Serializable {

    private Long        orderLineId;
    private Long        bidVendorId;
    private BigDecimal  score;
}
