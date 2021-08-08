package com.midea.cloud.srm.model.base.formula.dto.update;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
 *  修改日期: 2020/8/27 20:39
 *  修改内容:
 * </pre>
 */
@Data
public class PricingFormulaUpdateDto {
    /**
     * 公式头id
     */
    @NotNull(message = "公式头id不能为空")
    private Long pricingFormulaHeaderId;
    /**
     * 公式名
     */
    private String pricingFormulaName;

    /**
     * 公式描述
     */
    private String pricingFormulaDesc;

    /**
     * INVAILD("INVAILD", "失效"),
     *     DRAFT("DRAFT", "新建"),
     *     ACTIVE("ACTIVE", "生效");
     */
    private String pricingFormulaStatus;
    /**
     * 公式值
     */
    private String pricingFormulaValue;

    @Valid
    private List<PricingFormulaLineUpdateDto> lineDto;

    private String isSeaFoodFormula;
}
