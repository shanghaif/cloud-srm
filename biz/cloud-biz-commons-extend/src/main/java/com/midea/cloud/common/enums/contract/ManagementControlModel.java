package com.midea.cloud.common.enums.contract;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author tanjl11
 * @date 2020/11/29 19:05
 */
@AllArgsConstructor
@Getter
public enum ManagementControlModel {
    QUAN_LIMIT("QUAN_LIMIT", "定量"),
    CERTAION_AMOUNT("CERTAION_AMOUNT", "定金额"),
    QUAN_AMOUNT_LIMIT("QUAN_AMOUNT_LIMIT", "定量定金额"),
    FORM_CONTRANT("FORM_CONTRANT", "标准合同"),
    TOTAL_AMOUNT("TOTAL_AMOUNT" , "定合同总金额");
    private final String value;
    private final String name;
}
