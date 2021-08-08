package com.midea.cloud.srm.model.base.clarify.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author tanjl11
 * @date 2020/10/08 16:45
 */
@Getter
@AllArgsConstructor
public enum ClarifySourcingType {
    AUCTION("AUCTION", "竞价"),
    RFQ("RFQ", "询比价"),
    TENDER("TENDER", "招标");
    private final String itemValue;
    private final String itemName;
}
