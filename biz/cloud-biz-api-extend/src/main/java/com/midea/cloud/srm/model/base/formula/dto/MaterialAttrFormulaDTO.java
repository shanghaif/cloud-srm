package com.midea.cloud.srm.model.base.formula.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MaterialAttrFormulaDTO {

    // 获取物资编码
    private String materialCode;

    // 检查物资描述
    private String materialDescr;

    // 价格公式
    private String pricingFormulaName;

    // 价格公式明细
    private String pricingFormulaValue;

    /**
     * 属性编码
     */
    private String attributeCode;
    /**
     * 属性名称
     */
    private String attributeName;
    /**
     * 属性值
     */
    private String attributeValue;
    /**
     * 是否为关键属性
     */
    private String keyFeature;

}
