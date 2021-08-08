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
 *  修改日期: 2020/8/26 23:06
 *  修改内容:
 * </pre>
 */
@Getter
@AllArgsConstructor
public enum BaseMaterialCalculateType {
    DIRECT_CALCULATE("DIRECT_CALCULATE","直接计算"),
    CALCULATE_BY_RATE("CALCULATE_BY_RATE","费率计算");
    private String code;
    private String type;
}
