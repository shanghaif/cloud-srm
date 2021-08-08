package com.midea.cloud.srm.model.base.formula.dto.create;


import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


/**
 * <pre>
 *  公式行创建对象
 * </pre>
 *
 * @author tanjl11@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/8/27 18:45
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class PricingFormulaLineCreateDto {

    /**
     * 公式行类型
     */
    @NotEmpty(message = "公式行类型不能为空")
    private String pricingFormulaLineType;

    /**
     * 要素id
     */
    private Long essentialFactorId;

    /**
     * 行值
     */
    @NotEmpty(message = "行值不能为空")
    private String pricingFormulaLineValue;
}
