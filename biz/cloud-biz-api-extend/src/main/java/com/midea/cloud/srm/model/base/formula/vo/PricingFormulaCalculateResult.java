package com.midea.cloud.srm.model.base.formula.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

/**
 * 公式价格计算结果
 *
 * @author zixuan.yan@meicloud.com
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class PricingFormulaCalculateResult implements Serializable {

    private String        calculateId;
    private BigDecimal    value;
    private PricingFormulaCalculateParameter calculateParameter;
    private Map<String,String> formulaParam;

    private PricingFormulaCalculateResult(PricingFormulaCalculateParameter calculateParameter,
                                          BigDecimal calculateResult,  Map<String,String> formulaParam
    ) {
        this.calculateId = calculateParameter.getCalculateId();
        this.value = calculateResult;
        this.calculateParameter = calculateParameter;
        this.formulaParam=formulaParam;
    }


    public static PricingFormulaCalculateResult create(PricingFormulaCalculateParameter calculateParameter,
                                                       BigDecimal calculateResult,Map<String,String> formulaParam) {
        return new PricingFormulaCalculateResult(calculateParameter, calculateResult,formulaParam);
    }
}
