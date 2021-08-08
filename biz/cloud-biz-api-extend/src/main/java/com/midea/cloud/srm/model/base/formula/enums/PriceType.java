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
 *  修改日期: 2020/9/1 8:54
 *  修改内容:
 * </pre>
 */
@AllArgsConstructor
@Getter
public enum PriceType {
    DAY_PRICE("DAY_PRICE", "当天价格"),
    YESTERDAY_PRICE("YESTERDAY_PRICE", "前一天价格");
    private String code;
    private String type;
}
