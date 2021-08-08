package com.midea.cloud.srm.model.bid.purchaser.projectmanagement.techscore.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collection;

/**
 * 供应商技术得分计算参数
 *
 * @author zixuan.yan@meicloud.com
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CalculateVendorTechnologyScoreParameter implements Serializable {

    private Collection<Long>    bidVendorIds;
}
