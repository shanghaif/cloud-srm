package com.midea.cloud.srm.model.base.formula.dto.update;

import lombok.Data;

import javax.validation.constraints.NotNull;

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
 *  修改日期: 2020/8/27 20:41
 *  修改内容:
 * </pre>
 */
@Data
public class PricingFormulaLineUpdateDto {
    private Long pricingFormulaLineId;

    /**
     * 公式行类型
     */
    private String pricingFormulaLineType;

    /**
     * 要素id
     */
    private Long essentialFactorId;

    /**
     * 行值
     */
    private String pricingFormulaLineValue;

}
