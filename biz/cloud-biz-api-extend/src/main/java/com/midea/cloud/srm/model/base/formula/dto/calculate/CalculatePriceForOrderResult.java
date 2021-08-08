package com.midea.cloud.srm.model.base.formula.dto.calculate;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @author tanjl11
 * @date 2020/11/25 18:07
 */
@Data
public class CalculatePriceForOrderResult implements Serializable {
    //公式，以及价格
    public Map<String, String> formulaAndParam;
    //
    public BigDecimal calcResult;
}
