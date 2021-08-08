package com.midea.cloud.srm.model.logistics.bargaining.purchaser.projectmanagement.evaluation.enums;

import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 决标方式
 *
 * @author zixuan.yan@meicloud.com
 */
@Getter
@AllArgsConstructor
public enum BiddingAwardWay {
    INDIVIDUAL_DECISION("INDIVIDUAL_DECISION", "单项决标"),
    COMBINED_DECISION("COMBINED_DECISION", "组合决标");

    private final String    itemValue;
    private final String    itemName;

    public static BiddingAwardWay get(String itemValue) {
        return Arrays.stream(BiddingAwardWay.values())
                .filter(biddingAwardWay -> biddingAwardWay.getItemValue().equals(itemValue))
                .findAny()
                .orElseThrow(() -> new ApiException("Could not find BiddingAwardWay with [" + itemValue + "]"));
    }
}
