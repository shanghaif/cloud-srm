package com.midea.cloud.srm.model.base.formula.dto.calculate;

import lombok.Data;

import java.io.Serializable;

/**
 * @author tanjl11
 * @date 2020/11/23 21:41
 */
@Data
public class SeaFoodFormulaCalculateParam implements Serializable {
    //供应商报价填的东西
    private String essentialFactorValues;
    //基价列表
    private String priceJSON;
    //公式id
    private Long formulaId;
    //物料id
    private Long materialId;
    //计算id
    private String calculateId;
}
