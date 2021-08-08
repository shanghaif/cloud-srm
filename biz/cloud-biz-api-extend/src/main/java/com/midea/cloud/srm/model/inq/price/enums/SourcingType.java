package com.midea.cloud.srm.model.inq.price.enums;

import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 寻源方式
 *
 * @author zixuan.yan@meicloud.com
 */
@Getter
@AllArgsConstructor
public enum SourcingType {
    AUCTION("AUCTION", "竞价"),
    RFQ("RFQ", "询比价"),
    TENDER("TENDER", "招标"),
    HANDMAKE("HANDMAKE", "手工创建");
    private final String itemValue;
    private final String itemName;

    public static SourcingType get(String itemValue) {
        return Arrays.stream(SourcingType.values())
                .filter(biddingAwardWay -> biddingAwardWay.getItemValue().equals(itemValue))
                .findAny()
                .orElseThrow(() -> new ApiException("Could not find SourcingType with [" + itemValue + "]"));
    }
}
