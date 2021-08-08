package com.midea.cloud.srm.base.formula.service;

import com.midea.cloud.srm.model.base.formula.vo.PricingFormulaCalculateParameter;
import com.midea.cloud.srm.model.base.formula.vo.PricingFormulaCalculateResult;

import java.util.List;

/**
 * 公式价格计算服务
 *
 * @author zixuan.yan@meicloud.com
 */
public interface IPricingFormulaCalculateService {

    /**
     * 计算公式价格
     *
     * @param parameter 计算参数
     * @return  计算结果
     */
    PricingFormulaCalculateResult calculate(PricingFormulaCalculateParameter parameter);

    /**
     * 批量计算公式价格
     * c
     * @param parameters 计算参数集
     * @return  计算结果集
     */
    List<PricingFormulaCalculateResult> calculate(List<PricingFormulaCalculateParameter> parameters);
}
