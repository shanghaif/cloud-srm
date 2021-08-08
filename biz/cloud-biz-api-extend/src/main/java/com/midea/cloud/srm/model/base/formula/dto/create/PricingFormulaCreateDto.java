package com.midea.cloud.srm.model.base.formula.dto.create;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

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
 *  修改日期: 2020/8/27 18:44
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class PricingFormulaCreateDto {
    /**
     * 公式名称
     */
    @NotEmpty(message = "公式名称不能为空")
    private String pricingFormulaName;

    /**
     * 公式描述
     */
    private String pricingFormulaDesc;

    /**
     * 公式值
     */
    @NotEmpty(message = "公式值不能为空")
    private String pricingFormulaValue;
    @Valid
    private List<PricingFormulaLineCreateDto> lineDto;
    /**
     * 是否海鲜价公式
     */
    private String isSeaFoodFormula;
}
