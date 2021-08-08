package com.midea.cloud.srm.model.base.formula.vo;

import com.midea.cloud.srm.model.base.formula.entity.PricingFormulaHeader;
import com.midea.cloud.srm.model.base.formula.entity.PricingFormulaLine;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
 *  修改日期: 2020/8/27 19:54
 *  修改内容:
 * </pre>
 */
@Data
public class PricingFormulaDetailVO {
    /**
     * 公式头id
     */
    private Long pricingFormulaHeaderId;

    /**
     * 公式名称
     */
    private String pricingFormulaName;

    /**
     * 公式描述
     */
    private String pricingFormulaDesc;

    /**
     * 公式值
     */
    private String pricingFormulaValue;

    /**
     * INVAILD("INVAILD", "失效"),
     * DRAFT("DRAFT", "新建"),
     * ACTIVE("ACTIVE", "生效");
     */
    private String pricingFormulaStatus;

    private List<PricingFormulaLineVO> lineDto;

    private String isSeaFoodFormula;
    /**
     * 对象转换 - {@link PricingFormulaHeader}.
     *
     * @return A new {@link PricingFormulaHeader}.
     */
    public PricingFormulaHeader toPricingFormulaHeader() {
        PricingFormulaHeader header = new PricingFormulaHeader();
        BeanUtils.copyProperties(this, header);
        return header;
    }

    /**
     * 对象转换 - {@link List<PricingFormulaLine>}.
     *
     * @return A new {@link List<PricingFormulaLine>}.
     */
    public List<PricingFormulaLine> toPricingFormulaLines() {
        if (lineDto == null)
            return Collections.emptyList();
        return lineDto.stream()
                .map(vo -> {
                    PricingFormulaLine line = new PricingFormulaLine();
                    BeanUtils.copyProperties(vo, line);
                    return line;
                })
                .collect(Collectors.toList());
    }
}
