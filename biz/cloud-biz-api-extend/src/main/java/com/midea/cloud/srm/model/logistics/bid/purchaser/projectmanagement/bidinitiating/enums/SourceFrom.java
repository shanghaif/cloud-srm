package com.midea.cloud.srm.model.logistics.bid.purchaser.projectmanagement.bidinitiating.enums;

import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 寻源单来源
 *
 * @author zixuan.yan@meicloud.com
 */
@Getter
@AllArgsConstructor
public enum SourceFrom {
    MANUAL("MANUAL", "手工"),
    PURCHASE_REQUEST("PURCHASE_REQUEST", "采购申请"),
    CONTRACT("CONTRACT", "合同");

    private final String    itemValue;
    private final String    itemName;

    public static SourceFrom get(String itemValue) {
        return Arrays.stream(SourceFrom.values())
                .filter(sourceFrom -> sourceFrom.getItemValue().equals(itemValue))
                .findAny()
                .orElseThrow(() -> new ApiException("Could not find SourceFrom with [" + itemValue + "]"));
    }
}
