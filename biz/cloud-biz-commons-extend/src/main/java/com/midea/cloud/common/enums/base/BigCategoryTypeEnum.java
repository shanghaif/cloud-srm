package com.midea.cloud.common.enums.base;

import lombok.Getter;

@Getter
public enum BigCategoryTypeEnum {
    /*   采购分类   */
    PRODUCTION_MATERIAL("10","生产材料"),
    SPARE_PARTS("30","备品备件"),
    SERVICE("40","服务类"),
    COMPREHENSIVE_MATERIALS("70","综合类物资"),
    WASTE_MATERIALS("50","废旧物资处置"),
    LOGISTICS("60","物流"),
    DEVICE("20","设备");

    private String code;
    private String value;

    BigCategoryTypeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }
}
