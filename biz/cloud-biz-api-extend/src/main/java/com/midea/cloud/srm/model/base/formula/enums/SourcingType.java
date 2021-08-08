package com.midea.cloud.srm.model.base.formula.enums;

import lombok.Getter;

/**
 * 寻源类型
 *
 * @author zixuan.yan@meicloud.com
 */
@Getter
public enum  SourcingType {
    BIDDING(0, "招标"),
    BARGAINING(1, "询比价"),
    COMPETING(2, "竞价");

    private final int       itemValue;
    private final String    itemName;

    SourcingType(int itemValue, String itemName) {
        this.itemValue = itemValue;
        this.itemName = itemName;
    }
}
