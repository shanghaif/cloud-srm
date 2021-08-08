package com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BidingFileTypeEnum {
    TECHNICAL_BID("TECHNICAL_BID","技术标附件"),
    COMMERCIA_BID("COMMERCIA_BID","商务标附件");
    private String code;
    private String name;
}
