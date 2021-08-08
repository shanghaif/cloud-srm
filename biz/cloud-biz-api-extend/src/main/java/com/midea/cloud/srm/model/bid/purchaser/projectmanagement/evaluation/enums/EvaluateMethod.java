package com.midea.cloud.srm.model.bid.purchaser.projectmanagement.evaluation.enums;

import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 评分方式
 *
 * @author zixuan.yan@meicloud.com
 */
@Getter
@AllArgsConstructor
public enum EvaluateMethod {
    LOW_PRICE("LOWER_PRICE", "合理低价法"),
    HIGH_PRICE("HIGH_PRICE", "合理高价法"),
    COMPOSITE_PRICE("COMPOSITE_SCORE", "综合评分法");

    private final String    itemValue;
    private final String    itemName;

    public static EvaluateMethod get(String itemValue) {
        return Arrays.stream(EvaluateMethod.values())
                .filter(evaluateMethod -> evaluateMethod.getItemValue().equals(itemValue))
                .findAny()
                .orElseThrow(() -> new ApiException("Could not find EvaluateMethod with [" + itemValue + "]"));
    }
}
