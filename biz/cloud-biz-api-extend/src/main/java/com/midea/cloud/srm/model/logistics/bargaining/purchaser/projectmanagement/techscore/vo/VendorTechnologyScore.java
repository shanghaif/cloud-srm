package com.midea.cloud.srm.model.logistics.bargaining.purchaser.projectmanagement.techscore.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 供应商技术得分
 *
 * @author zixuan.yan@meicloud.com
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VendorTechnologyScore implements Serializable {

    private Long        bidVendorId;
    private BigDecimal  score;
    private Integer     weight;
    private BigDecimal  scoreWithoutWeight;
}
