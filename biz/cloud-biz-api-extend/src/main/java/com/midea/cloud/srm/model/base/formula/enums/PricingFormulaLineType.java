package com.midea.cloud.srm.model.base.formula.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author tanjl11@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/27 19:43
 *  修改内容:
 * </pre>
 */
@Getter
@AllArgsConstructor
public enum PricingFormulaLineType {
    CONSTANT("CONSTANT", "常量"), FIELD("FIELD", "字段"),
    OP("OP","操作符");
    private String code;
    private String type;
}
