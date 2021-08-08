package com.midea.cloud.srm.model.base.formula.dto.calculate;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author tanjl11
 * @date 2020/11/16 17:39
 */
@Data
public class BaseMaterialPriceDTO implements Serializable {

    /**
     * 基价名称
     */
    private String baseMaterialName;
    /**
     * 基价id
     */
    private Long baseMaterialId;
    /**
     * 基价价格
     */
    private BigDecimal baseMaterialPrice;
    /**
     * 公式id
     */
    private Long formulaId;
    /**
     * 要素id
     */
    private Long essentialFactorId;
}
