package com.midea.cloud.srm.model.logistics.bargaining.purchaser.projectmanagement.evaluation.enums;

import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 决标结果
 *
 * @author zixuan.yan@meicloud.com
 */
@Getter
@AllArgsConstructor
public enum SelectionStatus {
    WIN("中标", "WIN"),
    FAIL("落标", "FAIL");

    private final String    itemValue;
    private final String    itemName;

    public static SelectionStatus get(String itemValue) {
        return Arrays.stream(SelectionStatus.values())
                .filter(biddingAwardWay -> biddingAwardWay.getItemValue().equals(itemValue))
                .findAny()
                .orElseThrow(() -> new ApiException("Could not find SelectionStatus with [" + itemValue + "]"));
    }
}
