package com.midea.cloud.common.enums.inq;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
public enum  ReasonForLimit {
    //黄牌
    YELLOW("YELLOW","黄牌"),
    //红牌
    RED("RED","红牌"),
    //战略供应商
    STRATEGIC_SUPPLIER("STRATEGIC_SUPPLIER","战略供应商"),
    //新供应商
    NEW_SUPPLIER("NEW_SUPPLIER","新供应商"),
    //质量警告
    QUALITY_WARNING("QUALITY_WARNING","质量警告");
    public String code;
    public String value;

}
