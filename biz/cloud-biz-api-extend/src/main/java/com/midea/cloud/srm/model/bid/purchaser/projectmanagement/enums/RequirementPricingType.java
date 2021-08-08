package com.midea.cloud.srm.model.bid.purchaser.projectmanagement.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <pre>
 *  需求报价类型
 * </pre>
 *
 * @author tanjl11@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/5 15:42
 *  修改内容:
 * </pre>
 */
@AllArgsConstructor
@Getter
public enum RequirementPricingType {
    SIMPLE_PURCHASER("SIMPLE_PRICING","普通报价"),
    FORMULA_PURCHASER("FORMULA_PRICING","公式报价"),
    MODEL_PURCHASER("TEMPLATE_PRICING","模型报价");

    private final String    code;
    private final String    type;

}
