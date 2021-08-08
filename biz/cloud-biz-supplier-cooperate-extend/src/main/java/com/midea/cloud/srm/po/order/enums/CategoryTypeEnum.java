package com.midea.cloud.srm.po.order.enums;

import lombok.Data;
import lombok.Getter;

/**
 * <pre>
 *  采购分类类型枚举
 * </pre>
 *
 * @author chenwt24@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-10-08
 *  修改内容:
 * </pre>
 */
@Getter
public enum CategoryTypeEnum {
    /*   采购分类   */
    PRODUCTION_MATERIAL("10","生产材料"),
    SPARE_PARTS("30","备品备件 "),
    SERVICE("40","服务类"),
    COMPREHENSIVE_MATERIALS("70","综合类物资"),
    WASTE_MATERIALS("50","废旧物资处置"),
    LOGISTICS("60","物流"),
    DEVICE("20","设备");

    private String code;
    private String value;

    CategoryTypeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }
}
